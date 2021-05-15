package main.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import event.model.service.EventService;
import event.model.vo.Event;
import market.model.service.MarketService;
import market.model.vo.Market;

/**
 * Servlet implementation class MainEventListServlet
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Event> eList = null;
		eList = new EventService().selectEventListToMain();
		if (!eList.isEmpty()) {
			request.setAttribute("eList", eList);
			ArrayList<Market> mList = new MarketService().printAllMarketListToMain();
			if (!mList.isEmpty()) {
				request.setAttribute("mList", mList);
				RequestDispatcher view = request.getRequestDispatcher("/main.jsp");
				view.forward(request, response);
			}
		}else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html");
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
