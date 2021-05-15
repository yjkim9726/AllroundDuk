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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내 정보 보기</title>
<script type="text/javascript" src="/resources/js/enroll.js"></script> 
<link rel="stylesheet" type="text/css" href="/resources/css/mypageInfoModify.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

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
			var id =$('#id');
            console.log(id);
			var pwd = $('#pwd');
			var pwdch = $('#pwd-check');
			var name = $('#name');
			var email = $('#email');
			var phone = $('#phone');
			var addrcity = $('#addr-city');
			var addrgu = $('#addr-gu');

			var idreg = /^[a-z][a-z|A-Z|0-9]{4,12}$/;
			var pwdreg = /^[a-z|A-Z|0-9]{5,12}$/;
			var nicknamereg = /^[a-z|A-Z|0-9|가-핳]{2,8}/;
			var emailreg = /^[a-z|A-Z|0-9]{2,}@[a-z]+\.[a-z]+$/;
			var phonereg = /[0-9]{,8}&/;
            /* 
			$("#id").on("keyup", function(){
				if(!idreg.test(id.val())) {
					$("#message").text("아이디는 영소문자로 시작하며 대소문자,숫자를 포함한 4~12자리 입니다.");
	                return false;
	                }else {
	                    $("#message").text("사용가능한 아이디 입니다.");
	                }	
				}
			}); */
			$(pwd).on("keyup", function(){
				if(!pwdreg.test(pwd.val())) {
					$("#message").text("패스워드는 대소문자,숫자를 포함한 5~12자리 입니다.");
	                return false;
	                }else {
	                    $("#message").text("사용가능한 패스워드 입니다.");
	                }	
				
			});
			$(pwdch).on("keyup", function(){
				if(pwd.val() != pwdch.val()) {
					$("#message").text("패스워드가 일치하지 않습니다. 다시 확인해주세요.");
	                return false;
	                }else {
	                    $("#message").text("패스워드가 일치합니다.");
	                }	
				
			});
			/* $("#nickname").on("keyup", function(){
				if(!nicknamereg.test(nickname.val())) {
					$("#message").text("닉네임은 2글자이상 8자 이하입니다.");
	                return false;
	                }else {
	                    $("#message").text("사용가능한 아이디입니다.");
	                }	
				
			}); */
			$("#email").on("keyup", function(){
				if(!emailreg.test(email.val())) {
					$("#message").text("올바른 이메일 형식이 아닙니다. 다시 확인해주세요.");
	                return false;
	                }else {
	                    $("#message").text("올바른 이메일 입니다.");
	                }	
				
			});
			$("#phone").on("keyup", function(){
				if(!phonereg.test(phone.val())) {
					$("#message").text("번호는 8자이상 입력해주세요.");
	                return false;
	                }else {
	                    $("#message").text("올바른 번호 입니다.");
	                }	
				
			});
        }
		
	</script>
	<header>
		<div class="frame">
			<a href="/main"><img src="/resources/img/logo.png"></a>
			<ul class="navbar">
				<a href="gymstory.html"><li class="nav-menu first-nav">GYM
						STORY</li></a>
				<a href="/partner/search"><li class="nav-menu second-nav">GYM POT</li></a>
				<a href="/market/list"><li class="nav-menu third-nav">GYM MARKET</li></a>
				<a href="/guendeal/list"><li class="nav-menu fourth-nav">GEUN DEAL</li></a>
			</ul>
			<ul class="login-box">
					<a href="#" role="button" id="msg-list"><li>MESSAGE</li></a>

					<a href="/member/mypage" role="button"><li>MY PAGE</li></a>
				</ul>
		</div>
	</header>

	<div class="main">
		<div class="main-frame">
			<div class="title">
				<legend class="title-1">GYM CARRY</legend>
				<legend class="title-2">내 정보 보기</legend>
				
			</div>
			<div class="enroll">
				<div class="mesdiv">
                    <span class="message" id="message"></span>
                </div>

					<form class="enroll-form" method="post" action="/mypage/infomodify">
						<fieldset>
							<ul>
							    <li>아이디<a>*</a><br> <input type="text" id="id" name="user-id" readonly value="<%= member.getUserId() %>"></li>
								<li>비밀번호<a>*</a><br> <input type="password" id="pwd"
									name="user-pwd" required value="<%= member.getUserPwd() %>"></li> <br>
                                <li>비밀번호 확인<a>*</a><br> <input type="password" id="pwd-check"
                                    name="user-pwd-check" required></li> <br>
								<li>닉네임<a>*</a><br> <input type="text" id="nickname" name="nickname"
									readonly value="<%= member.getNickname() %>"></li> <br>
								<li>이름<a>*</a><br> <input type="text" id="name" name="user-name"
									required value="<%= member.getName() %>"></li> <br>							
								<li>이메일<a>*</a><br> <input type="text" name="email" id="email"
									required value="<%= member.getEmail() %>"></li> <br>
								<li>연락처<a>*</a><br> <input type="text" name="phone" id="phone"
									required value="<%= member.getPhone() %>"></li> <br>
								<li>주소(시/도)<a>*</a><br> <input type="text" id="addr-city"
									name="addr-city" required value="<%= member.getAddressCity() %>"></li> <br>
								<li>주소(구)<a>*</a><br> <input type="text" id="addr-gu"
									name="addr-gu" required value="<%= member.getAddressGu() %>"></li> <br>
                                <input type="submit" id="joinBtn" value="수정하기"> 
                                <input type="reset"id="cancelBtn" value="취소">
							</ul>
						</fieldset>
                   </form>
			</div>
		</div>
	</div>
	</form>

	<footer>
		<div class="frame">
			<a href="/main"><img src="/resources/img/wLogo.png"></a>
			<div class="footer-contents">
				<p>
					(주)올라운덕 <span class="stick"></span> 대표이사 올라운덕 <span class="stick"></span>
					사업자등록번호 123-45-67890
				</p>
				<p>
					서울특별시 종로구 종로대로 1000 <span class="stick"></span> 고객센터 080-1234-5678
					(수신자요금부담)
				</p>
			</div>
			<p class="copyright">
				<span>©</span>ALL ROUNDUK. All rights reserved.
			</p>
		</div>
	</footer>
</body>

</html>