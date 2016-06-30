<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>限行数据上传页</title>
</head>
<body>
	<div>
		<h2>1、限行数据更新</h2>
		<h2>2、请上传以UTF-8编码的properties限行数据文件</h2>
		<h2>3、注意选择城市。</h2>
		<form action="uploadFile.html" method="post" enctype="multipart/form-data">
			*请选择城市:<select name="city">
				<option value="BEIJING">北京</option>
				<option value="CHANGCHUN">长春</option>
				<option value="CHENGDU">成都</option>
				<option value="GUIYANG">贵阳</option>
				<option value="HANGZHOU">杭州</option>
				<option value="LANZHOU">兰州</option>
				<option value="NANCHANG">南昌</option>
				<option value="TIANJIN">天津</option>
				<!-- <option value="WUHAN">武汉</option> -->
			</select><br> *请选择文件:<input type="file" name="file" accept=".properties"><br> <input type="submit" value="确定上传 ">
		</form>
	</div>
</body>
</html>