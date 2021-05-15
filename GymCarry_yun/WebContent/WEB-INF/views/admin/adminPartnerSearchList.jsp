<%@page import="member.model.vo.Member"%>
<%@page import="partner.model.vo.Partner"%>
<%@page import="event.model.vo.Event"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<Partner> pList = (ArrayList<Partner>)request.getAttribute("pList");
	String pageNavi = (String)request.getAttribute("pageNavi");
	String userId = (String)session.getAttribute("userId");
	String selectOption = (String)request.getAttribute("selectOption");
	String searchKeyword = (String)request.getAttribute("searchKeyword");
	int currentPage = (int)request.getAttribute("currentPage");
	Member member = null;
   if (session != null && session.getAttribute("member") != null) {
      member = (Member) session.getAttribute("member");
   }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>짐캐리 소개</title>
        <link rel="stylesheet" type="text/css" href="/resources/css/adminPartnerList.css">
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
            <a href="/market/list"><li class="nav-menu third-nav">GYM MARKET</li></a>
            <a href="/geundeal/list"><li class="nav-menu fourth-nav">GEUN DEAL</li></a>
         </ul>
         <!-- 로그인 하지 않았을 때 보여지는 nav버튼 -->
         <%
         if (member == null) {
         %>
         <ul class="login-box">
            <a href="/member/login" role="button"><li>LOGIN</li></a>
            <a href="/member/enroll" role="button"><li>JOIN</li></a>
         </ul>
         <!-- 로그인 했을 때 보여지는 nav버튼(admin) -->
         <%
         } else if (member != null && member.getUserId().equals("admin")) {
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
                        <a href="/admin/memberlist"><button>회원관리</button></a>
                        <a href="#"><button class="pick">업체관리</button></a>
                        <a href="/admin/marketlist"><button>짐마켓관리</button></a>
                        <a href="/admin/eventlist"><button>근딜관리</button></a>
                    </div>
                    <div id="srchbox">
	                    <form action="/admin/partnersearch" method="get">
	                    	<select class="selectbox"  name="selectOption">
	                    		<option value="partnerCode">업체코드</option>
	                    		<option value="category">종목</option>
	                    		<option value="partnerName">업체이름</option>
	                    	</select>
	                        <input type="text" name="searchKeyword" placeholder="검색어 입력">
	                        <button type="submit" value="검색">검색</button>
	                    </form>
                    </div>
                </div>
                <div id="conbox">
                    <table>
					<tr class="tableheader">
						<td>NO</td>
						<td>CODE</td>
						<td>CATEGORY</td>
						<td>NAME</td>
						<td>REVIEW</td>
						<td>CONFIRM</td>
						<td>DELETE</td>
					</tr>
					<% for(Partner partner : pList) { %>
					<tr>
                        <td><%= partner.getNum() %></td>
                        <td><%= partner.getPartnerCode() %></td>
                        <td><%= partner.getPartnerType() %></td>
                        <td><a href="/partner/detail?partnerNo=<%= partner.getPartnerCode() %>"><%= partner.getPartnerName() %></a></td>
                        <td>
                        	<form action="/admin/partnerreview" method="get">
	                        	<button type="submit" id="cfbtn" class="cfbtn" onclick="location.href='#'">리뷰</button>
       		                </form>
                        </td>
                        <td>
                        	<form action="/admin/partnermodify" method="get">
	                        	<button type="submit" id="cfbtn" class="cfbtn">수정</button>
       		                </form>
                        </td>
                        <td>
                        	<form action="/admin/partnerdelete" method="get">
	                        	<button type="submit" id="cfbtn" class="cfbtn"  onclick="return button_event();">삭제</button>
	                        	<input type="hidden" value="Y" name="searchYn">
	                        	<input type="hidden" value="<%= selectOption %>" name="selectOption">
	                        	<input type="hidden" value="<%= searchKeyword %>" name="searchKeyword">
	                        	<input type="hidden" value="<%= partner.getPartnerCode() %>" name="partnerCode">
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