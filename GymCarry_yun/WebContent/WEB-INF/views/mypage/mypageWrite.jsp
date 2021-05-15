<%@page import="member.model.vo.Member"%>
<%@page import="market.model.vo.Market"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
   		ArrayList<Market> List = (ArrayList<Market>)request.getAttribute("mList");
    	String userId = (String)session.getAttribute("userId");
    	String pageNavi = "";
    	int currentPage = 1;	
    	   if(List != null) {
   	    	pageNavi = (String)request.getAttribute("pageNavi");
   	    	currentPage = (int)request.getAttribute("currentPage");		
       	}
    	Member member = null;
    	   if (session != null && session.getAttribute("member") != null) {
    	      member = (Member) session.getAttribute("member");
    	   }

    %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
        <title>내가 쓴 게시글</title>
        <link rel="stylesheet" type="text/css" href="/resources/css/mypageWrite.css">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
    </head>
    <body>
        <script>
            $(document).ready(function(){
            	var btn = document.getElementById("msg-list");
    			btn.onclick = function() {
    				 var url = "/message/list";
    		            var name = "MESSAGE LIST";
    		            var option = "width=550,height=670,left=500,top=300,resizable=no, toolbars=no, menubar=no ,status=no";
    				window.open(url, name, option);
    			}
                $(".delete").on("click",function() {
                	var del_check = confirm("삭제하시겠습니까? (한번삭제는 영원한 삭제~)");
                	if(del_check == true){
                		document.form.submit();
                	}else {
                		return false;
                	}
                });
            });
        </script>
        <div id="wrapper">
            <header id="header">
                <!-- 네이게이션 바 -->
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
            <!-- 메인 시작################# -->
            <main>
                <div class="inner-frame"> 
                    <div class="mylike-title">
                        <span class="mytitle">내가 쓴 게시글</span>
                    </div>
                    <% if(List == null) {%>
                    <div class="contain-table">
                        <table>
                            <tr class="list-header">
                                <th class="header-no">NO</th>
                                <th class="header-title">TITLE</th>
                                <th class="header-price">PRICE</th>
                                <th class="header-name">DATE</th>
                                <th class="header-modify"></th>
                                <th class="header-delete"></th>
                            </tr>
                         </table>
                      </div>
                        <div class="noContent">
                        		<img id="noC" src="/resources/img/NO.png">
                                <h3 id="review-content">내가 쓴 게시글이 없어요</h3>
                                <button type="button" onclick="history.back(-1);">목록보기</button>
                        </div>     
                      <% } else { %>
                      	<div class="contain-table">
                        <table>
                            <tr class="list-header">
                                <th class="header-no">NO</th>
                                <th class="header-title">TITLE</th>
                                <th class="header-price">PRICE</th>
                                <th class="header-name">DATE</th>
                                <th class="header-modify"></th>
                                <th class="header-delete"></th>
                            </tr>
                            <% for(Market mOne : List){ %>
	                            <tr class="list-contain">
	                                <th><%= mOne.getNum()  %></th>
	                                <th class="contain-title"><a href="/market/detail?marketNo=<%= mOne.getMarketNo()%>"><%= mOne.getMarketTitle() %></a></th>
			                        <th><%= mOne.getMarketPrice() %></th>
			                        <th><%= mOne.getMarketDate() %></th>
			                        <th>
			                        	<form action="/market/modify" method="get">
	                                		<input class="btn" type="submit" value="수정">
	                                		<input type="hidden" value="<%= mOne.getMarketNo() %>" name="marketNo">
	                                	</form>
	                                </th>
	                                <th>
	                                	<form action="/market/delete" method="get">
		                               		<input class="btn delete" type="submit" value="삭제">  
		                               		<input type="hidden" value="<%= mOne.getMarketNo() %>" name="marketNo">
		                   					<input type="hidden" value="<%= currentPage %>" name="currentPage">
	                                	</form>
	                                </th> 
	                            </tr>
	                        <% } %>
                        </table>
                   	 </div>
                    <% } %>
                    <div class="paging">
                            <%= pageNavi %>
                    </div>
                </div>
            </main>
            
            <footer>
                <div class="frame">
                    <a href="/main"><img src="${path}/resources/img/wLogo.png"></a>
                    <div class="footer-contents">
                        <p>(주)올라운덕	<span class="stick"></span>	대표이사 올라운덕	<span class="stick"></span>	사업자등록번호 123-45-67890</p>
                        <p>서울특별시 종로구 종로대로 1000  	 <span class="stick"></span>	고객센터 080-1234-5678 (수신자요금부담)</p>
                    </div>
                    <p class="copyright"><span>©</span>ALL ROUNDUK. All rights reserved.</p>
                </div>
            </footer>

        </div>
    </body>
</html>