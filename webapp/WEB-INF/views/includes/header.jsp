<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="header">
	<h1>
		<a href="${pageContext.servletContext.contextPath }/main">MySite</a>
	</h1>
	<ul>
		<c:choose>
			<c:when test="${empty sessionScope.authUser }">
				<li><a href="${pageContext.servletContext.contextPath }/user/login">로그인</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/user/join">회원가입</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.servletContext.contextPath }/user/modify">회원정보수정</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a></li>
				<li>${sessionScope.authUser.name }님안녕하세요 ^^;</li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>