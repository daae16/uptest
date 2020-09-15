<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST" action="upload" enctype="multipar/form-data">
   사진 1 : <input type="file" name="org_file_path1" id="org_file_path1"><b>
   사진 2 : <input type="file" name="org_file_path2" id="org_file_path2"><b>
   업로더 : <input type="text" name="up_name" id="up_name">
   <button type="button" onclick="upload()">업로드</button>

</form>
<script>
function upload() {
	var xhr = new xhrHTMLRequest();
	xhr.open("POST","/upload");
	xhr.onreadystatechange = function(){
		if(xhr.readyState==4){
			if(xhr.status == 200){
				
			}
		}
	}
	var formData = new FormData();
	
	var f1 = document.querySeleter("#org_file_path1");
	var f2 = document.querySeleter("#org_file_path2");
	formData.append('org_file_path1',f1.files[0]);
	formData.append('org_file_path2',fi.files[0]);
	formData.append('up_name',document.querySelector("#up_name").value);
	xhr.send(formData);
}

</script>

</body>
</html>