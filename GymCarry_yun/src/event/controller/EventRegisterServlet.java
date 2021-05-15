package event.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import event.model.service.EventService;
import event.model.vo.Event;
import event.model.vo.EventPic;

/**
 * Servlet implementation class EventRegisterServlet
 */
@WebServlet("/geundeal/write")
public class EventRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EventRegisterServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/geundeal/geundealWrite.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String uploadPicPath = request.getServletContext().getRealPath("resources/Eupload");
		int uploadPicSizeLimit = 5*1024*1024;
		String encType = "UTF-8";
		MultipartRequest multi = new MultipartRequest(request, uploadPicPath, uploadPicSizeLimit, encType, new DefaultFileRenamePolicy());
		String partnerName = multi.getParameter("partnerName");
		String eventAddress = multi.getParameter("eventAdress"); 
		String eventTitle = multi.getParameter("eventTitle"); 
		String startDate = multi.getParameter("startDate");
		String endDate = multi.getParameter("endDate");
		String eventContent = multi.getParameter("eventContent");
		
		Event event = new Event();
		event.setPartnerName(partnerName);
		event.setEventAddress(eventAddress);
		event.setEventTitle(eventTitle);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setEventContent(eventContent);
		int result = new EventService().registerEvent(event);
//		System.out.println("결과값0 :" + result);
		if (result > 0) {
		Enumeration files = multi.getFileNames();
		int picResult = 0;
		String fileName = null;
			while (files.hasMoreElements()) {
				fileName = (String)files.nextElement();
				File uploadPic = multi.getFile(fileName);
				String fileSaveName = multi.getFilesystemName(fileName); 
				EventPic eventPic = new EventPic();
				if(uploadPic != null) {
					String filePath = uploadPic.getPath();
					long fileSize = uploadPic.length();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); 
					Timestamp uploadTime = Timestamp.valueOf(formatter.format(Calendar.getInstance().getTimeInMillis()));
					
					eventPic.setFileName(fileSaveName);
					eventPic.setFilePath(filePath);
					eventPic.setFileSize(fileSize);
					eventPic.setUploadTime(uploadTime);
					eventPic.setFileUser(partnerName);
					picResult = new EventService().registerEventFile(eventPic);
				}
			}
			if (fileName != null && picResult > 0) {
				response.sendRedirect("/main"); 
			}else {
				RequestDispatcher view = request.getRequestDispatcher("#"); 
				view.forward(request, response);
			}
		}else {
			RequestDispatcher view = request.getRequestDispatcher("#"); 
			view.forward(request, response);
		}
	
	}

}
