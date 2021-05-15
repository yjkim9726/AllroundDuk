package market.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import like.model.service.LikeService;
import like.model.vo.Like;
import market.model.service.MarketService;
import market.model.vo.Market;
import market.model.vo.MarketPic;
import member.model.vo.Member;

/**
 * Servlet implementation class MarketDetailServlet
 */
@WebServlet("/market/detail")
public class MarketDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MarketDetailServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int marketNo = Integer.parseInt(request.getParameter("marketNo"));
		int uniqId = 0;
		Like like = null;
		
		
		Market market = new MarketService().printOneMarket(marketNo);
		ArrayList<MarketPic> MPic = new MarketService().printListPic(marketNo);
		if(market != null) {
			request.setAttribute("market", market);
			request.setAttribute("picList", MPic);
			if(session.getAttribute("member") != null) {			
				Member member = (Member)session.getAttribute("member");
				uniqId = member.getUniqId();
				like = new LikeService().selectOneLike(uniqId, marketNo);
			}
			if(like != null) {
				request.setAttribute("likeCheck", "like"); // 스트링 like를 likeCheck 키값으로 줌
			}else {
				request.setAttribute("likeCheck", "none");
			}
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/gymmarket/marketDetail.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view =request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html");
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