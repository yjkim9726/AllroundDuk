<%@page import="member.model.vo.Member"%>
<%@page import="message.model.vo.Message"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ArrayList<Message> mList = (ArrayList<Message>) request.getAttribute("mList");
	Member member = (Member) request.getAttribute("member");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MESSAGE LIST</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/jquery-3.5.1.min.js"></script>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Anton&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/resources/css/messageList.css">
</head>
<body>
	<!-- https://webclub.tistory.com/189 -->
	<!-- https://imivory.tistory.com/8 -->
	<div class="dialog" id="list-dialog" style="OVERFLOW-Y: auto;">
		<span class="dialog__close" onclick="fn_popClose();">&#x2715;</span>
		<h2 class="dialog__title">MESSAGE LIST</h2>
		<ul class="tabs">
			<a href="/message/list"><li id="recived" value="recived" class="tab-link current" data-tab="tab-1"><pre>받은 쪽지함</pre></li></a>
			<a href="/message/sentList"><li id="sent" value="sent" class="tab-link" data-tab="tab-2"><pre>보낸쪽지함</pre></li></a>
		</ul>
		<%
			for (Message message : mList) {
		%>
		<div id="tab-1" class="tab-content current">
			<div id="msg-list" class="msg-list">
				<%-- <a href="javascript:void(0);" onclick='fn_ajax("<%=message.getMessageNo()%>");'> --%>
				<a href="/message/detail?messageNo=<%=message.getMessageNo()%>">
					<div class="top-box">
						<div class="msg-id msg-top">
						<span class="from">FROM.</span>
						<%=message.getNickName()%></div>
						<!-- <div id="msg-read" class="msg-top">읽음</div> -->
						<div class="msg-date msg-top"><%=message.getMessageDate()%></div>
					</div>
					<div class="msg-content">
						<%-- <%=message.getMessageNo()%> --%>
						<p class="content-height" id="content-height"><%=message.getMessageContent()%></p>
					</div>
				</a>
			</div>
		</div>

		<%
			}
		%>
	</div>
	<div class="detail" style="display: none">
	</div>
                
	<script>
	$(document).ready(function() {
	      // 팝업 창 크기를 HTML 크기에 맞추어 자동으로 크기를 조정하는 함수.
	      var strWidth;
	      var strHeight;

	      //innerWidth / innerHeight / outerWidth / outerHeight 지원 브라우저 
	      if ( window.innerWidth && window.innerHeight && window.outerWidth && window.outerHeight ) {
	          strWidth = $('.dialog').outerWidth() + (window.outerWidth - window.innerWidth);
	          strHeight = $('.dialog').outerHeight() + (window.outerHeight - window.innerHeight);
	      }
	      else {
	          var strDocumentWidth = $(document).outerWidth();
	          var strDocumentHeight = $(document).outerHeight();

	          window.resizeTo ( strDocumentWidth, strDocumentHeight );

	          var strMenuWidth = strDocumentWidth - $(window).width();
	          var strMenuHeight = strDocumentHeight - $(window).height();

	          strWidth = $('.dialog').outerWidth() + strMenuWidth;
	          strHeight = $('.dialog').outerHeight() + strMenuHeight;
	      }

	      //resize 
	      window.resizeTo( strWidth, strHeight );

	  });
	
	
	/* 	function dialog() {

			var dialogBox = $('.dialog'), dialogTrigger = $('.dialog__trigger'), dialogClose = $('.dialog__close'), dialogTitle = $('.dialog__title'), dialogContent = $('.dialog__content'), dialogAction = $('.dialog__action');

			// Open the dialog
			dialogTrigger.on('click', function(e) {
				dialogBox.toggleClass('dialog--active');
				e.stopPropagation()
			});

			// Close the dialog - click close button
			dialogClose.on('click', function() {
				dialogBox.removeClass('dialog--active');
			});

			// Close the dialog - press escape key // key#27
			$(document).keyup(function(e) {
				if (e.keyCode === 27) {
					dialogBox.removeClass('dialog--active');
				}
			});
		}; */
		
		
		function fn_popClose(){
			top.window.close();
		}
		
		
		/* function fn_ajax(no){
		
	        $.ajax({

	            data: {"messageNo":no},

	            url: '/message/detail',

	            success: function (res) {
					$('.detail').html(res);
					$('.detail').show();
					$('#list-dialog').hide();
					
	                // alert(response.success);

	            },

	            error: function (e) {

	                alert("통신실패" + e)

	            }

	        });
		}
 */
		// Run function when the document has loaded
		/* 		$(function() {
		 dialog();
		 });
		 $(document).ready(function() {
		 $('ul.tabs li').click(function() {
		 var tab_id = $(this).attr('data-tab');
		 $('ul.tabs li').removeClass('current');
		 $('.tab-content').removeClass('current');
		 $(this).addClass('current');
		 $("#" + tab_id).addClass('current');
		 });
		 }); */
		/* 		function getbtn() {
		 var btn = document.getElementsByClassName("btn");
		 var height = document.getElementsByClassName("content-height");
		 if (btn[0].style.display == "block") {
		 btn[0].style.display = "none";
		 height[0].style.height = "40px";
		 height[0].style.overflow = "hidden";
		 } else {
		 btn[0].style.display = "block";
		 height[0].style.height = "inherit";
		 }
		 }
		 function getbtn2() {
		 var btn = document.getElementsByClassName("btn");
		 var height = document.getElementsByClassName("content-height");
		 if (btn[1].style.display == "block") {
		 btn[1].style.display = "none";
		 height[1].style.height = "40px";
		 height[1].style.overflow = "hidden";
		 } else {
		 btn[1].style.display = "block";
		 height[1].style.height = "inherit";
		 }
		 } */
	</script>
</body>
</html>