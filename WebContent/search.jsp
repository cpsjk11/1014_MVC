<%@page import="mybatis.vo.LocVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</style>

<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
</head>
<body>
	<div id="wrap">
		<header>
			<h1>도시검색 목록</h1>
		</header>
		<article>
			<table>
				<caption>도시검색 목록</caption>
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
							<%-- <input type="button"value="추가" onclick="addWin()"/>--%>
							<input type="button"value="이전으로" onclick="javascript:location.href='Controller'"/>
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
					<%
				// 첫작업 : VO -- Mapper -- Config -- Factory -- DAO -- Action -- JSP
				// 그 이후부터는 : VO -- Mapper -- DAO -- ACtion -- JSP
					// 컨트롤러에서 forward로 이동되어 왔을 것으므로
					// request에 저장된 정보들 중 "ar"이라는 이름으로
					// 저장된 정보를 얻어낸다.
					Object obj = request.getAttribute("ar");
					
					//System.out.println(obj);
					LocVO[] ar = null;
					if(obj != null){
						ar = (LocVO[])obj;
					
						for(LocVO vo : ar){
					%>
						<tr>
							<td><%=vo.getLocation_id()%></td>
							<td><%=vo.getStreet_address()%></td>
							<td>
							<%if(vo.getPostal_code() != null){ %>
							<%=vo.getPostal_code()%>
							<%}%>
							</td>
							<td><%=vo.getCity()%></td>
							<td>
							<%if(vo.getState_province() != null){ %>
							<%=vo.getState_province()%>
							<%}%>
							</td>
							<td><%=vo.getCountry_id()%></td>
						</tr>
					<%
						}// for문의 끝
					}//if문의 끝
					%>
				</tbody>
			</table>
		</article>
		</div>
		</body>
		</html>
		