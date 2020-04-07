$(function(){
	// 大カテゴリを選択すると自動で中カテゴリが表示される
	$("select[name='bigCategory']").on("change",function(){
		var hostUrl = "http://localhost:8080/get_child_category";
		var parentId = $("select[name='bigCategory'] option:selected").val();
		
		$.ajax({
			url: hostUrl,
			type: "GET",
			dataType: "json",
			data:{
				parentId: parentId
			},
			async: true
		}).done(function(data){
			$("select[name='middleCategory'] option").remove(); // リセットする
			$('#middleCategory').append($('<option>').html("- 中カテゴリ -").val(0));
			$("select[name='childCategory'] option").remove(); // リセットする
			$('#childCategory').append($('<option>').html("- 小カテゴリ -").val(0));
			// dataがnullじゃなければ = 大カテゴリ以外を選択した場合
			// nullじゃなければtrueとして扱える
			if (data.childCategoryList) {
				
				for (let i = 0; i < data.childCategoryList.length; i++) {
					var val = data.childCategoryList[i].id;
					var text = data.childCategoryList[i].categoryName;
					$('#middleCategory').append($('<option>').html(text).val(val));	
				};
			} else {
				// dataがnullの場合 = 大カテゴリを選択した場合
				// リセットされ元に戻る
				$('#middleCategory').append($('<option>').html("大カテゴリを選択してください").val(-1));
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