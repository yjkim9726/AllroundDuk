<%@page import="message.model.vo.Message"%>
<%@page import="market.model.vo.Market"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Message message = (Message) request.getAttribute("message"); 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>REPLY MESSAGE</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/jquery-3.5.1.min.js"></script>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Anton&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">
	<link rel="stylesheet" type="text/css"
	href="/resources/css/messageReply.css">
</head>
<body>
	<div class="dialog">
	<button class="back-button" onclick="history.back()"><span class="dialog__back">&#11013;</span></button>
		<span class="dialog__close" onclick="fn_popClose();">&#x2715;</span>
		<h2 class="dialog__title">REPLY MESSAGE</h2>
		<br>
		<form action="/message/reply" method="post">
			<div class="write-box">
				<div class="write-top">
					<span class="to">TO.</span> <span class="user-id"><%=message.getNickName()%></span>
					<input type="hidden" name="uniqId"
						value="<%=message.getSenderId()%>">
				</div>

				<div class="write-tag">
					<textarea class="write-text" placeholder="내용을 입력하세요." name="messageContent" onKeyUp="javascript:fnChkByte(this,'500')"></textarea>
				</div>
				<div class="byteInfo">
				<span id="byteInfo">0</span>/500Byte
				</div>

			</div>
			<input type="submit" value="보내기" class="send-btn">
		</form>
	</div>
	<script>
	function fnChkByte(obj, maxByte)
	{
	    var str = obj.value;
	    var str_len = str.length;

	    var rbyte = 0;
	    var rlen = 0;
	    var one_char = "";
	    var str2 = "";


	    for(var i=0; i<str_len; i++)
	    {
	        one_char = str.charAt(i);
	        if(escape(one_char).length > 4) {
	            rbyte += 2; //한글2Byte
	        }else{
	            rbyte++; //영문 등 나머지 1Byte
	        }
	        if(rbyte <= maxByte){
	            rlen = i+1; //return할 문자열 갯수
	        }
	     }
	     if(rbyte > maxByte)
	     {
	        // alert("한글 "+(maxByte/2)+"자 / 영문 "+maxByte+"자를 초과 입력할 수 없습니다.");
	        alert("메세지는 최대 " + maxByte + "byte를 초과할 수 없습니다.")
	        str2 = str.substr(0,rlen);  //문자열 자르기
	        obj.value = str2;
	        fnChkByte(obj, maxByte);
	     }
	     else
	     {
	        document.getElementById('byteInfo').innerText = rbyte;
	     }
	}
	
	function fn_popClose(){
		top.window.close();
	}
	</script>
</body>
</html>