<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board" method="post">
					<c:choose>
						<c:when test="${empty map.kwd }">
							<input type="text" id="kwd" name="kwd" value="">		
						</c:when>
						<c:otherwise>
							<input type="text" id="kwd" name="kwd" value="${map.kwd }">
						</c:otherwise>
					</c:choose>
					
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:set var="count" value="${fn:length(map.list) }"/>
					<c:forEach items="${map.list }" var="vo" varStatus="status">
					<c:set var="startCount" value="${ ( map.recentPage - 1 ) * map.listCount }"/>
						<tr>
							<td>[${map.totalCount - (startCount + status.index) }]</td>
							<c:choose>
								<c:when test="${vo.depth > 0 }">
									<c:if test="${vo.deleted eq true}">
										<td style="text-align:left; padding-left:${vo.depth * 20}px">
											<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png"/>
											삭제된 글입니다.
										</td>									
									</c:if>
									<c:if test="${vo.deleted eq false}">
										<td style="text-align:left; padding-left:${vo.depth * 20}px">
											<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png"/>
											<a href="${pageContext.servletContext.contextPath }/board/view?no=${vo.no }&page=${map.recentPage}">${vo.title }</a>
										</td>
									</c:if>
								</c:when>
								<c:when test="${vo.depth == 0 }">
									<c:if test="${vo.deleted eq true}">
										<td style="text-align:left">
											삭제된 글입니다.
										</td>
									</c:if>
									<c:if test="${vo.deleted eq false}">
										<td style="text-align:left">
											<a href="${pageContext.servletContext.contextPath }/board/view?no=${vo.no }&page=${map.recentPage}">${vo.title }</a>
										</td>
									</c:if>		
								</c:when>
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:if test="${sessionScope.authUser.no == vo.userNo && vo.deleted == false}">
								<td><a href="${pageContext.servletContext.contextPath }/board/delete?no=${vo.no }&page=${map.recentPage }" class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${map.totalCount != 0 }">
					<div class="pager">
						<c:set var="pageCount" value="${fn:length(map.pages) }"/>
						<c:set var="recentPageVal" value="${map.recentPage }"></c:set>
						<c:set var="endPageVal" value="${map.endPage }"></c:set>
						<ul>
							<li><a href="${pageContext.servletContext.contextPath }/board/list?page=${map.startPage - map.pageCount }&kwd=${kwd }">◀</a></li>
							<c:forEach items="${map.pages }" var="page" varStatus="status">
								<c:choose>
									<c:when test="${ page eq recentPageVal }">
										<li class="selected">${page }</li>
									</c:when>
									<c:when test="${page gt endPageVal }">
										<li>${page }</li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.servletContext.contextPath }/board/list?page=${page }&kwd=${kwd }">${page }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<li><a href="${pageContext.servletContext.contextPath }/board/list?page=${map.startPage + map.pageCount }&kwd=${kwd }">▶</a></li>
						</ul>
					</div>
				</c:if>
				
				<c:if test="${not empty sessionScope.authUser }">
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/newpostwrite?page=${map.recentPage }" id="new-book">글쓰기</a>
					</div>
				</c:if>
				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>