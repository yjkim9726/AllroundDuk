package admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import review.model.service.ReviewService;

/**
 * Servlet implementation class adminPartnerReviewDeleteServlet
 */
@WebServlet("/admin/reviewdelete")
public class adminPartnerReviewDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminPartnerReviewDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int partner = Integer.parseInt(request.getParameter("partnerCode"));
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		int result = new ReviewService().deleteReviewToAdmin(reviewNo);
		if(result > 0 ) {
			response.sendRedirect("/admin/partnerreview?partnerCode=" + partner);
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
