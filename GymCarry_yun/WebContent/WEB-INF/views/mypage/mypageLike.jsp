<%@page import="like.model.vo.Like"%>
<%@page import="market.model.vo.Market"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<Market> LList = (ArrayList<Market>) request.getAttribute("LList");
	String pageNavi = (String)request.getAttribute("pageNavi");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
        <title>내가 찜한 목록</title>
        <link rel="stylesheet" type="text/css" href="/resources/css/mypageLike.css">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="/resources/css/mypageLike.css">
    </head>
    <body>
        <script>
        window.onload = function() {
			var btn = document.getElementById("msg-list");
			btn.onclick = function() {
				 var url = "/message/list";
		            var name = "MESSAGE LIST";
		            var option = "width=550,height=670,left=500,top=300,resizable=no, toolbars=no, menubar=no ,status=no";
				window.open(url, name, option);
			}
			
			$(".overall-checkbox").on("change", function(){
	                var delcheck = $("input[type=checkbox]"); 
	                console.log(delcheck);
	                if($(this).is(":checked")) {
	                    delcheck.prop("checked", true);
	                }else {
	                    delcheck.prop("checked", false);
	                }
	        });
	        /* $("#deletebtn").on("click", function(){
	            var checkdel = confirm("정말 삭제하시겠습니까?");
	            if(checkdel == true){
	                location.href = "/market/delete?marketNo=";
	            } else {
	                return false;
	            }
	        }); */
		}
          
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
			<ul class="login-box">
				<a href="#" role="button" id="msg-list"><li>MESSAGE</li></a>
				<a href="/member/mypage" role="button"><li>MY PAGE</li></a>
			</ul>
                </div>
            </header>
            <!-- 메인 시작################# -->
            <main>
                <div class="inner-frame"> 
                    <div class="mylike-title">
                        <span class="mytitle">내가 찜한 목록</span>
                    </div>
                    <form action="/like/delete" method="post">
                    <div class="contain-table">
                        <table>
                            <tr class="list-header">
                                <th class="header-no">NO</th>
                                <th class="header-title">TITLE</th>
                                <th class="header-price">PRICE</th>
                                <th class="header-name">NICKNAME</th>
                                <th class="hearder-delete"><input type="checkbox" class="overall-checkbox"></th>
                            </tr>
                            <% for(Market market : LList) { %>
                            <tr class="list-contain">
                                <th><%= market.getNum() %></th>
                                <th class="contain-title">
                                <a href="/market/detail?marketNo=<%= market.getMarketNo()%>">
                                <%= market.getMarketTitle() %></a></th>
                                <th><%= market.getMarketPrice() %></th>
                                <th><%= market.getNickName() %></th>
                                <th><input type="checkbox" name="checkRow" value="<%= market.getMarketNo()%>"></th>
                            </tr>
                            <% } %>
                        </table>
                    </div>
                    <div class="deletebtn">
<!--                         <form action="/market/like" method="get">
                            <input type="submit" name="delete" id="deletebtn" value="삭제하기">
                        </form>  --> 
                        <a href="/like/delete?marketNo=">
						<input type="submit" value="삭제" id="deletebtn">
						</a>
                    </div>
                    </form>
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