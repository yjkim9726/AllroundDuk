package review.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import partner.model.service.PartnerService;
import partner.model.vo.Partner;
import review.model.service.ReviewService;
import review.model.vo.Review;
import review.model.vo.ReviewPageDate;
import teacher.model.service.TeacherService;
import teacher.model.vo.Teacher;

/**
 * Servlet implementation class ReviewListServlet
 */
@WebServlet("/review/list")
public class ReviewListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		String userId = (String)session.getAttribute(("userId"));
		
		int partnerCode = Integer.parseInt(request.getParameter("code"));
		
		ArrayList<Review> rList = new ReviewService().printReviewList(partnerCode);
		if (!rList.isEmpty()) {
			request.setAttribute("rList", rList);
		
			int currentPage = 0;
			if (request.getParameter("currentPage") == null) {
				currentPage = 1;
			}else {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
			ReviewPageDate pageData = new ReviewService().printAllList(currentPage, partnerCode);
			String pageNavi = pageData.getPageNavi();
			
			if (!rList.isEmpty()) {
				request.setAttribute("rList", rList);
				request.setAttribute("pageNavi", pageNavi);
				request.getRequestDispatcher("/WEB-INF/views/review/potDetail.jsp").forward(request, response);
			}
		} else {
			request.getRequestDispatcher("/WEB-INF/views/review/reviewError.html").forward(request, response);
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
