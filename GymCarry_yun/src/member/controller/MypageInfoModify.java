package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MypageInfoModify
 */
@WebServlet("/mypage/infomodify")
public class MypageInfoModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MypageInfoModify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Member member = new Member();
		member.setUserId(request.getParameter("user-id"));
		member.setUserPwd(request.getParameter("user-pwd"));
		member.setNickname(request.getParameter("nickname"));
		member.setName(request.getParameter("user-name"));
		member.setEmail(request.getParameter("email"));
		member.setPhone(request.getParameter("phone"));
		member.setAddressCity(request.getParameter("addr-city"));
		member.setAddressGu(request.getParameter("addr-gu"));
		
		int result = new MemberService().modifyMember(member);
		if(result > 0) {
			response.sendRedirect("/member/mypage");
		} else {
			request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
