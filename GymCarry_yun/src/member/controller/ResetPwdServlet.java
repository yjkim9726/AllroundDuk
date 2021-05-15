package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;

/**
 * Servlet implementation class ResetPwdServlet
 */
@WebServlet("/member/resetPwd")
public class ResetPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPwdServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("/WEB-INF/views/member/pwdReset.jsp").forward(request, response);
		

	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("user-id");
		String pwd = request.getParameter("user-pwd");
		
		int result = new MemberService().resetPwd(id, pwd);
		
		if(result > 0) {
			response.sendRedirect("/main");
		}else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/memberError.jsp");
			view.forward(request, response);
		}
		
	}

}
