<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    Member member = null;
    if (session != null && session.getAttribute("member") != null) {
       member = (Member) session.getAttribute("member");
    }
    %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>GYM CARRY 근딜 신청</title>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/geundealWrite.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
    
    <script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
    <link rel="stylesheet" href="../../../resources/css/summernote/summernote-lite.css">
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
            <a href="/member/update" role="button"><li>MY PAGE</li></a>
            <a href="#" role="button" id="msg-list"><li>MESSAGE</li></a>
         </ul>
         <%
         }
         %>
      </div>
	</header>
	<main>
	    <div class="frame">
	        <div class="inner-frame">
	            <span class="title">REQUEST GEUN DEAL</span>
                <div class="contents">
                    <form action="/geundeal/write" method="post" enctype="multipart/form-data">
                    <div id="topcon">
                        <span class="sub-title">업체 명</span>
                        <input class="text-box" type="text" placeholder="업체명을 입력하세요" name="partnerName"><br>
                        <span class="sub-title">업체 위치</span>
                        <input class="text-box" type="text" placeholder="업체위치(단어)를 입력하세요" maxlength="4" name="eventAdress"><br>
                        <span class="sub-title">이벤트 제목</span>
                        <input class="text-box" type="text" placeholder="이벤트 제목을 입력하세요" name="eventTitle"><br>
                        <span class="sub-title">이벤트 기간</span>
                        <input class="date-box" type="date" name="startDate"><p>~</p><input class="date-box" type="date" name="endDate"><br>
                    </div>
                        <span class="contitle">이벤트 내용</span>
                        <textarea id="summernote" name="eventContent"></textarea>
                        
                        <span class="contitle">사진 첨부 1</span>
                        <input class="file-box" type="file" name="upPic1" id="bizFile1">
                        <label for="bizFile1" class="fileBtn">파일선택</label>
						<span id="fileName1">선택된 파일없음</span><br>
						
						<span class="contitle">사진 첨부 2</span>
                        <input class="file-box" type="file" name="upPic2" id="bizFile2">
                        <label for="bizFile2" class="fileBtn">파일선택</label>
						<span id="fileName2">선택된 파일없음</span><br>
						
						<span class="contitle">사진 첨부 3</span>
                        <input class="file-box" type="file" name="upPic3" id="bizFile3">
                        <label for="bizFile3" class="fileBtn">파일선택</label>
						<span id="fileName3">선택된 파일없음</span><br>

                        <button class="smitbt" type="submit">신청하기</button>
                    </form>
                </div>
	        </div>
	    </div>
        <div class="line"></div>
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
$(document).ready(function() { 
	$('#summernote').summernote({
			width: 750,
    		height: 500,                // 에디터 높이
    		minHeight: null,            // 최소 높이
    		maxHeight: null,            // 최대 높이
    		focus: true,                // 에디터 로딩후 포커스를 맞출지 여부
    		lang: "ko-KR",				// 한글 설정
    		placeholder: '내용을 입력해주세요.', //placeholder 설정
    		toolbar: [
	  		    ['style', ['style']],
	  		    ['font', ['bold', 'italic', 'underline', 'clear']],
	  		    ['fontname', ['fontname']],
	  		    ['color', ['color']],
	  		    ['para', ['ul', 'ol', 'paragraph']],
	  		    ['height', ['height']],
	  		    ['table', ['table']],
	  		    ['insert', ['link', 'hr']],
	  		    ['view', ['fullscreen', 'codeview']],
	  		    ['help', ['help']]
  		  	]
      	});
 });
 
document.getElementById('bizFile1').addEventListener('change', function(){
	var filename = document.getElementById('fileName1');
	if(this.files[0] == undefined){
		filename.innerText = '선택된 파일없음';
		return;
	}else{
	filename.innerText = this.files[0].name;
		
	}
});
document.getElementById('bizFile2').addEventListener('change', function(){
	var filename = document.getElementById('fileName2');
	if(this.files[0] == undefined){
		filename.innerText = '선택된 파일없음';
		return;
	}else{
	filename.innerText = this.files[0].name;
		
	}
});
document.getElementById('bizFile3').addEventListener('change', function(){
	var filename = document.getElementById('fileName3');
	if(this.files[0] == undefined){
		filename.innerText = '선택된 파일없음';
		return;
	}else{
	filename.innerText = this.files[0].name;
		
	}
	var btn = document.getElementById("msg-list");
    btn.onclick = function() {
        var url = "/message/list";
             var name = "MESSAGE LIST";
             var option = "width=550,height=670,left=500,top=300,resizable=no, toolbars=no, menubar=no ,status=no";
       window.open(url, name, option);
    }

});

</script>
</html>