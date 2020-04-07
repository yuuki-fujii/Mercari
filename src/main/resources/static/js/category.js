/**
 * カテゴリを自動変更するためのjsファイル
 */
$(function(){
	// 
	$("select[name='bigCategory']").on("change",function(){
		var hostUrl = "http://localhost:8080/get_middle_category"
		var bigCategoryId = $("select[name='bigCategory'] option:selected").val();
		
		
		
		
		$.ajax({
			url: hostUrl,
			type: "GET",
			dataType: "json",
			data:{
				bigCategoryId: bigCategoryId
			},
			async: true
		}).done(function(data){
			$("select[name='middleCategory'] option").remove(); // リセットする
			$('#middleCategory').append($('<option>').html("- 中カテゴリ -").val(0));
			// dataがnullじゃなければ = 大カテゴリ以外を選択した場合
			// nullじゃなければtrueとして扱える
			if (data.middleCategoryList) {
				
				for (let i = 0; i < data.middleCategoryList.length; i++) {
					var val = data.middleCategoryList[i].id;
					var text = data.middleCategoryList[i].categoryName;
					$('#middleCategory').append($('<option>').html(text).val(val));	
				};
			} else {
				// dataがnullの場合 = 大カテゴリを選択した場合
				// リセットされ元に戻る
				$('#middleCategory').append($('<option>').html("大カテゴリを選択してください"));
			} 
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert('正しい結果を得られませんでした。');
			console.log(data.middleCategoryList);
			console.log("XMLHttpRequest : " + XMLHttpRequest.status); 
			console.log("textStatus : " + textStatus); 
			console.log("errorThrown : " + errorThrown.message);
		});
	});
});