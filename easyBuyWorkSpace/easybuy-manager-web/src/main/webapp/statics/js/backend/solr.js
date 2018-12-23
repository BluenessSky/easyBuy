$(function(){
	$("#btnSolr").click(function(){
		$.ajax({
			url:"solr/intosolr",
			type:"post",
			data:null,
			dataType:"json",
			success:function(result){
				if(result.status == 200){
					alert("添加成功");
				} else {
					alert(result.data);
				}
			}
		});
	});
});