package market.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import market.model.service.MarketService;
import market.model.vo.MPageData;
import market.model.vo.Market;

/**
 * Servlet implementation class MarketSearchServlet
 */
@WebServlet("/market/search")
public class MarketSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MarketSearchServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int currentPage = 0;
		if(request.getParameter("currentPage") == null) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		String search = request.getParameter("searchKeyword");
		String selectOption = request.getParameter("searchOption");
		MPageData pageData = null;
		ArrayList<Market> mList = null;
		String pageNavi = "";
		
		if(selectOption.equals("title")) {
			pageData = new MarketService().printSearcTitle(search, selectOption, currentPage);			
			mList = pageData.getMarketList();
			pageNavi = pageData.getPageNavi();
		} else if(selectOption.equals("writer")) {
			pageData = new MarketService().printSearcWriter(search, selectOption, currentPage);			
			mList = pageData.getMarketList();
			pageNavi = pageData.getPageNavi();
		} else {
			pageData = new MarketService().printSearcContent(search, selectOption, currentPage);			
			mList = pageData.getMarketList();
			pageNavi = pageData.getPageNavi();
		}
		
		if(!mList.isEmpty()) {
			request.setAttribute("mList", mList);
			request.setAttribute("pageNavi", pageNavi);
			request.setAttribute("searchKeyword", search);
			request.setAttribute("selectOption", selectOption);
			request.getRequestDispatcher("/WEB-INF/views/gymmarket/marketSearch.jsp").forward(request, response);
		}  else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('검색된 내용이 없습니다. 다시 검색해주세요'); location.href='/market/list';</script>"); 
			out.flush();
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