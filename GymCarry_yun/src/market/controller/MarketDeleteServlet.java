package market.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import market.model.service.MarketService;
import market.model.vo.Market;
import member.model.vo.Member;

/**
 * Servlet implementation class MarketDeleteServlet
 */
@WebServlet("/market/delete")
public class MarketDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MarketDeleteServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		int uniqId = member.getUniqId();
		int currentPage = 0;
		if(request.getParameter("currentPage") == null) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int marketNo = Integer.parseInt(request.getParameter("marketNo"));
			int result = new MarketService().deleteMarket(uniqId, marketNo);

			if(result > 0 ) {
				// 넘겨줄값이 있으면 request , 넘겨줄값이 없으면 response
				response.sendRedirect("/mypage/writeList?currentPage="+currentPage);
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