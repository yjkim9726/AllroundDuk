<%@page import="member.model.dao.MemberDAO"%>
<%@page import="member.model.vo.Member"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ArrayList<Member> mList = (ArrayList<Member>)request.getAttribute("mList");
	String pageNavi = (String)request.getAttribute("pageNavi");
	String userId = (String)session.getAttribute("userId");
	int currentPage = (int)request.getAttribute("currentPage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GYMCARRY 짐캐리</title>
<link rel="stylesheet" type="text/css"
	href="/resources/css/adminMemberList.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">
</head>
<body>
	<header>
		<div class="frame">
			<a href="/main"><img src="${path}/resources/img/logo.png"></a>
			<ul class="navbar">
				<a href="/gymstory.jsp"><li class="nav-menu first-nav">GYM STORY</li></a>
				<a href="/partner/search"><li class="nav-menu second-nav">GYM POT</li></a>
				<a href="/market/list"><li class="nav-menu third-nav">GYM MARKET</li></a>
				<a href="/geundeal/list"><li class="nav-menu fourth-nav">GEUN
						DEAL</li></a>
			</ul>
			<ul class="login-box">
				<a href="/admin/memberlist" role="button"><li>MANAGE</li></a>
				<a href="/admin/eventlist" role="button"><li>CFM DEAL</li></a>
			</ul>
		</div>
	</header>

	<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ 상단 버튼 및 검색 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
	
	<main>
		<div class="frame">
			<div class="topbox">
				<div id="btnbox">
					<a href="/admin/memberlist"><button class="pick">회원관리</button></a>
					<a href="/admin/partnerlist"><button>업체관리</button></a> 
					<a href="/admin/marketlist"><button>짐마켓관리</button></a> 
					<a href="/admin/eventlist"><button>근딜관리</button></a>
				</div>
				<div id="srchbox">
					<form action="/admin/membersearch" method="get">
						<select class="selectbox" name="selectOption">
							<option value="id">아이디</option>
							<option value="nickname">닉네임</option>
						</select> 
						<input type="text" name="searchKeyword" placeholder="검색어를 입력하세요">
						<button type="submit" class="searchbtn" value="검색">검색</button>
					</form>
				</div>
			</div>
			
	<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ 리스트 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
	
			<div id="conbox">
				<table>
					<tr class="tableheader">
						<td>ID</td>
						<td>NICKNAME</td>
						<td>NAME</td>
						<td>CITY</td>
						<td>AREA</td>
						<td>DELETE</td>
					</tr>
					<% for(Member member : mList) { %>
					<tr>
						<td class="userId" id="userId" name="userId"><%= member.getUserId() %></td>
						<td><%= member.getNickname() %></td>
						<td><%= member.getName() %></td>
						<td><%= member.getAddressCity() %></td>
						<td><%= member.getAddressGu() %></td>
						<!-- ++++++++++++++++++++삭제++++++++++++++++++++++++ -->
						<td class="deletetd">
							<form action="/admin/memberdelete" method="post">
								<button type="submit" id="cfbtn" class="cfbtn" onclick="return button_event();">삭제</button>
								<input type="hidden" value="<%= member.getUniqId() %>" name="memberNo"> 
								<input type="hidden" value="N" name="searchYn"> 
								<input type="hidden" value="<%= currentPage %>" name="currentPage">
							</form>
						</td>
					</tr>
					<% } %>
				</table>
				<div class="paging">
					<%= pageNavi %>
				</div>
			</div>
		</div>
	</main>
	<footer>
		<div class="frame">
			<a href="/main"><img src="/resources/img/wLogo.png"></a>
			<div class="footer-contents">
				<p>
					(주)올라운덕 <span class="stick"></span> 대표이사 올라운덕 <span class="stick"></span>
					사업자등록번호 123-45-67890
				</p>
				<p>
					서울특별시 종로구 종로대로 1000 <span class="stick"></span> 고객센터 080-1234-5678
					(수신자요금부담)
				</p>
			</div>
			<p class="copyright">
				<span>©</span>ALL ROUNDUK. All rights reserved.
			</p>
		</div>
	</footer>

</body>
<script>
    	function button_event(){
			if (confirm("정말 삭제하시겠습니까?")) {
				document.form.submit();
			}else {
				return false;
			}
		}
    </script>
</body>
</html>