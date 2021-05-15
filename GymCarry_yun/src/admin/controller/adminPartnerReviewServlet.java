package admin.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.model.service.ReviewService;
import review.model.vo.Review;

/**
 * Servlet implementation class adminPartnerReviewServlet
 */
@WebServlet("/admin/partnerreview")
public class adminPartnerReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminPartnerReviewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			int partnerCode = Integer.parseInt(request.getParameter("partnerCode"));
			ArrayList<Review> rList = new ReviewService().printReviewListToAdmin(partnerCode);
			if(!rList.isEmpty()) {
				request.setAttribute("partnerCode", partnerCode);
				request.setAttribute("rList", rList);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/admin/adminReviewList.jsp");
				view.forward(request,response);
			} else {
				request.setAttribute("rList", null);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/admin/adminReviewList.jsp");
				view.forward(request,response);
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
