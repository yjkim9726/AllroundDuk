$(function() {
	//
	$("#loginBtn").on("click",function() {
		var id = $(".userId").val();
		if(id == "") {
			alert("아이디를 입력해주세요");
			return;
		}

		var pw = $(".userPwd").val();
		if(pw == "") {
			
		}
	})
})