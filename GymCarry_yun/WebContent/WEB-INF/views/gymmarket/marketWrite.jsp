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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>

<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>

<title>양도권 게시글 작성</title>
<link rel="stylesheet" href="/resources/css/summernote/summernote-lite.css">
<link rel="stylesheet" type="text/css" href="/resources/css/marketWrite.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
</head>
<body>
    <script>
         $(document).ready(function(){
          	  
            	var title = $('.title');
            	var price = $('.price');
          
            	
            	$(title).on("keyup",function(){
            		 if(title.val() == "") {
                		$("#message").text("제목을 입력해주세요.");
                		return false;
                	}else {
                		$("#message").text("");
                	}
            	});
            	$(price).on("keyup",function(){
            		if(price.val() == "") {
                		$("#message").text("가격을 입력해주세요.");
                		return false;
                	}else {
                		$("#message").text("");
                	}
            	});
            
          	/* $('.sub').on("click", function(){
                var go_upload = confirm("허위사실 기재 및 부적절한 게시글 작성시 관리자에 의해 삭제될 수 있습니다. 업로드 하시겠습니까?")
                if(go_upload == true) {
                    var form = document.marketwriteForm;
                    form.submit();
                }else if(go_login == false) {
                	//location.replace("/market/write");
                }
            }); */
            
            $('#summernote').summernote({
      		  height: 400,                 // 에디터 높이
      		  width: 585,
      		  minHeight: 200,             // 최소 높이
      		  maxHeight: 300,             // 최대 높이
      		  focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
      		  lang: "ko-KR",					// 한글 설정
      		  placeholder: '내용을 입력해주세요.' ,	//placeholder 설정
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
            
            var btn = document.getElementById("msg-list");
            btn.onclick = function() {
                var url = "/message/list";
                     var name = "MESSAGE LIST";
                     var option = "width=550,height=670,left=500,top=300,resizable=no, toolbars=no, menubar=no ,status=no";
               window.open(url, name, option);
            }

        })
    </script>
    <div id="wrapper">
        <header id="header">
            <div class="frame">
         <a href="/main"><img src="resources/img/logo.png"></a>
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
                <form action="/market/write" method="POST" name="marketwriteForm" enctype="multipart/form-data">
                    <table>
                        <tr>
                            <th class="table-head" colspan="2">게시글 작성</th>
                        </tr>
                        <tr>
                        	<th class="" colspan ="2"><span id="message" style="color:#690000;"></span></th>
                        </tr>
                        <tr>
                            <td class="tabletext">제목</td>
                            <td><input type="text" class="textbox title" name="title" placeholder=" 제목을 입력하세요." required></td>
                        </tr>
                        <tr>
                            <td class="tabletext">운동종목</td>
                            <td> 
	                           <input type="radio" name="field" class="field" value="필라테스" checked><label for="f" >필라테스</label> <input type="radio" name="field"class="field" value="헬스"><label for="h">헬스</label> <input type="radio" name="field" class="field" value="기타" ><label for="o"> 기타</label>
				        	</td>
                        </tr>
                        <tr>
                            <td class="tabletext">희망가격</td>
                            <td><input type="text" class="textbox price" name="price" placeholder=" 가격을 입력하세요." required></td>
                        </tr>
                        <tr>
                            <td class="tabletext">내용</td>
                            <td><textarea id="summernote" class="content" name="editordata" required></textarea></td>
                        </tr>
                         <tr>
                            <td class="tabletext">사진첨부1</td>
                            <td><input type="file" name="picture1"></td>
                        </tr>
                        <tr>
                            <td class="tabletext">사진첨부2</td>
                            <td><input type="file" name="picture2"></td>
                        </tr>
                        <tr>
                            <td class="tabletext">사진첨부3</td>
                            <td><input type="file" name="picture3"></td>
                        </tr> 
                        <tr>
                            <td colspan="2" class="table-go" ><input type="submit" class="buttoms sub" name=submit value="올리기"> <input type="reset" class="buttoms"name=reset value="취소"></td>
                            
                        </tr>
                    </table>
            </form>
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
</html>]