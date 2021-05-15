<%@page import="partner.model.vo.PricePic"%>
<%@page import="member.model.vo.Member"%>
<%@page import="review.model.vo.ReviewPic"%>
<%@page import="partner.model.vo.PartnerPic"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="partner.model.vo.Partner"%>
<%@page import="teacher.model.vo.Teacher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="review.model.vo.Review"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<Review> rList = (ArrayList<Review>)request.getAttribute("rList");
	ArrayList<Teacher> tList = (ArrayList<Teacher>)request.getAttribute("tList");
	ArrayList<PartnerPic> pPics =  (ArrayList<PartnerPic>)request.getAttribute("pPics");
	ArrayList<PricePic> pricePics = (ArrayList<PricePic>)request.getAttribute("pricePics");
////추가
	ArrayList<ReviewPic> rPics = null;
	if((ArrayList<ReviewPic>)request.getAttribute("rPics") != null) {
		rPics =  (ArrayList<ReviewPic>)request.getAttribute("rPics");
	}
////
	Partner partner = (Partner)request.getAttribute("partner");
	String pageNavi = (String)request.getAttribute("pageNavi");
	int rCount = (int)request.getAttribute("rCount");
	Member member = null;
	String userId = null;
	if (session != null && session.getAttribute("member") != null) {
	   member = (Member) session.getAttribute("member");
	   userId = member.getUserId();
	}
	
	

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>짐팟 상세 페이지</title>
        <link rel="stylesheet" type="text/css" href="/resources/css/potdetail.css">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
         <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js" ></script>
  		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/css/lightbox.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/js/lightbox.min.js"></script>
		    
		<!--지도-->
		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d1fb489ef2c93c4cee862199d8eeac6a&libraries=services"></script>
		<script type="text/javascript" src="/resources/script/gymmap-detail.js"></script>	
    </head>
    
    <script>
    	function cnfUser() {
    		if('<%= userId %>' == 'null') {
    			alert("로그인 후 이용하시기 바랍니다.");
    			return false;
    		} else {
	    		$('form#reviewForm').submit();
    		} 
    	}
    	
    	// 팝업창
    	$(document).ready(function(){
            $('a[rel="lightbox"]').colorbox();
        });
    	
    	//지도데이터에 들어갈 변수
    	mapdata = new Object();
    	mapdata.name = '<%= partner.getPartnerName() %>';
    	mapdata.address = '<%= partner.getPartnerAddress() %>';
    	mapdata.code = <%= partner.getPartnerCode() %>;
    </script>
    	
    <body>

      <div class="frame">
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
        
        
        <% 
      	//이미지 3개를 불러올 건데 이미지가 없을 수 도 있기 때문에
    	//예외처리를 해줍니다
        String url1 = "#"; String url2 = "#"; String url3 = "#";
        try {
        	url1 = "/resources/PUpload/" + pPics.get(0).getFileName();
        } catch (IndexOutOfBoundsException e) {
        	url1 = "/resources/img/no-image.jpg";
        }
        try {
        	url2 = "/resources/PUpload/" + pPics.get(1).getFileName();
        } catch (IndexOutOfBoundsException e) {
        	url2 = "/resources/img/no-image.jpg";
        }
        try {
        	url3 = "/resources/PUpload/" + pPics.get(2).getFileName();
        } catch (IndexOutOfBoundsException e) {
        	url3 = "/resources/img/no-image.jpg";
        }
        %>
        <div class="main-img">
            <div>
                <img src="<%= url1 %>" id="main-img1" >
            </div>
            <div>
                <img src="<%= url2 %>" id="main-img2">
            </div>
            <div>
                <img src="<%= url3 %>" id="main-img3">
            </div>
        </div>
        
        
        <div class="contents">
            <div class="con-left">
                <div class="detil">
                    <div id="title">
                    	<div class="left-title">
      	                    <div id="kind"><%=partner.getPartnerType() %></div>
                        	<div id="name"><%=partner.getPartnerName() %></div>
                    	</div>
                    	<div class="right-title">
                        <form id="reviewForm" action="/review/write" method="get">
                        	<input type="hidden" name="code" value="<%= partner.getPartnerCode() %>">  
                        	<input type="hidden" name="partnerName" value="<%= partner.getPartnerName() %>">                     
                        </form>
                            <div id="write">
                        	  <button onclick="cnfUser();" id="write-icon">
	                            <img src="/resources/img/review.png" id="write-img">
	                            <div id="rev-text">리뷰쓰기</div>
                                </button>
                            </div>
                    	</div>
                    </div>
                    <hr color="lightgray" size="3px" width="890px" >
                    <div id="group">
                        <p>주소:</p>
                        <div id="span1">가격대:</div>
                        <div id="span2">영업시간: </div>
                        <p>주차여부:</p>
                        <p>연락처: </p>
                        <p>추가 내용: </p>
                    </div>
                    <div id="group-cont">
                        <div id="address">
                        	<%= partner.getPartnerAddress() %>
                        </div>
                        <div class="price">
                            <div id="price-text">
                                <%= partner.getPartnerPrice() %>
                            </div>
                            <div id="price-img">
                            <% for(PricePic pricePicture : pricePics) { %>
                                <a rel="lightbox" href="/resources/PUpload/<%= pricePicture.getFileName() %>" >
                                <img src="/resources/PUpload/<%= pricePicture.getFileName() %>" id="p-img1">
                                </a> 
							<% } %>                       
                            </div>
                        </div>
                        </div>
                        <div id="hours">
                            <%= partner.getPartnerHours() %>
                        </div>
                        <div id="parking">
                        <%! String commend =""; %>
                            	<% if( partner.getPartnerParking().charAt(0) == 'y'  ) { %>
                            		<% commend = "가능"; %>
                            		<% } else { %>
                            			<% commend = "불가능"; %>
                            		<% } %>
                            			<%= commend %>
                        </div>
                        <div id="tel"><%= partner.getPartnerPhone() %></div>
                        <div id=add-content><%=partner.getAddContent() %>
                        </div>
                    </div>
                
                <hr color="lightgray" size="3px" width="890px">
                <div class="review">
                    <div class="top">
                        <div id="rev-count">
                            리뷰(<%= rCount %>)
                        </div>
                        <div class="rev-sort">
                           <!--  <ul>
                                <li><a href="#">전체</a></li>
                                <li><a href="#">추천</a></li>
                                <li><a href="#">비추천</a></li>
                            </ul> -->
                        </div>
                    </div>
                    <div id="review-cont">
                    <% for(int i = 0; i < rList.size(); i++) { %>
                        <div class="review1">
                            <div id="rev-left">
                                <div id="top">
                                     <div id="user">
                                        <div id="nickname"><%= rList.get(i).getNickName() %> 
                                        </div>
                                        <div id="user-text">님
                                        </div>         
                                     </div> 
                                    <div id="date">
                                    
                                    <%= rList.get(i).getRvDate().substring(0,10) %></div>
                                </div>
                                <% String reviewURL="/resources/RUpload/" +  rPics.get(i).getFileName(); %>
                                <a rel="lightbox" href="<%= reviewURL %>">
                                	<input type="hidden" name="rvNo" value="<%= rList.get(i).getRvNo() %>">
                                	<!-- 리뷰이미지 -->
                                	<img id="rev-img" src="<%= reviewURL %>">
                                </a>
                                <div id="rev-content">
									<%= rList.get(i).getRvContent() %>
                                </div>
                            </div>
                            <div id="rev-right">
                            <%! String recommend =""; %>
                            <%! String url =""; %>
                            	<% if( rList.get(i).getRvRecommend().charAt(0) == 'y'  ) { %>
                            		<% recommend = "추천"; %>
                            		<% url = "/resources/img/good.png"; %>
                            		<% } else { %>
                            			<% recommend = "비추천"; %>
                            			<% url = "/resources/img/bad.png"; %>                        			
                            		<% } %>
                            		<img src=<%= url %> id="sort-icon">
                            		<div id="icon-text">
                            			<%= recommend %>
                            		</div>

                            </div> 
                        </div>
                     	 <% } %> 
                        
                    </div>
                </div>
                <div>
                    <ul class="page-navi">
                        <li><%= pageNavi %></li>
                    </ul>
                </div>
            </div>
            
            
            <div class="con-right">
                <div class="map" id="map">
                    <!-- <img src="/resources/img/map-img.png" id="map-img"> -->
                </div>
            <span>대표강사</span>
                <div class="list">
                <% for(Teacher teacher : tList) { %>
                    <div id="teacher">
                        <div>
                            <img src="/resources/img/teacher.jpg" id="teacher-img">
                        </div>
                        <div id="teacher-li">
                            <div id="class"><%=teacher.getTchClass() %></div>
                            <div id="t-name"><%=teacher.getTchName() %></div>
                            <div id="career-text">경력</div>
                            <div id="career"><%=teacher.getTchrCareer() %> </div>
                        </div>  
                    </div>
                <% } %>
                </div>
            </div>
            </div>
  
        
        <footer>
            <div class="footer">
                <a href="/main"><img src="/resources/img/wLogo.png"></a>
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