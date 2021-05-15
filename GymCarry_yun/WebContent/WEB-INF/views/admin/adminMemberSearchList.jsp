<%@page import="member.model.vo.Member"%>
<%@page import="member.model.vo.Member"%>
<%@page import="event.model.vo.Event"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<Member> mList = (ArrayList<Member>)request.getAttribute("mList");
	String pageNavi = (String)request.getAttribute("pageNavi");
	String userId = (String)session.getAttribute("userId");
	int currentPage = (int)request.getAttribute("currentPage");
	String selectOption = (String)request.getAttribute("selectOption");
	String searchKeyword = (String)request.getAttribute("searchKeyword");
	Member memberId = null;
	   if (session != null && session.getAttribute("member") != null) {
	      memberId = (Member) session.getAttribute("member");
	   }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>짐캐리 소개</title>
        <link rel="stylesheet" type="text/css" href="/resources/css/adminMemberList.css">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
    </head>
    <body>
        <header>
            <div class="frame">
         <a href="/main"><img src="/resources/img/logo.png"></a>
         <ul class="navbar">
            <a href="/gymstory.jsp"><li class="nav-menu first-nav">GYM STORY</li></a>
            <a href="/partner/search"><li class="nav-menu second-nav">GYM POT</li></a>
            <a href="/member/list"><li class="nav-menu third-nav">GYM MARKET</li></a>
            <a href="/geundeal/list"><li class="nav-menu fourth-nav">GEUN DEAL</li></a>
         </ul>
         <!-- 로그인 하지 않았을 때 보여지는 nav버튼 -->
         <%
         if (memberId == null) {
         %>
         <ul class="login-box">
            <a href="/member/login" role="button"><li>LOGIN</li></a>
            <a href="/member/enroll" role="button"><li>JOIN</li></a>
         </ul>
         <!-- 로그인 했을 때 보여지는 nav버튼(admin) -->
         <%
         } else if (memberId != null && memberId.getUserId().equals("admin")) {
         %>
         <ul class="login-box">
            <a href="/admin/memberlist" role="button"><li>MANAGE</li></a>
            <a href="/admin/eventlist" role="button"><li>CFM DEAL</li></a>
         </ul>
         <!-- 로그인 했을 때 보여지는 nav버튼(user) -->
         <%
         } else {
         %>
         <ul class="login-box">
            <a href="/member/mypage" role="button"><li>MY PAGE</li></a>
            <a href="#" role="button" id="msg-list"><li>MESSAGE</li></a>

         </ul>
         <%
         }
         %>
      </div>
        </header>
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
	                    	<select class="selectbox"  name="selectOption">
	                    		<option value="id">아이디</option>
								<option value="nickname">닉네임</option>
	                    	</select>
	                        <input type="text" name="searchKeyword" placeholder="검색어 입력">
	                        <button type="submit" value="검색">검색</button>
	                    </form>
                    </div>
                </div>
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
                        <td><%= member.getUserId() %></td>
                        <td><%= member.getNickname() %></td>
						<td><%= member.getName() %></td>
						<td><%= member.getAddressCity() %></td>
						<td><%= member.getAddressGu() %></td>
                        <!-- $$$$$$$$$$$$$$$$$$4 삭제 $$$$$$$$$$$$$$$$$$$$$$$ -->
                        <td class="deletetd">
	                        <form action="/admin/memberdelete" method="get">
	                        	<button type="submit" id="cfbtn" class="cfbtn"  onclick="return button_event();">삭제</button>
	                        	<input type="hidden" value="<%= member.getUniqId() %>" name="memberNo"> 
	                        	<input type="hidden" value="Y" name="searchYn">
	                        	<input type="hidden" value="<%= currentPage %>" name="currentPage">
	                        	<input type="hidden" value="<%= selectOption %>" name="selectOption">
	                        	<input type="hidden" value="<%= searchKeyword %>" name="searchKeyword">
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
                    <p>(주)올라운덕	<span class="stick"></span>	대표이사 올라운덕	<span class="stick"></span>	사업자등록번호 123-45-67890</p>
                    <p>서울특별시 종로구 종로대로 1000  	 <span class="stick"></span>	고객센터 080-1234-5678 (수신자요금부담)</p>
                </div>
                <p class="copyright"><span>©</span>ALL ROUNDUK. All rights reserved.</p>
            </div>
        </footer>
        
    </body>
    <script>
    window.onload = function() {
        var btn = document.getElementById("msg-list");
        btn.onclick = function() {
            var url = "/message/list";
                 var name = "MESSAGE LIST";
                 var option = "width=550,height=670,left=500,top=300,resizable=no, toolbars=no, menubar=no ,status=no";
           window.open(url, name, option);
        }
  
    	function button_event(){
			if (confirm("정말 삭제하시겠습니까?")) {
				document.form.submit();
			}else {
				return false;
			}
		}
     }
    </script>
</html>