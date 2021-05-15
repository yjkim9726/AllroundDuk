package message.controller;

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
import message.model.service.MessageService;
import message.model.vo.Message;

/**
 * Servlet implementation class MessageListServlet
 */
@WebServlet("/message/sentList")
public class MessageSentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageSentListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int currentPage = 0;
//		if(request.getParameter("currentPage") == null) {
//			currentPage = 1;
//		} else {
//			currentPage = Integer.parseInt(request.getParameter("currentPage"));
//		}
//		
//		MSGPageData pageData = new MessageService().printAllList(currentPage);
//		ArrayList<Message> mList = pageData.getMessageList();
//		String pageNavi = pageData.getPageNavi();
//		
		HttpSession session = request.getSession(); // 세션에서 정보 가져오기
		if(session != null && (session.getAttribute("member")) != null) {
			Member member = (Member)session.getAttribute("member");
			int uniq_id = member.getUniqId();
			ArrayList<Message> mList = new MessageService().printSentList(uniq_id);
			// if(!mList.isEmpty()) {
			if(mList != null) {
				request.setAttribute("mList", mList);
//				request.setAttribute("pageNavi", pageNavi);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/message/messageSentList.jsp");
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
