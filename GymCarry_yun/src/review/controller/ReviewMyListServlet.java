package review.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;
import review.model.service.ReviewService;
import review.model.vo.Review;

/**
 * Servlet implementation class ReviewMyWriteServlet
 */
@WebServlet("/review/mylist")
public class ReviewMyListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewMyListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		int uniqId = member.getUniqId();
		ArrayList<Review> rList = new ReviewService().printReviewListById(uniqId);
		if(!rList.isEmpty()) {
			request.setAttribute("rList", rList);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/mypage/mypageReview.jsp");
			view.forward(request,response);
		} else {
			request.setAttribute("rList", null);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/mypage/mypageReview.jsp");
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
