<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="POST" action="upload" enctype="multipart/form-data">

		사진1:<input type="file" name="org_file_path1" id="org_file_path1"><br>
		사진2:<input type="file" name="org_file_path2" id="org_file_path2"><br>
		업로더:<input type="text" name="up_name" id="up_name"><br>
		<button type="button" onclick="upload()">업로드</button>

	</form>
	<script>
		function upload() {
			var xhr = new XMLHttpRequest();
			xhr.open("POST", "/upload");
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {
					if (xhr.status == 200) {

					}
				}
			}
			var formData = new FormData();
			//------------form에있는 사진1, 2를 var f1, f2에 넣어서 브라우저의 콘솔에 찍는 코드(이걸통해서 파일을 배열로 받는다는걸 알 수 있음)
			//------------console.log(fi.files[1])을 해보면 f1에는 파일 한개만 넣었기 때문에 1번방은 언디파인디드가 뜸)
			//var f1 = document.querySelector("#org_file_path1");
			//var f2 = document.querySelector("#org_file_path2");
			//console.log(f1.files[0]);
			//console.log(f2.files[0]);
			
						
			var f1 = document.querySelector("#org_file_path1");
			var f2 = document.querySelector("#org_file_path2");
			formData.append('org_file_path1',f1.files[0]);
			formData.append('org_file_path2',f2.files[0]);
			formData.append('up_name',document.querySelector("#up_name").value);
			xhr.send(formData);
		}
	</script>
</body>
</html>