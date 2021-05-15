package message.controller;

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
import message.model.service.MessageService;
import message.model.vo.Message;

/**
 * Servlet implementation class MessageWriteServlet
 */
@WebServlet("/message/write")
public class MessageWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageWriteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int marketNo = Integer.parseInt(request.getParameter("marketNo"));
		Market market = new MarketService().printOneMarket(marketNo);
		
		request.setAttribute("market", market);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/message/messageWrite.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String content = request.getParameter("messageContent");
		
		int receiverId = Integer.parseInt(request.getParameter("uniqId"));
		// int receiverId = new MessageService().selectReceiverId(nickName);

		HttpSession session = request.getSession();
		if (session != null && (session.getAttribute("member")) != null) {
			Member member = (Member)session.getAttribute("member");
			int senderId = member.getUniqId();
			Message message = new Message();
			message.setMessageContent(content);
			message.setSenderId(senderId);
			message.setReceiverId(receiverId);
			int sendResult = new MessageService().writeSenderMessage(message);
			int receiveResult = new MessageService().writeReceiverMessage(message);
			
			if (sendResult > 0 && receiveResult > 0) {
				response.sendRedirect("/message/list");
			} else {
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/messageError.html");
				view.forward(request, response);
			}
		} 
//		else {
//			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/message/messageError.html");
//			view.forward(request, response);
//		}
	}

}

