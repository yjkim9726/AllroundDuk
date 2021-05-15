package admin.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/admin/memberdelete")
public class adminMemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminMemberDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		int currentPage = 0;
		if (request.getParameter("currentPage") == null) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int userNo = Integer.parseInt(request.getParameter("memberNo"));
		String searchYn = request.getParameter("searchYn");
		String selectOption = null;
		String searchKeyword = null;
		
		int result = new MemberService().deleteMarketToAdmin(userNo);
		if (result>0) {
			if(!searchYn.equals("Y")) {
				response.sendRedirect("/admin/memberlist?currentPage="+currentPage);
			}else {
				selectOption = request.getParameter("selectOption");
				searchKeyword = request.getParameter("searchKeyword");
				response.sendRedirect("/admin/membersearch?selectOption=" + selectOption + "&searchKeyword=" + URLEncoder.encode(searchKeyword, "UTF-8") + "&currentPage=" + currentPage);
			}
		}else {
			RequestDispatcher view = request.getRequestDispatcher("#");
			view.forward(request, response);
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
