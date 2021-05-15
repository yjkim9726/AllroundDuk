package admin.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.model.service.EventService;
import event.model.vo.Event;
import event.model.vo.PageData;

/**
 * Servlet implementation class adminEventSearchServlet
 */
@WebServlet("/admin/eventsearch")
public class adminEventSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminEventSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		String cate = request.getParameter("cate");
		String searchKeyword = request.getParameter("searchKeyword");
		PageData pageData = new EventService().printSearchList(searchKeyword, currentPage, cate);
		ArrayList<Event> eList = pageData.getEventList();
		String pageNavi = pageData.getPageNavi();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		if (!eList.isEmpty()) {
			request.setAttribute("eList", eList);
			request.setAttribute("pageNavi", pageNavi);
			request.setAttribute("cate", cate);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("searchKeyword", searchKeyword);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/admin/adminEventSearchList.jsp");
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
