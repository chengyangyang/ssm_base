<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%request.setAttribute("path", request.getContextPath()); %>
<!-- 引入jquery -->
<script type="text/javascript" src="${ path}/static/js/jquery-3.1.1.min.js"></script> 
<script type="text/javascript" src="${ path}/static/js/jquery-form.js"></script> 
<!--  引入样式-->
<link href="${ path}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${ path}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script> 

</head>
<body>
<!-- hello word!-->
<form id="uploadform" enctype="multipart/form-data">
<input type="text" id="name001" />
<input type="file" id="file" name="file" />
<input type="button" onclick="formfile()" value="提交"/>
</form> 
<!-- <form action="/ssm_base/person/importExcel" method="post" enctype="multipart/form-data">
<input type="file" id="excelFile" />
<input type="submit" class="btn btn-info" value="提交">
</form> -->
<input type="" class="btn btn-info" value="打印" onclick="print1()">
</body>

<script type="text/javascript">

function print1(){
	window.print();
}

upload=function(){
	var formData = new FormData(document.getElementById("upload-form"));  
	    $.ajax({  
	         url: "/ssm_base/person/upload",  
	         type:"POST",
	         data: formData,   
	         contentType: false,  
	         processData: false,  
	         success: function (resp) {
	        if(resp.result==1){
	$('#myModal').modal('hide');
	comm.showSuccessToast("上传成功");//成功提示
	}else{
	comm.showFailToast("上传失败");//失败提示
	}
	       // updownload.searchList();
	         }
	    });
	}



/* 使用jQueryform start */
 alert();
function formfile(){
	var form = $("#uploadform");
	var options = {
			url:"/ssm_base/person/importExcel",
			type:"POST",
			dataType:"json",
			success:function(result){
				alert("成功"+result);
				console.log(result);
			},
			error:function(result){
				alert("失败"+result);
				console.log(result);
			}
	
	};
	form.ajaxSubmit(options); 
}

/* 使用jQueryform end */

</script>
</html>