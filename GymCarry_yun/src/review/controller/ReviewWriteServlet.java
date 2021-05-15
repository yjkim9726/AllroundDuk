package review.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;

import javax.imageio.stream.FileCacheImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.jmx.snmp.Enumerated;

import member.model.vo.Member;
import partner.model.vo.Partner;
import review.model.service.ReviewService;
import review.model.vo.Review;
import review.model.vo.ReviewPic;

/**
 * Servlet implementation class ReviewWriteServlet
 */
@WebServlet("/review/write")
public class ReviewWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		/*
		 * HttpSession session = request.getSession(); //로그링 정보 가져오는 거! if (session !=
		 * null && (session.getAttribute("partnerCode")) != null) { int partnerCode =
		 * (int)session.getAttribute("partnerCode");
		 */
		int partnerCode = Integer.parseInt(request.getParameter("code"));
		String partnerName = request.getParameter("partnerName");
		Review review = new ReviewService().insertReview(partnerCode);
		System.out.println(partnerCode);
		request.setAttribute("code", partnerCode);
		request.setAttribute("partnerName", partnerName); // 파트너 이름을 팟디테일에서 가져오고
		if (review != null) {
			request.setAttribute("review", review);
			request.getRequestDispatcher("/WEB-INF/views/review/reviewWrite.jsp").forward(request, response);
		} else {
			// 리뷰없어도 작성할 수있도록 페이지 이동
			request.getRequestDispatcher("/WEB-INF/views/review/reviewWrite.jsp").forward(request, response);
		}
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Review review = null;
		HttpSession session = request.getSession();
		if(session != null && (session.getAttribute("member")) != null) {
			Member member = (Member)session.getAttribute("member");
			int uniqId = member.getUniqId();
			int reviewNo = 0;
			int updateResult = 0;
			String fileUserId = member.getUserId();
			String uploadFilePath = request.getServletContext().getRealPath("resources/RUpload");
			int uploadPicSizeLimit = 5*1024*1024;
			String encType = "UTF-8";
			
			MultipartRequest multi = new MultipartRequest(request, uploadFilePath, uploadPicSizeLimit, encType, new DefaultFileRenamePolicy());
			
			int partnerCode = Integer.parseInt(multi.getParameter("partnerCode"));
			String content = multi.getParameter("rev_write");
			String recommend = multi.getParameter("rvRecommend");
			
			Review review2 = new Review();
			review2.setPartnerCode(partnerCode);
			review2.setUniqId(uniqId);
			review2.setRvContent(content);
			review2.setRvRecommend(recommend);
			
			int result = new ReviewService().insertWriteReview(review2);
			
			if (result > 0) {
				Enumeration file = multi.getFileNames();
				String fileName = null;
				int fileResult = 0;
				while (file.hasMoreElements()) {
					fileName = (String)file.nextElement();
					File uploadPic = multi.getFile(fileName);
					String fileSaveName = multi.getFilesystemName(fileName);
					ReviewPic reviewPic = new ReviewPic();
					if(uploadPic != null) {
						String filePath = uploadPic.getPath();
			            long fileSize = uploadPic.length();
			            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); 
			            Timestamp uploadTime = Timestamp.valueOf(formatter.format(Calendar.getInstance().getTimeInMillis()));
				
			            reviewPic.setRvNo(uniqId);
			            reviewPic.setFileName(fileSaveName);
			            reviewPic.setFilePath(filePath);
			            reviewPic.setFileSize(fileSize);
			            reviewPic.setFileUser(fileUserId);
			            reviewPic.setUploadtime(uploadTime);
			            
			            System.out.println(reviewPic.toString());
			            fileResult = new ReviewService().insertReviewFile(reviewPic, uniqId);
					}
				}
				if (fileName != null && fileResult > 0) {
					response.sendRedirect("/partner/detail?code="+partnerCode);
				} else {
					request.getRequestDispatcher("/WEB-INF/views/review/reviewError.html").forward(request, response);
				}
			} else {
				request.getRequestDispatcher("/WEB-INF/views/review/reviewError.html").forward(request, response);
			}
	} else {
		request.getRequestDispatcher("/WEB-INF/views/review/reviewError.html").forward(request, response);
	}
	}
}





