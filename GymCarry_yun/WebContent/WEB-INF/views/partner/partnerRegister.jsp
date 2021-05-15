<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <title>GYM CARRY 업체등록</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/partnerRegister.css">
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
         <ul class="login-box">
            <a href="/admin/memberlist" role="button"><li>MANAGE</li></a>
            <a href="/admin/eventlist" role="button"><li>CFM DEAL</li></a>
         </ul>
      </div>
	</header>
	<main>
	    <div class="frame">
	        <div class="inner-frame">
	            <span class="title">REGISTER PARTNER</span>
                <div class="contents">
                    <form action="/partner/upload" method="post" enctype="multipart/form-data">
                    <div id="topcon">
                        <span class="sub-title">운동 종류</span>
                        <div class="radio-box">
                            <label class="box-radio-input">
                                <input type="radio" name="category" value="헬스" checked="checked"><span>헬스</span>
                            </label>
                            <label class="box-radio-input">
                                <input type="radio" name="category" value="요가">
                                <span>요가</span>
                            </label>
                            <label class="box-radio-input">
                                <input type="radio" name="category" value="필라테스">
                                <span>필라테스</span>
                            </label>
                            <label class="box-radio-input last-radio">
                                <input type="radio" name="category" value="기타">
                                <span>기타</span>
                            </label>
                            <input type="text" name="category2" class="text-box short-box" placeholder="운동종류 입력">
                        </div>
                        <br>
                        <span class="sub-title">업체 명</span>
                        <input class="text-box" type="text" placeholder="업체명을 입력하세요" name="partnerName"><br>
                        <span class="sub-title">주소</span>
                        <input class="text-box" type="text" placeholder="주소를 입력하세요" name="partnerAddress"><br>
                         <span class="sub-title">지하철역</span>
                        <input class="text-box" type="text" placeholder="가까운 지하철역을 입력하세요" name="station"><br>
                        <span class="sub-title">가격대</span>
                        <input class="text-box" type="text" placeholder="가격대를 입력하세요" name="partnerPrice"><br>

                        <span class="contitle">가격사진 첨부 1</span>
                        <input class="file-box" type="file" name="upPricePic1" id="bizFile1">
                        <label for="bizFile1" class="fileBtn">파일선택</label>
						<span id="fileName1" class="filname">선택된 파일없음</span><br>
						
						<span class="contitle">가격사진 첨부 2</span>
                        <input class="file-box" type="file" name="upPricePic2" id="bizFile2">
                        <label for="bizFile2" class="fileBtn">파일선택</label>
						<span id="fileName2" class="filname">선택된 파일없음</span><br>
						
						<span class="contitle">가격사진 첨부 3</span>
                        <input class="file-box" type="file" name="upPricePic3" id="bizFile3">
                        <label for="bizFile3" class="fileBtn">파일선택</label>
						<span id="fileName3" class="filname">선택된 파일없음</span><br>

                        <span class="sub-title">영업 시간</span>
                        <input class="text-box" type="text" placeholder="영업시간을 입력하세요" name="partnerHours"><br>
                        <span class="sub-title">주차 여부</span>
                        <div class="radio-box">
                            <label class="box-radio-input">
                                <input type="radio" name="parking" value="y" checked="checked"><span>주차 가능</span>
                            </label>
                            <label class="box-radio-input">
                                <input type="radio" name="parking" value="n">
                                <span>주차 불가능</span>
                            </label>
                        </div>
                        <br>
                        <span class="sub-title">연락처</span>
                        <input class="text-box" type="text" placeholder="연락처를 입력하세요" name="partnerPhone"><br>
                    </div>
                        <span class="contitle">추가 내용</span>
                        <textarea id="summernote" name="addContent"></textarea>
                        
                        <span class="contitle">센터사진 첨부 1</span>
                        <input class="file-box" type="file" name="upPic1" id="bizFile4">
                        <label for="bizFile4" class="fileBtn">파일선택</label>
						<span id="fileName4" class="filname">선택된 파일없음</span><br>
						
						<span class="contitle">센터사진 첨부 2</span>
                        <input class="file-box" type="file" name="upPic2" id="bizFile5">
                        <label for="bizFile5" class="fileBtn">파일선택</label>
						<span id="fileName5" class="filname">선택된 파일없음</span><br>
						
						<span class="contitle">센터사진 첨부 3</span>
                        <input class="file-box" type="file" name="upPic3" id="bizFile6">
                        <label for="bizFile6" class="fileBtn">파일선택</label>
						<span id="fileName6" class="filname">선택된 파일없음</span><br>
                        <span class="contitle">대표강사 등록</span>
                        <div id="teacher">
                            <div class="teacher1">
                                <span class="teacher-title">강사명</span>
                                <input class="text-box" type="text" placeholder="강사명을 입력하세요" name="tchrName"><br>
                                <span class="teacher-title">과목명</span>
                                <input class="text-box" type="text" placeholder="과목명을 입력하세요" name="tchrClass"><br>
                                <span class="teacher-title">강사소개</span>
                                <input class="text-box" type="text" placeholder="강사소개를 입력하세요" name="tchrCareer"><br>
                                <!--
                                <span class="teacher-title">강사사진</span>
                                <input class="file-box" type="file" name="tchPic" id="bizFile3">
                                <label for="bizFile7" class="fileBtn fileBtnM">파일선택</label>
                                <span id="fileName7" class="filnameB">선택된 파일없음</span><br>
                                -->
                            </div>
                        </div>
<!--                        
							<div id="teacher">
                            <div class="teacher1">
                                <span class="teacher-title">강사명</span>
                                <input class="text-box" type="text" placeholder="강사명을 입력하세요" name="tchrName"><br>
                                <span class="teacher-title">과목명</span>
                                <input class="text-box" type="text" placeholder="과목명을 입력하세요" name="tchrClass"><br>
                                <span class="teacher-title">강사소개</span>
                                <input class="text-box" type="text" placeholder="강사소개를 입력하세요" name="tchrCareer"><br>
                                <span class="teacher-title">강사사진</span>
                                <input class="file-box" type="file" name="tchPic" id="bizFile3">
                                <label for="bizFile7" class="fileBtn fileBtnM">파일선택</label>
                                <span id="fileName7" class="filnameB">선택된 파일없음</span><br>
                            </div>
                        </div>
                        <div class="teacherList"></div>                     
							<div class="tchbtn-box">
                            <button class="tchbtn" id="plus">추가</button>
                            <button class="tchbtn">삭제</button>    
                        </div>
-->
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
});
document.getElementById('bizFile4').addEventListener('change', function(){
	var filename = document.getElementById('fileName4');
	if(this.files[0] == undefined){
		filename.innerText = '선택된 파일없음';
		return;
	}else{
	filename.innerText = this.files[0].name;
		
	}
});
document.getElementById('bizFile5').addEventListener('change', function(){
	var filename = document.getElementById('fileName5');
	if(this.files[0] == undefined){
		filename.innerText = '선택된 파일없음';
		return;
	}else{
	filename.innerText = this.files[0].name;
		
	}
});
document.getElementById('bizFile6').addEventListener('change', function(){
	var filename = document.getElementById('fileName6');
	if(this.files[0] == undefined){
		filename.innerText = '선택된 파일없음';
		return;
	}else{
	filename.innerText = this.files[0].name;
		
	}
});
document.getElementById('bizFile7').addEventListener('change', function(){
	var filename = document.getElementById('fileName7');
	if(this.files[0] == undefined){
		filename.innerText = '선택된 파일없음';
		return;
	}else{
	filename.innerText = this.files[0].name;
		
	}
});

function add_div(){
    var div = document.createElement('div');
    div.innerHTML = document.getElementById('teacher').innerHTML;
    document.getElementById('teacherList').appendChild(div);
}

function remove_div(obj){
document.getElementById('teacherList').removeChild(obj.parentNode);
}
</script>
<script>

</script>
</html>