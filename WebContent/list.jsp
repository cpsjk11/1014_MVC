<%@page import="mybatis.vo.CntVO"%>
<%@page import="paging.Paging"%>
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
</style>

<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
</head>
<body>
	<div id="wrap">
		<header>
			<h1>도시 목록</h1>
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
							<input type="button"value="추가" onclick="addWin()"/>
							<input type="button"value="검색" onclick="searchWin()"/>
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
					String cPage = request.getParameter("cPage");
					Object obj = request.getAttribute("ar3");
					Object obj2 = request.getAttribute("pvo");
					
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
					<tfoot>
					<tr>
						<td colspan="6">
							<ol class="paging">
<%
				Paging pvo = null;
				if(obj2 != null){
					pvo = (Paging)obj2;
					
					if(pvo.getStartPage() < pvo.getPagePerBlock()){
						
				
%>
								<li class="disable">&lt;</li> <!-- 페이지 이전 버튼 비 활성화 -->
<%
}else{	
%>
								<li><a href="Controller?type=list&cPage=<%=pvo.getStartPage() - pvo.getPagePerBlock()%>">&lt;</a></li> <!-- 페이지 이전 버튼 활성화 -->
<%} 
				for(int i=pvo.getStartPage(); i<=pvo.getEndPage(); i++){
					if(pvo.getNowPage() == i){
%>
								<li class="now"><%=i%></li>
<%
	}else{

%>
								<li><a href="Controller?type=list&cPage=<%=i%>"><%=i%></a></li>
<%
	}
}// 반복문의 끝
				
				if(pvo.getEndPage() < pvo.getTotalPage()){
%>
				<li><a href="Controller?type=list&cPage=<%=pvo.getStartPage() + pvo.getPagePerBlock()%>">&gt;</a></li>
<%
				}else{
%>	
				<li class="disable">&gt;</li> <!-- 페이지 다음 버튼  활성화 -->
<%
				}
%>
							</ol>
						</td>
					</tr>
				</tfoot>
				</tbody>
			</table>
		</article>
	</div>
	<%--추가Popup --%>
	<div id="add_popup" title="사원추가" class="popup">
		<div id="content">
			<form action="Controller" method="post">
				<label for="s_loc_id">도시코드:</label>
				<input type="text" id="s_loc_id" name="location_id"/><br/>
				<label for="s_addr">주소:</label>
				<input type="text" id="s_addr" name="street_address"/><br/>
				<label for="s_post">우편번호:</label>
				<input type="text" id="s_post" name="postal_code"/><br/>
				<label for="s_city">도시명:</label>
				<input type="text" id="s_city" name="city"/><br/>
				<label for="s_state">주,도:</label>
				<input type="text" id="s_state" name="state_province"/><br/>
				<label for="s_country">국적코드:</label>
				<select id="s_country" name="country_id">
				<%
					Object ob = request.getAttribute("ar2");
					if(ob != null){
						CntVO[] ar2 = (CntVO[])ob;
						for(CntVO cvo : ar2){
					
				%>
					<option value="<%=cvo.getCountry_id()%>"><%=cvo.getCountry_id()%></option>
				<%
						}
					}
				%>
				</select><br/>
				<input type="button" value="저장" onclick="send(this.form)">
				<input type="hidden" name="type" value="add"/>
				</form>
		</div>
	</div>
	
	<div id="search_popup" title="도시검색" class="popup">
		<div id="content">
			<form action="Controller" method="post">
				<select id="searchType" name="searchType">
					<option value="x">선택해주세요</option>
					<option value="0">도시코드</option>
					<option value="1">주소</option>
					<option value="2">우편번호</option>
					<option value="3">도시명</option>
					<option value="4">국적</option>
				</select>
				<input type="text" id="searchValue" name="searchValue"/><br/>
				<input type="button" value="검색" onclick="search(this.form)">
				<input type="hidden" name="type" value="list"/>
				</form>
		</div>
	</div>
	<%} %>
	<script>
	$(function(){
		$( "#s_hdate" ).datepicker({
			dateFormat: 'yy-mm-dd',
			dayNamesMin: ["일","월","화","수","목","금","토"],
			monthNames:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월",],
			showMonthAfterYear:true,
		});
	});
		function addWin(){
			 $( "#add_popup" ).dialog();
		}
		
		function send(frm){
			var loc = $("#s_loc_id").val();
			var city = $("#s_city").val()
			var cnt = $("#s_country").val();
			var addr = $("#s_addr").val();
			var post = $("#s_post").val()
			var state = $("#s_state").val();
			var country = $("#s_country").val();
			
			if($("#s_loc_id").val().trim().length <= 0){
				alert("도시코드를 입력해주세요")
				$("#s_loc_id").val("");
				$("#s_loc_id").focus();
				return;
			}
			
			if($("#s_city").val().trim().length <= 0){
				alert("도시명을 입력해주세요")
				$("#s_city").val("");
				$("#s_city").focus();
				return;
			}


			//frm.submit();
			var param = "type=add&location_id="+encodeURIComponent(loc)+"&street_address="+encodeURIComponent(addr)+
						"&postal_code="+encodeURIComponent(post)+"&city="+encodeURIComponent(city)+
						"&state_province="+encodeURIComponent(state)+"&country_id="+encodeURIComponent(country);
						
			
			$.ajax({
				url:"Controller",
				data:param,
				type:"post",
				dataType: "json", // json표기 법을 위해 jsp페이지를 하나 만들어 거기서 표현을 했다.
				}).done(function(data){
					// data안에 chk라는 변수의 값이 1이면 성공
					if(data.chk == 1){
						alert("저장 완료");
						location.href="Controller";
					}
					else
						alert("뭔가 잘못 되었다!")
				}).fail(function(err){
					console.log(err);
				});
				
			
		}
		function searchWin(){
			 $( "#search_popup" ).dialog();
		}
		function search(frm){
			// 유효성 검사
			if($("#searchType").val().trim() == "x"){
				alert("검색할 키워드를 선택해주세요");
				return;
			}
			if($("#searchValue").val().trim().length <= 0){
				alert("검색할 내용을 입력해주세요");
				$("#searchValue").val("");
				$("#searchValue").focus();
				return;
			}
			
			frm.submit();
		}
	</script>
</body>
</html>