package like.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import like.model.service.LikeService;
import member.model.vo.Member;

/**
 * Servlet implementation class LikeDeleteServlet
 */
@WebServlet("/like/delete")
public class LikeDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int uniqId = Integer.parseInt(request.getParameter("uniqId"));
//		int marketNo = Integer.parseInt(request.getParameter("marketNo"));
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		int uniqId = member.getUniqId();
		String [] marketNo = request.getParameterValues("checkRow");
		
		int result = new LikeService().deleteLikeList(marketNo, uniqId);
		if(result > 0) {
			response.sendRedirect("/like/list");
		}else {
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
