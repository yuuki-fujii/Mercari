// 大カテゴリを選択すると自動で検索が実行される
$(function(){
	$("select[name='bigCategory']").on("change",function(){
		var hostUrl = "http://localhost:8080/search_by_category";
		var categoryId = $("select[name='bigCategory'] option:selected").val();
		
		$.ajax({
			url: hostUrl,
			type: "GET",
			dataType: "json",
			data:{
				categoryId: categoryId
			},
			async: true
		}).done(function(data){
			console.log(data.itemList);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert('正しい結果を得られませんでした。');
			console.log("XMLHttpRequest : " + XMLHttpRequest.status); 
			console.log("textStatus : " + textStatus); 
			console.log("errorThrown : " + errorThrown.message);
		});
	});	
});