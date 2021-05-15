package admin.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;
import member.model.vo.MemberPageData;

/**
 * Servlet implementation class adminEventSearchServlet
 */
@WebServlet("/admin/membersearch")
public class adminMemberSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminMemberSearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int currentPage = 0;
		if (request.getParameter("currentPage") == null) {
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		String selectOption = request.getParameter("selectOption");
		String searchKeyword = request.getParameter("searchKeyword");
		MemberPageData pageData = new MemberService().printMemberSearchToAdmin(searchKeyword, currentPage, selectOption);
		ArrayList<Member> mList = pageData.getMemberList();
		String pageNavi = pageData.getPageNavi();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		if (!mList.isEmpty()) {
			request.setAttribute("mList", mList);
			request.setAttribute("pageNavi", pageNavi);
			request.setAttribute("selectOption", selectOption);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("searchKeyword", searchKeyword);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/admin/adminMemberSearchList.jsp");
			view.forward(request, response);
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
