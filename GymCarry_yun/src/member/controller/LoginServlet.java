package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/member/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		Member member = new MemberService().selectOneUser(userId, userPwd);
		
		Member loginInfo = null;
		//로그인여부 확인
		if (member != null) {
			loginInfo = new Member();
			loginInfo.setUniqId(member.getUniqId());
			loginInfo.setUserId(member.getUserId());
			loginInfo.setAdminYn(member.getAdminYn());
			loginInfo.setNickname(member.getNickname());
			loginInfo.setName(member.getName());
			loginInfo.setGender(member.getGender());
			loginInfo.setEmail(member.getEmail());
			loginInfo.setPhone(member.getPhone());
			loginInfo.setAddressCity(member.getAddressCity());
			loginInfo.setAddressGu(member.getAddressGu());
			loginInfo.setAdminYn(member.getAdminYn());
			// session은 유저가 접속할 때 생성되어 유지되는 객체이다.
			HttpSession session = request.getSession();
			session.setAttribute("member", loginInfo);
			RequestDispatcher view = request.getRequestDispatcher("/loginSuccess.jsp");
			view.forward(request, response);
		}else {
			// 콘솔에 알림창 뜨도록 할 것.
			System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
			request.getRequestDispatcher("/WEB-INF/views/member/memberError.html").forward(request, response);
		}
	}

}
