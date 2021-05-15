<%@page import="member.model.vo.Member"%>
<%@page import="partner.model.vo.Partner"%>
<%@page import="review.model.vo.Review"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Review review = (Review)request.getAttribute("review");
	int partnerCode = (int)request.getAttribute("code");
	String partnerName = (String)request.getAttribute("partnerName");
	String userId = null;
	int uniqId = 0;
	if(session.getAttribute("userId") != null && session.getAttribute("uniqId") != null) {
		userId = (String)session.getAttribute("userId");
	 	uniqId = (int)session.getAttribute("uniqId");
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
<title>리뷰 작성</title>
</head>
<body>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="utf-8">

        <title>짐팟 리뷰작성</title>
        <link rel="stylesheet" type="text/css" href="/resources/css/reviewWrite.css">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js" ></script>
    </head>
    <body>
    <script>
        $(document).ready(function() {
           $('#rev_write').keyup(function (e){
                var content = $(this).val();
                $('#counter').html("("+content.length+" / 최대 400자)");    //글자수 실시간 카운팅
            });
           
        });
            
        function good(img) {
            if(img.checked) {
            	$('input:checkbox[id="badcheck"]').prop("disabled", false);
                img.src="/resources/img/good.png"
            	img.checked = false;
            } else {
                if(!$('input:checkbox[id="badcheck"]').is(":checked") == true) {
                	$('input:checkbox[id="badcheck"]').prop("disabled", true);
                    img.src="/resources/img/good-click.png"
                    $('input:checkbox[id="goodcheck"]').val('y');
                	img.checked = true;
                }
            }
        }
        // 비추천
        function bad(img) {
            if(img.checked) {
                $('input:checkbox[id="goodcheck"]').prop("disabled", false);
                img.src="/resources/img/bad.png"
                img.checked = false;
            } else {
                if(!$('input:checkbox[id="goodcheck"]').is(":checked") == true) {
                    $('input:checkbox[id="goodcheck"]').prop("disabled", true);
                    img.src="/resources/img/bad-click.png"
                    $('input:checkbox[id="badcheck"]').val('n');
                    img.checked = true;
                }
            }
        }
        
        function cnfReviewUpload() {
        	var chkVal;
            $('input[name=check]:checked').each(function(i, iVal) {
          	  chkVal = iVal.value;
          	  $('#rvRecommend').val(chkVal);
            });
            
            console.log($('#rvRecommend').val());
        	
           	var chkGood = $('input:checkbox[id="goodcheck"]').val();
           	var chkBad = $('input:checkbox[id="badcheck"]').val();
           	console.log(chkGood);
           	console.log(chkBad);
           	
           	if(chkGood=='on' && chkBad=='on') {
           		alert("추천 / 비추천 버튼을 눌러주세요.");
           		return false;
           		
           	} else if($('#rev_write').val().length==0) {
           		alert("리뷰 내용 작성은 필수입니다.");
       			return false;
       			
           	} else {
           		if(!confirm("허위 사실 기재 및 부적절한 리뷰 작성시 \n관리자에 의해 삭제될 수 있습니다.\n업로드 하시겠습니까?")) {
               		return false;
           		}
           		$('form#reviewUploadForm').submit();
           	}
        }
        
        function cnfReviewReset(){
        	if(!confirm("리뷰 작성을 취소하시겠습니까?")) {
           		return false;
       		}
       		history.back();
        }
        
    </script>
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
        
        
        <form id="reviewUploadForm" action="/review/write" method="post" enctype="multipart/form-data">
        <input type="hidden" id="rvRecommend" name="rvRecommend" >
        <input type="hidden" name="partnerCode" value="<%= partnerCode %>"> 
        <div class="content">
            <div class="title">
                <div id="name"><%= partnerName %></div>
                <div id="explan">에 대한 솔직한 리뷰를 써주세요.</div>
            </div>
            
            
            <div class="whether">
                
            <!-- checkbox label 값을 이미지로 출력 -->
            <div id="good">
                <input type="checkbox" name="check" id="goodcheck" style="display:none" >

                <label for="goodcheck"  class="check_img">

                <img src="/resources/img/good.png" id="good_img" onclick="good(this)">
                <div id="good_text">추천</div>
                    
            </label>
            </div>
                
            <div id="bad">
                <input type="checkbox"  name="check" id="badcheck" style="display:none">

                <label for="badcheck" class="check_img">

                <img src="/resources/img/bad.png" id="bad_img" onclick="bad(this)">
                <div id="bad_text">비추천</div>
                </label>    
            </div>
          
            </div>

            
                
            <div id="write_box">
                <textarea id="rev_write" name="rev_write" maxlength="400" cols="90" rows="12" placeholder="내용을 입력해주세요(400자 이내). "></textarea><br>
                 <span id="counter">( 0/ 최대 400자)</span>
            </div>
        
            
            <div id="attach">
                <div id="att-text">
                    사진첨부 (필수)
                </div>
                <div>
                	<input type="file" name="picture" id="att-btn">
                    <!-- <button id="att-btn"><div id="btn-text">찾기</div></button> -->
                </div>
            </div>
      	</form>
            
            
        <div class="btns">
            <input type="button" value="취소" id="reset-btn" onclick="cnfReviewReset()">
            <input type="button" value="리뷰 올리기" id="upload-btn" onclick="cnfReviewUpload()">
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