<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<script>
var isEnd = false;
var render = function(mode, vo){
	var html = 
		"<li data-no='" + vo.no + "'>" + 
			"<div class='list-content'>" + 
				"<strong>" + vo.name + "</strong>" + 
				"<p>" + vo.content.replace(/\n/gi, "<br/>") + "</p>" +
			"</div>" + 
			"<strong class='user'></strong>" + 
			"<a id='delete-ajax' href='#' data-no='" + vo.no + "'>삭제</a>" + 
		"</li>";
	
	if(mode == true){
		$("#list-guestbook").prepend(html);
	}else {
		$("#list-guestbook").append(html);
	}
}

$(function(){
	$("#add-form").submit(function(event) {
		event.preventDefault();
		
		var data = {};
		$.each($(this).serializeArray(), function(index, values){
			datas[values.name] = values.value;
		});
	});
	
	$("#btn-fetch").click(function(){
		if(isEnd == true){
			return;
		}
		
		var startNo = $("#list-guestbook li").last().data("no") || 0;
		$.ajax({
			url : "/mysite3/api/guestbook/list?no=" + startNo, 
			type : "get", 
			dataType : "json", 
			success : function(response) {
				if(response.result != "success") {
					console.log(response.message);
					return;
				}
				
				if(response.data.length < 5) {
					isEnd = true;
					$("#btn-fetch").prop("disabled", true);
				}
				
				$.each(response.data, function(index, vo){
					render(false, vo);
				});
			}
		});
	});
});
</script>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" name="name" id="input-name" placeholder="이름">
					<input type="password" name="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" name="content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<button id="btn-fetch">가져오기</button>
			
			<div id="dialog-message" title="test" style="display:none">
  				<p>테스트 입니다.</p>
			</div>
			
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>