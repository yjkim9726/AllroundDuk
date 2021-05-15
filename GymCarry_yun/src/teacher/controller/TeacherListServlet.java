package teacher.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.model.vo.Review;
import teacher.model.service.TeacherService;
import teacher.model.vo.Teacher;

/**
 * Servlet implementation class TeacherListServler
 */
@WebServlet("/teacher/list")
public class TeacherListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int partnerCode = Integer.parseInt(request.getParameter("code"));
		ArrayList<Teacher> tList = new TeacherService().printTeacherList(partnerCode);
		if (!tList.isEmpty()) {
			request.setAttribute("tList", tList);
			request.getRequestDispatcher("/WEB-INF/views/review/potDetail.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("/WEB-INF/views/review/reviewError.html").forward(request, response);
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
