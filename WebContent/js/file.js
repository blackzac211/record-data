function uploadFile(file_path, callback) {
	if ($("#file_name").val() == "") {
		alert("Please, enter file name.");
		return;
	}
	if ($("#mfile")[0].files[0] == null) {
		alert("Please, upload a file.");
		return;
	}
	var formData = new FormData();
	formData.append("file_path", decodeURIComponent(file_path));
	formData.append("file_name", $("#file_name").val());
	formData.append("mfile", $("#mfile")[0].files[0]);
	showLoading();
	$.ajax({
		url : '/file/uploadFile.do',
		data : formData,
		processData : false,
		contentType : false,
		type : "post",
		async : true,
		success : function(data) {
			hideLoading();
			alert("The file has been uploaded successfully.");
			callback();
		},
		error : function() {
			alert("An error has occured.");
		}
	});
}

function downloadFile(org_file_name) {
	var ifrm = $("<iframe id='download-ifrm' name='download-ifrm' style='width:0;height:0;'></iframe>");
	$("body").append(ifrm);
	var form = $("<form method='post' action='/file/downloadFile.do' target='download-ifrm'></form>");
	$("body").append(form);
	form.append("<input type='hidden' name='org_file_name' value='"+decodeURIComponent(org_file_name)+"' />");
	form.submit();
	form.remove();
}

function deleteFile(org_file_name, callback) {
	if(!confirm("Do you want to delete the file?")) {
		return;
	}
	$.ajax({
		type:"post",
		url:"/file/deleteFile.do",
		data:"org_file_name="+org_file_name,
		dataType:"json",
		async:false,
		success : function(data) {
			alert("successfully deleted.");
			callback();
		},
		error : function() {
			alert("An error has occured.");
		}
	});
}

