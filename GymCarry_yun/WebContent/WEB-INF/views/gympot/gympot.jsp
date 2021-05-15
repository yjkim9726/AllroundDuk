<%@page import="member.model.vo.Member"%>
<%@page import="partner.model.vo.PartnerPageData"%>
<%@page import="partner.model.vo.PartnerPic"%>
<%@page import="partner.model.vo.Partner"%>
<%@page import="java.util.ArrayList"%>

<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 서버에서 데이터 읽어오기 -->
<%
	ArrayList<Partner> pList = (ArrayList<Partner>) request.getAttribute("pList");
	PartnerPageData pPages = (PartnerPageData) request.getAttribute("pPages");
	ArrayList<PartnerPic> pPics = (ArrayList<PartnerPic>) request.getAttribute("pPics");

	String userId = null;
	int uniqId = 0;
	if (session.getAttribute("userId") != null && session.getAttribute("uniqId") != null) {
		userId = (String) session.getAttribute("userId");
		uniqId = (int) session.getAttribute("uniqId");
	}
	Member member = null;
	   if (session != null && session.getAttribute("member") != null) {
	      member = (Member) session.getAttribute("member");
	   }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>GYM POT 짐팟</title>
<!-- 자바스크립트에 jsp변수 넘기기  -->
<script>
positions = []; //배열 선언
<% for (Partner partner : pList) { %>
	mapdata = new Object(); //오브젝트 선언
	mapdata.name = '<%= partner.getPartnerName() %>',
	mapdata.address = '<%= partner.getPartnerAddress() %>',
	mapdata.code = <%= partner.getPartnerCode() %>
	positions.push(mapdata);
	<% } %>
station = '<%=(String) request.getAttribute("station")%>';
if (station == 'null') {
	station = '종각역';
}
</script>
<!--제이쿼리-->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!--지도-->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=	f6d1d236fcd89d65153f8c19d45ccf11&libraries=services"></script>
<script type="text/javascript" src="/resources/script/gymmap.js"></script>
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="/resources/css/gympot.css">
<link rel="stylesheet" type="text/css"
	href="/resources/css/mapcontrol.css">
<!-- 폰트 -->
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
</head>

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
		<div class="contents1">
			<a href="#">
				<div class="contents-img">
					<div id="text1">GYM POT</div>
					<div id="text2">내 주변의 모든 운동시설 찾기</div>
				</div>
			</a>
		</div>
		<div class="contents2">
			<div class="contents2-1">

				<form class="search-box" action="/partner/search" method="get">
					<input type="text" id="search" name="query"
						placeholder="지역/ 운동종류 또는 센터명" value=""
						onkeyup="return search_enter()" /> <input type="hidden"
						name="page" value="1"> <span class="icon"><button
							id="btn" onclick="return search_btn()">
							<img src="/resources/img/search.png">
						</button></span>
				</form>

				<%
					int currentPage = pPages.getCurrentPage();
					int startPage = pPages.getStartPage();
					int endPage = pPages.getEndPage();
					int lastPage = pPages.getLastPage();
					int total = pPages.getTotal();

					int pListCntMin = (currentPage - 1) * 4;
					int pListCntMax = (currentPage) * 4;
					if (pListCntMax > total) {
						pListCntMax = total;
					}
					int pcnt = 0; //사진정보 불러올 변수
				%>
				<div class="list">
					<%
						for (int i = pListCntMin; i < pListCntMax; i++) {
					%>
					<div class="list-content">
						<div class="list-left">
							<%
								String url = "/resources/PUpload/" + pPics.get(pcnt++).getFileName();
							%>
							<img src=<%=url%> id="list-img">
						</div>
						<div class="list-right"
							onclick="pot_detail(<%=Integer.toString(pList.get(i).getPartnerCode())%>)">
							<div class="list-kind"><%=pList.get(i).getPartnerType()%></div>
							<div class="list-name"><%=pList.get(i).getPartnerName()%></div>
							<div class="list-addr"><%=pList.get(i).getPartnerAddress()%></div>
							<div class="list-hours"><%=pList.get(i).getPartnerHours()%></div>
						</div>
					</div>
					<hr color="black" size="1px" width="360px">
					<%
						}
					%>
	               <ul class="page-navi">
	                  <!-- 페이징 변수 로드하기 -->
	                  <%
	                     if (startPage > 5) {
	                  %>
	                  <li onclick="page_move(<%=startPage - 1%>)">◀</li>
	                  <%
	                     }
	                  %>
	                  <%
	                     for (int i = startPage; i < endPage + 1; i++) {
	                  %>
	                  <%
	                     if (i != currentPage) {
	                  %>
	                  <li onclick="page_move(<%=i%>)"><%=i%></li>
	                  <%
	                     } else {
	                  %>
	                  <li class="current_page" style="color: blue; font-weight: bold"><%=i%></li>
	                  <%
	                     }
	                     }
	                  %>
	                  <%
	                     if (endPage + 1 < lastPage) {
	                  %>
	                  <li onclick="page_move(<%=endPage + 1%>)">▶</li>
	                  <%
	                     }
	                  %>
               </ul>
				</div>
			</div>
			<div class="contents2-2">
				<div id="map"></div>
				<div class="custom_zoomcontrol radius_border">
					<span id="zoomIn"><img
						src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png"
						alt="확대"></span> <span id="zoomOut"><img
						src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png"
						alt="축소"></span>
				</div>
			</div>
		</div>
		<footer>
			<div class="footer">
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
	</div>
</body>
<script>
    //검색어 입력시 이동하는 기능
    function search_btn() {
    	var searchText = document.getElementsByName('query')[0].value;
        if (searchText == '') {
        	alert("검색어를 입력해주세요");
        	return false;
        } else {
            document.getElementsByClassName('search-box').submit();
        }
    }
    //엔터키 눌렀을 때 검색되는 기능
    function search_enter() {
        if (window.event.keyCode == 13) { // 엔터키가 눌렸을 때
            var searchText = document.getElementsByName('query')[0].value;
            if (searchText == '') {
            	alert("검색어를 입력해주세요");
            	return false;
            } else {
                document.getElementsByClassName('search-box').submit();
            }
        }
    }
    //리스트 클릭시 이동하는 기능
    function pot_detail(code) {
    	location.href="/partner/detail?code=" + code;
    }
	//페이지 이동하는 기능
	function page_move(page) {
		var query = '<%=(String) request.getAttribute("query")%>'
		if(query=='null') {
			query = "종각역";
		}
		location.href="/partner/search?query=" + query + "&page=" + page;
	}
</script>
</html>