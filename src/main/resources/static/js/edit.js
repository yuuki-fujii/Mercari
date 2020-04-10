/**
 * 商品編集画面におけるJavaScriptの動作をまとめたファイル
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
	
	// 動作確認用
	var bigCategoryId = $('#editItemForm [name=bigCategoryId]').val();
	var middleCategoryId = $('#editItemForm [name=middleCategoryId]').val();
	var smallCategoryId = $('#editItemForm [name=smallCategoryId]').val();
	
	console.log('大カテゴリid : ' + bigCategoryId);
	console.log('中カテゴリid : ' + middleCategoryId);
	console.log('小カテゴリid : ' + smallCategoryId);
	
	// 大カテゴリのプルダウン作成
	function createBigCategorySelect(){
		var bigOptions = '<option value="">- 大カテゴリ -</option>';
		for (let i = 0; i < bigCategories.length; i++) {
			let bigCategory = bigCategories[i]; // 大カテゴリを1つ取得
			
			// 飛田さんのコード　三項演算子が使われている
			// 三項演算子　条件 ? trueの時の値 : falseの時の値
			// bigCategory.idが存在する場合、selected そうでない場合は空文字
			let selectedStr = $('#editItemForm [name=bigCategoryId]').val() == bigCategory.id ? ' selected' : '';			
			bigOptions += '<option value="' + bigCategory.id + '"' + selectedStr + '>' + bigCategory.name + '</option>';
		}
		$('#bigSelect').html(bigOptions);
		createMiddleCategorySelect();
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
						let selectedStr = $('#editItemForm [name=middleCategoryId]').val() == middleCategory.id ? ' selected' : '';
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
						let selectedStr = $('#editItemForm [name=smallCategoryId]').val() == smallCategory.id ? ' selected' : '';
			            smallOptions += '<option value="' + smallCategory.id + '"' + selectedStr + '>' + smallCategory.name + '</option>';
					}
					$('#smallSelect').html(smallOptions);
				}	
			}	
		} else {
			$('#smallSelect').html('');
			$('#smallSelect').val('');
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
		return categoryName;
	}
	
    // 検索ボタンクリック時のイベント処理設定
    $('#button-edit').on('click', function() {
      var str = new String(createCategoryName());
      // 空文字の時は全てのカテゴリidをnullにする
      if ('' == str) {
    	  $('#editItemForm [name=bigCategoryId]').val(null); 
    	  $('#editItemForm [name=middleCategoryId]').val(null); 
    	  $('#editItemForm [name=smallCategoryId]').val(null); 
      }
      $('#editItemForm [name=categoryName]').val(createCategoryName());
      $('#editItemForm').submit();
    });
	
	// 大カテゴリが変更された時の処理
	$('#bigSelect').on('change',function(){
		createMiddleCategorySelect();
	});
	
	// 中カテゴリが変更された時の処理
	$('#middleSelect').on('change',function(){
		createSmallCategorySelect();
	});
	
});