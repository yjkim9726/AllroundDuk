package admin.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import partner.model.service.PartnerService;

/**
 * Servlet implementation class adminPartnerDeleteServlet
 */
@WebServlet("/admin/partnerdelete")
public class adminPartnerDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminPartnerDeleteServlet() {
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
		} else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		int partnerCode = Integer.parseInt(request.getParameter("partnerCode"));
		String searchYn = request.getParameter("searchYn");
		String selectOption = null;
		String searchKeyword = null;
		
		int result = new PartnerService().deletePartnerToAdmin(partnerCode);
		if(result > 0) {
			if(!searchYn.equals("Y")) {
				response.sendRedirect("/admin/partnerlist?currentPage=" + currentPage);
			}else {
				selectOption = request.getParameter("selectOption");
				searchKeyword = request.getParameter("searchKeyword");
				response.sendRedirect("/admin/partnersearch?selectOption=" + selectOption + "&searchKeyword=" + URLEncoder.encode(searchKeyword, "UTF-8") +  "&currentPage=" + currentPage);
			}
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
