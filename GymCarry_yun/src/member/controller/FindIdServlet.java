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
 * Servlet implementation class FindIdServlet
 */
@WebServlet("/member/findId")
public class FindIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/findId.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String userName = request.getParameter("userName");
		String userPhone = request.getParameter("userPhone");
		Member member = new MemberService().selectOneById(userName, userPhone);
		
		Member memberInfo = null;
		//로그인여부 확인
				if (member != null) {
					memberInfo = new Member();
					memberInfo.setUniqId(member.getUniqId());
					memberInfo.setUserId(member.getUserId());
					memberInfo.setAdminYn(member.getAdminYn());
					memberInfo.setNickname(member.getNickname());
					memberInfo.setName(member.getName());
					memberInfo.setGender(member.getGender());
					memberInfo.setEmail(member.getEmail());
					memberInfo.setPhone(member.getPhone());
					memberInfo.setAddressCity(member.getAddressCity());
					memberInfo.setAddressGu(member.getAddressGu());
					memberInfo.setAdminYn(member.getAdminYn());
					// session은 유저가 접속할 때 생성되어 유지되는 객체이다.
					HttpSession session = request.getSession();
					session.setAttribute("member", memberInfo);
					RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/findIdSuccess.jsp");
					view.forward(request, response);
				}else {
					// 콘솔에 알림창 뜨도록 할 것.
					System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
					request.getRequestDispatcher("/WEB-INF/views/member/memberError.html").forward(request, response);
				}
	}

}
