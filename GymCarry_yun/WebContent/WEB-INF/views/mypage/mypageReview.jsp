<%@page import="member.model.vo.Member"%>
<%@page import="review.model.vo.ReviewPic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="review.model.vo.Review"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
    	Review review = (Review) request.getAttribute("review");
    	ArrayList<Review> rList = (ArrayList<Review>)request.getAttribute("rList");
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
        <title>내가 쓴 리뷰</title>
        <link rel="stylesheet" type="text/css" href="/resources/css/mypageReview.css">
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
            });
                $("#deletebtn").on("click", function(){
                    var checkdel = confirm("정말 삭제하시겠습니까?");
                    if(checkdel == true){
                    		document.form.submit();
                    } else {
                        return false;
                    }
                });
        </script>
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
                <div class="mytitle">
                    <span>내가 쓴 리뷰</span>  
                </div>
                <div class="content-box">
                <% if(rList == null) { %>  
                        <div class="noContent">
                        		<img id="noC" src="/resources/img/NO.png">
                                <h3 id="review-content">내가 쓴 리뷰가 없어요</h3>
                                <button type="button" onclick="history.back(-1);">목록보기</button>
                        </div>                        
                <% }else{ %>
                <% for(Review rOne : rList) { %>
                    <div class="review-box">
                        <div class="review-title">
                       	 	<input type="hidden" name="partnerCode" value="<%= rOne.getPartnerCode()%>">
                            <span name="partner-name"><%= rOne.getPartnerName() %></span>
                        </div>
                        <div class="review-content">
                            <div>
                           
                                <img class="review-img" src="/resources/RUpload/<%=rOne.getFileName() %>" alt="<%=rOne.getFileName()%>">
                            </div>
                            <div class="review-text">
                                <span name="review-content"><%= rOne.getRvContent() %></span>
                            </div>
                        </div>
                        <div class="review-bottom">
                            <div class="recommend">
                            
                            	<% if(rOne.getRvRecommend().equals("y")) {%>
                                <img name="recom-img" class="recommend-img" src="${path}/resources/img/good.png"><span>추천</span>
                                <% } else { %>
                                <img name="recom-img" class="recommend-img" src="${path}/resources/img/bad.png"><span>비추천</span>
                                <% } %>
                            </div>
                            <div class="modifybtn">
                            	<form class="form" action="/review/modify" method="get">
                                	<input type="submit" value="수정하기">
                                	<input type="hidden" value="<%= rOne.getRvNo() %>" name="reviewNo">
                                </form>
                                <form class="form" action="/review/delete" method="get">
                                	<input id="deletebtn" type="submit" value="삭제하기">
                                	<input type="hidden" value="<%= rOne.getRvNo() %>" name="reviewNo">
                                </form>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div> 
                <% } %>
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
    </body>
</html>