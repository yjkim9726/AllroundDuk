package like.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import like.model.service.LikeService;
import like.model.vo.Like;
import market.model.vo.MPageData;
import market.model.vo.Market;
import member.model.vo.Member;

/**
 * Servlet implementation class LikeListServlet
 */
@WebServlet("/like/list")
public class LikeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LikeListServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 0;
		if(request.getParameter("currentPage") == null) {
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		HttpSession session = request.getSession();
		if(session != null && (session.getAttribute("member")) != null) {
			Member member = (Member)session.getAttribute("member");
			int uniqId = member.getUniqId();
			MPageData pageData = new LikeService().selectAllLikeList(uniqId, currentPage);
			ArrayList<Market> LList = pageData.getMarketList();
			String pageNavi = pageData.getPageNavi();
			if(LList != null) {
				request.setAttribute("LList", LList);
				request.setAttribute("pageNavi", pageNavi);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/mypage/mypageLike.jsp");
				view.forward(request, response);
			} else {
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html"); // 경로를 여기에 적어준다.
		           view.forward(request, response);
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
