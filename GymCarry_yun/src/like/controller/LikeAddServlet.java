package like.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.mail.Session;
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
 * Servlet implementation class LikeAddServlet
 */
@WebServlet("/market/like")
public class LikeAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LikeAddServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uniqId = Integer.parseInt(request.getParameter("uniqId"));
		int marketNo = Integer.parseInt(request.getParameter("marketNo"));
		int checkResult = 0;
		HttpSession session = request.getSession();
		if(session != null && (session.getAttribute("member")) != null) {
			Like likeCheck = new LikeService().selectOneLike(uniqId, marketNo);
			if(likeCheck != null) {
				checkResult = new LikeService().deleteLike(marketNo,uniqId);				
			}else {
				checkResult = new LikeService().insertLike(marketNo,uniqId);				
			}
			if(checkResult > 0) {
				response.sendRedirect("/market/detail?marketNo="+marketNo);				
			}else {
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html");
				view.forward(request, response);				
			}
		} else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인이 필요합니다')</script>");
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