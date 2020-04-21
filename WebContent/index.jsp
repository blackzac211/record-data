<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/common/head.jsp" />

<script type="text/javascript">

	$(function() {
		showGraph();
	});
	
	function showGraph() {
		$.ajax({
			type:"post",
			url:"/graph/selectRecordLogs.do",
			// data:"id_up="+id_up,
			dataType:"json",
			async:true,
			success:function(data) {
				
			}
		});
	}
</script>
</head>
<body>

<div id="main">
	<div id="common-title">
		녹취 현황
	</div>
	
	<span class="button" onclick="showGraph()">조회</span>
</div> 

</body>
</html>