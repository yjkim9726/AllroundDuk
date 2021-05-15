package partner.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;
import partner.model.service.PartnerService;
import partner.model.vo.Partner;
import partner.model.vo.PartnerPic;
import partner.model.vo.PricePic;
import review.model.service.ReviewService;
import review.model.vo.Review;
import review.model.vo.ReviewPageDate;
import review.model.vo.ReviewPic;
import teacher.model.service.TeacherService;
import teacher.model.vo.Teacher;

/**
 * Servlet implementation class PartnerDetailServlet
 */
@WebServlet("/partner/detail")
public class PartnerDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PartnerDetailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		int partnerCode = Integer.parseInt(request.getParameter("code"));
		Member member = (Member)session.getAttribute("member");
		String userId = null;
		if(member != null) {
			userId = member.getUserId();
		}
		Partner partner = new PartnerService().printOnePartner(partnerCode);
		ArrayList<PartnerPic> pPics = null;
		//추가*****************************
		ArrayList<ReviewPic> rPics = null;
		ArrayList<PricePic> pricePics = null;
		//******************************
		ArrayList<Teacher> tList = null;
		ArrayList<Review> rList = null;
		
		String pageNavi = null;
		int reviewCount = 0;
		
		if (partner != null) {
			pPics = new PartnerService().printAllPic(partnerCode);
			pricePics = new PartnerService().printPricePic(partnerCode);
			tList = new TeacherService().printTeacherList(partnerCode);

			int currentPage = 0;
			if (request.getParameter("currentPage") == null) {
				currentPage = 1;
			} else {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
			ReviewPageDate pageData = new ReviewService().printAllList(currentPage, partnerCode);
			reviewCount =  new ReviewService().getReviewCount(partnerCode);
			pageNavi = pageData.getPageNavi();
			rList = pageData.getrList();
			//추가*****************************
			rPics = new ReviewService().printReviewPics(rList);
			
			//******************************
			request.setAttribute("userId", userId);
			request.setAttribute("partner", partner);
			request.setAttribute("pPics", pPics);
			//추가*****************************
			request.setAttribute("rPics", rPics);
			//******************************
			request.setAttribute("tList", tList);
			request.setAttribute("rList", rList);
			request.setAttribute("pricePics", pricePics);
			request.setAttribute("rCount", reviewCount);
			request.setAttribute("pageNavi", pageNavi);
			request.getRequestDispatcher("/WEB-INF/views/gympot/potDetail.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/WEB-INF/views/gympot/potDetailError.html").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}