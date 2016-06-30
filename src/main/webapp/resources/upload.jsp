<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
</head>
<body>

	<div class="form-inline">
		<input name="News[picture_url]" type="text" id="license_url" value="" readonly onclick="open_upload('license_url',6)" class="form-control" />
		<button type="button" onclick="open_upload('license_url',6)" class="btn btn-sm btn-info">选择要上传的文件</button>
	</div>

	<!-- uploadfile modal -->
	<div class="modal fade" id="fileModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">添加文件</h4>
				</div>
				<div class="modal-body">
					<form action="${pageContext.request.contextPath}/search/uploadFile.html" id="upform" method="post" role="form" target="up_frame" onsubmit="return chk();" enctype="multipart/form-data">
						<div class="form-group form-inline">
							<label for="exampleInputCity">选择上传的城市：</label> <select name="city" class="form-control input-sm" id="exampleInputCity">
								<option value="BEIJING">北京</option>
								<option value="CHANGCHUN">长春</option>
								<option value="CHENGDU">成都</option>
								<option value="GUIYANG">贵阳</option>
								<option value="HAERBIN">哈尔滨</option>
								<option value="LANZHOU">兰州</option>
								<option value="NANCHANG">杭州</option>
								<option value="TIANJIN">天津</option>
								<option value="WUHAN">武汉</option>
							</select><br> <label for="exampleInputFile">选择上传的文件：</label> <input type="hidden" value="" name="filetype" id="filetype" /> <input type="hidden" value="" name="control" id="control" /> <input type="file" id="files" name="file" class="form-control input-sm" id="exampleInputFile" accept=".properties" />
							<p class="help-block">1、限行数据更新</p>
							<p class="help-block">2、请上传以UTF-8编码的properties限行数据文件</p>
							<p class="help-block">3、注意正确选择城市</p>
							<p align="center">
								<button type="button" id="btn_sub" class="btn btn-default">
									<i class="glyphicon glyphicon-open" title="open"></i>上传文件
								</button>
								<button type="button" class="btn btn-danger" onclick="close_upload()">
									<i class="glyphicon glyphicon-ban-circle" title="ban-circle"></i>关闭上传
								</button>
							</p>
						</div>
						<iframe src="" name="up_frame" id="up_frame" style="display: none" frameborder="0"></iframe>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- <script src="https://code.jquery.com/jquery.js"></script> -->
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<!-- <script src="js/bootstrap.min.js"></script> -->
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	function chk() {
		if (document.getElementById("files").value === "") {
			alert("上传文件不能为空");
			document.getElementById("files").focus();
			return false;
		}
		;
	}

	function open_upload(e, s) {
		$("#fileModal").modal("show");
		$("#files").val($("#" + e).val());
		$("#control").val(e);
		$("#filetype").val(s);
	}

	function close_upload() {
		$("#fileModal").modal("hide");
	}

	function upload_success(info, e, s, w_str) {
		if (info == "ok") {
			$("#fileModal").modal("hide");
			var conl = document.getElementById(s);
			conl.value = e;
			$("#login-form").append(w_str);
		} else {
			$("#fileModal").modal("hide");
			$("#x_alert").html(e);
			$("#x_alert").show();
			window.setTimeout(function() {
				$("#x_alert").hide();
			}, 3000);
		}
	}
		$(function(){
			$("#btn_sub").on("click",function(){
				chk();
				$("#upform").submit();
				close_upload();
				window.location='${pageContext.request.contextPath}/search/list.html';
			});
		});
		
	</script>
</body>
</html>