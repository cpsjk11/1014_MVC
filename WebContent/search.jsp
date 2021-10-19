<%@page import="paging.Paging"%>
<%@page import="mybatis.vo.LocVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
		width: 600px;
		border-collapse: collapse;
		
	}
	table th, table td{
		border: 1px solid black;
		padding: 4px;
	}
	table caption{
		text-indent: -9999px;
		height: 0;
	}
	.popup{
		display: none;
	}
	.disable {
	    padding:3px 7px;
	    border:1px solid silver;
	    color:silver;
	}
	.paging{
		list-style: none;
	}
		table tfoot ol.paging li {
	    float:left;
	    margin-right:8px;
	}
	#header{
		text-decoration: none;
		color: black;
	}
	.s{
	    padding:3px 7px;
	    border:1px solid blue;
	    color: blue;
	}
	.s a{
		text-decoration: none;
		color: blue;
	}
	.now{
		border:1px solid silver;
		padding:3px 7px;
	    color: blue;

	}
	.move{
		padding:3px 7px;
		
	}
	.move a{
		color: black;
		text-decoration: none;
	}
</style>

<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
</head>
<body>
	<div id="wrap">
		<header>
			<h1><a href="Controller" id="header">도시 검색목록</a></h1>
		</header>
		<article>
			<table>
				<caption>도시목록</caption>
				<colgroup>
					<col width="50px"/>
					<col width="150px"/>
					<col width="*"/>
					<col width="100px"/>
					<col width="100px"/>
					<col width="50px"/>
				</colgroup>
				<thead>
					<tr>
						<td colspan="6">
							<%-- <input type="button"value="추가" onclick="addWin()"/>
							<input type="button"value="검색" onclick="searchWin()"/>--%>
						</td>
					</tr>
					<tr>
						<th>도시코드</th>
						<th>주소</th>
						<th>우편번호</th>
						<th>도시명</th>
						<th>주,도</th>
						<th>국적코드</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="vo" items="${ar}">
					<tr>
						<td>${vo.location_id }</td>
						<td>${vo.street_address}</td>
						<td>${vo.postal_code }</td>
						<td>${vo.city }</td>
						<td>
						<c:if test="${vo.state_province ne null}">
							${vo.state_province }
						</c:if>
						</td>
						<td>${vo.country_id }</td>
					</tr>
				</c:forEach>
					<%-- <%
						}// for문의 끝
					}//if문의 끝
					%> --%>
					<tfoot>
					<tr>
						<td colspan="6">
							<ol class="paging">
<%-- <%
				Paging pvo = null;
				if(obj2 != null){
					pvo = (Paging)obj2;
					
					if(pvo.getStartPage() < pvo.getPagePerBlock()){
						
				
%> --%>
				<c:if test="${pvo.startPage < pvo.pagePerBlock}">
								<li class="disable">&lt;</li> <!-- 페이지 이전 버튼 비 활성화 -->

				</c:if>
				<c:if test="${pvo.startPage > pvo.pagePerBlock}">	

				<li class="s"><a href="Controller?type=search&cPage=${pvo.startPage - pvo.pagePerBlock}">&lt;</a></li> <!-- 페이지 이전 버튼 활성화 -->
				</c:if>


				<c:forEach begin="${pvo.startPage}" end="${pvo.endPage}" varStatus="st">
					<c:if test="${pvo.nowPage eq st.index}">
					
								<li class="now">${st.index}</li>

					</c:if>
				<c:if test="${pvo.nowPage ne st.index }">


								<li class="move"><a href="Controller?type=search&cPage=${st.index }">${st.index}</a></li>

				</c:if>
	
           		 </c:forEach>
				
				
				<c:if test="${pvo.endPage < pvo.totalPage }">
				<li class="s"><a href="Controller?type=search&cPage=${pvo.startPage + pvo.pagePerBlock}">&gt;</a></li>

				</c:if>
				<c:if test="${pvo.endPage >= pvo.totalPage }">
	
				<li class="disable">&gt;</li> <!-- 페이지 다음 버튼  활성화 -->

				</c:if>
							</ol>
						</td>
					</tr>
				</tfoot>
				</tbody>
			</table>
		</article>
	</div>