$(function(){
	// 中カテゴリを選択すると自動で中カテゴリが表示される
	$("select[name='middleCategory']").on("change",function(){
		var hostUrl = "http://localhost:8080/get_child_category";
		var parentId = $("select[name='middleCategory'] option:selected").val();
		
		$.ajax({
			url: hostUrl,
			type: "GET",
			dataType: "json",
			data:{
				parentId: parentId
			},
			async: true
		}).done(function(data){
			$("select[name='childCategory'] option").remove(); // リセットする
			$('#childCategory').append($('<option>').html("- 小カテゴリ -").val(0));
			// dataがnullじゃなければ = 大カテゴリ以外を選択した場合
			// nullじゃなければtrueとして扱える
			if (data.childCategoryList) {
				
				for (let i = 0; i < data.childCategoryList.length; i++) {
					var val = data.childCategoryList[i].id;
					var text = data.childCategoryList[i].categoryName;
					$('#childCategory').append($('<option>').html(text).val(val));	
				};
			} else {
				// dataがnullの場合 = 大カテゴリを選択した場合
				// リセットされ元に戻る
				$('#childCategory').append($('<option>').html("中カテゴリを選択してください").val(-1));
			}
		})
		
	});
});