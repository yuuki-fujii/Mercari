/**
 *  商品検索画面におけるjavascriptの動作をまとめたファイル
 */
$(function(){
	var hostUrl = "http://localhost:8080/item/categories";
	
	// 必要な変数を先に宣言しておく
	var bigCategories; 
	var selectedBigCategory;
	var middleCategories;
	var selectedMiddleCategory;
	var smallCategories;
	
	// getJSONメソッドを使って、全情報が詰まった大カテゴリリストを取得する
	$.getJSON(hostUrl)
		.done(function(data){
			bigCategories = data;
			createBigCategorySelect(bigCategories);
		}).fail(function(){
			console.log('失敗');
		}).always(function(){
			// 成功しても失敗しても通る
		});
	
	// 大カテゴリのプルダウン作成
	function createBigCategorySelect(){
		var bigOptions = '<option value="">- 大カテゴリ -</option>';
		for (let i = 0; i < bigCategories.length; i++) {
			let bigCategory = bigCategories[i]; // 大カテゴリを1つ取得
			
			// 飛田さんのコード　三項演算子が使われている
			// 三項演算子　条件 ? trueの時の値 : falseの時の値
			// bigCategory.idが存在する場合、selected そうでない場合は空文字
			let selectedStr = $('#searchForm [name=bigCategoryId]').val() == bigCategory.id ? ' selected' : '';			
			bigOptions += '<option value="' + bigCategory.id + '"' + selectedStr + '>' + bigCategory.name + '</option>';
		}
		$('#bigSelect').html(bigOptions);
	}
		
	// 中カテゴリのプルダウン作成
	function createMiddleCategorySelect(){
		let selectedBigCategoryValue = $('#bigSelect option:selected').val();
		// 大カテゴリ以外が選択された場合
		if (selectedBigCategoryValue != '') {
			for (let i = 0; i < bigCategories.length; i++) {
				let bigCategory = bigCategories[i]; // 大カテゴリを1つ取得
				if (bigCategory.id == selectedBigCategoryValue) {
					selectedBigCategory = bigCategory;
					var middleOptions = '<option value="">- 中カテゴリ -</option>';
					middleCategories = bigCategory.childCategories;
					for (let j = 0; j < middleCategories.length; j++) {
						let middleCategory = middleCategories[j];
						let selectedStr = $('#searchForm [name=middleCategoryId]').val() == middleCategory.id ? ' selected' : '';
			            middleOptions += '<option value="' + middleCategory.id + '"' + selectedStr + '>' + middleCategory.name + '</option>';
					}
					$('#middleSelect').html(middleOptions);
				}
			}
			createSmallCategorySelect();
		} else {
			// - 大カテゴリ -　が選択された場合　→　中カテゴリ、小カテゴリをリセットする
			 $('#middleSelect').html('');
		     $('#middleSelect').val('');
		     $('#smallSelect').html('');
		     $('#smallSelect').val('');
		}	
	}
	
	// 小カテゴリーのプルダウン生成
	function createSmallCategorySelect() {
		let selectedMiddleCategoryValue = $('#middleSelect option:selected').val();
		if (selectedMiddleCategoryValue != '') {
			for (let i = 0; i < middleCategories.length; i++) {
				let middleCategory = middleCategories[i];
				if (middleCategory.id == selectedMiddleCategoryValue) {
					selectedMiddleCategory = middleCategory;
					var smallOptions = '<option value="">- 小カテゴリ -</option>';
					smallCategories = middleCategory.childCategories;
					for (let j = 0; j < smallCategories.length; j++) {
						let smallCategory = smallCategories[j];
						let selectedStr = $('#searchForm [name=smallCategoryId]').val() == smallCategory.id ? ' selected' : '';
			            smallOptions += '<option value="' + smallCategory.id + '"' + selectedStr + '>' + smallCategory.name + '</option>';
					}
					$('#smallSelect').html(smallOptions);
				}	
			}	
		} else {
			$('#smallSelect').html('');
		    $('#searchForm [name=smallCategoryId]').val('');
		}
	}
	
	// カテゴリーのプルダウンの選択文字列を連結
	function createCategoryName() {
		let categoryName = '';
		// 大カテゴリが選択されている場合
		if ($('#bigSelect option:selected').val() != '') {
			categoryName += $('#bigSelect option:selected').text();
			// 中カテゴリが選択されている場合
			if ($('#middleSelect option:selected').val() != '') {
				categoryName += '/' + $('#middleSelect option:selected').text();
				// 小カテゴリが選択されている場合
				if ($('#smallSelect option:selected').val() != '') {
					categoryName += '/' + $('#smallSelect option:selected').text();
				}
			}
		}
		console.log(categoryName);
		return categoryName;
	}
	
    // カテゴリーリンククリック時のイベント処理設定
    $('.categoryLink').on('click', function() {
      $('#searchForm [name=categoryName]').val($(this).data('category'));
      $('#searchForm [name=pageNumber]').val(1);
      $('#searchForm').submit();
    });
	
    // ブランド名リンククリック時のイベント処理設定
    $('.brandLink').on('click', function() {
      $('#searchForm [name=brandName]').val($(this).text());
      $('#searchForm [name=pageNumber]').val(1);
      $('#searchForm').submit();
    });
    
    // 検索ボタンクリック時のイベント処理設定
    $('#button-search').on('click', function() {
      $('#searchForm [name=pageNumber]').val(1);
      $('#searchForm [name=categoryName]').val(createCategoryName());
      $('#searchForm').submit();
    });
	
    // Goボタンクリック時のイベント処理設定
    $('#button-go').on('click', function() {
      $('#searchForm [name=categoryName]').val(createCategoryName()); // プルダウンの入力内容をそのまま反映させるため
      $('#searchForm').submit();
    });
	
    // next,previousリンクの無効化
    var currentPage = parseInt($('#searchForm [name=pageNumber]').val());
    var maxPage = parseInt($('#maxPage').text());
    if (currentPage == 1) {
      $('.previous').addClass('disabled');
    }
    if (currentPage == maxPage) {
      $('.next').addClass('disabled');
    }
    
    // nextリンククリック時のイベント処理設定
    $('.next').on('click', function() {
      $('#searchForm [name=pageNumber]').val(currentPage + 1);
      $('#searchForm [name=categoryName]').val(createCategoryName());
      $('#searchForm').submit();
    });

    // previousリンククリック時のイベント処理設定
    $('.previous').on('click', function() {
      $('#searchForm [name=pageNumber]').val(currentPage - 1);
      $('#searchForm [name=categoryName]').val(createCategoryName());
      $('#searchForm').submit();
    });
    
	// 大カテゴリが変更された時の処理
	$('#searchForm [name=bigCategoryId]').on('change',function(){
		createMiddleCategorySelect();
	});
	
	// 中カテゴリが変更された時の処理
	$('#searchForm [name=middleCategoryId]').on('change',function(){
		createSmallCategorySelect();
	});
	
});