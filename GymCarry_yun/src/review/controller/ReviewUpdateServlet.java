package review.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import market.model.service.MarketService;
import market.model.vo.MarketPic;
import member.model.vo.Member;
import review.model.service.ReviewService;
import review.model.vo.Review;
import review.model.vo.ReviewPic;

/**
 * Servlet implementation class ReviewUpdateServlet
 */
@WebServlet("/review/modify")
public class ReviewUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
//		int partnerCode = (int)request.getAttribute("partnerCode");
		Review review = new ReviewService().printOneReview(reviewNo);
		if(review != null) {
			request.setAttribute("review", review);
			request.getRequestDispatcher("/WEB-INF/views/mypage/mypageModifyReview.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/WEB-INF/views/market/error.html").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		int uniqId = member.getUniqId();
		String fileUserId =member.getUserId();
		String uploadfilePath = request.getServletContext().getRealPath("/resources/RUpload");
		int uploadPicSizeLimit = 5*1024*1024;
		String encType = "UTF-8";
		MultipartRequest multi = new MultipartRequest(request, uploadfilePath ,uploadPicSizeLimit, encType, new DefaultFileRenamePolicy());
		
		int reviewNo = Integer.parseInt(multi.getParameter("reviewNo"));
		String content = multi.getParameter("rev_write");
		String partnerName = multi.getParameter("partner-name");
		String recommend = multi.getParameter("rvRecommend");
		int partnerCode = Integer.parseInt(multi.getParameter("partnerCode"));
		
		Review review = new Review();
		review.setUniqId(uniqId);
		review.setRvNo(reviewNo);
		review.setPartnerCode(partnerCode);
		review.setRvContent(content);
		review.setRvRecommend(recommend);
		int result = new ReviewService().modifyReview(review);
		
		if(result > 0) {
			Enumeration files = multi.getFileNames();
			String fileName = null;
			int fileresult = 0;
			int deleteresult = 0;
			deleteresult = new ReviewService().deleteReviewFile(reviewNo);
			if(deleteresult > 0) {
				while(files.hasMoreElements()) {
					fileName = (String)files.nextElement();
					File uploadPic = multi.getFile(fileName);
					String fileSaveName = multi.getFilesystemName(fileName);
					ReviewPic reviewPic = new ReviewPic();
					System.out.println("test" + uploadPic);
					if(uploadPic != null) {
						String filePath = uploadPic.getPath();
			            long fileSize = uploadPic.length();
			            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); 
			            Timestamp uploadTime = Timestamp.valueOf(formatter.format(Calendar.getInstance().getTimeInMillis()));
		            
			            reviewPic.setFileName(fileSaveName);
			            reviewPic.setFilePath(filePath);
			            reviewPic.setFileSize(fileSize);
			            reviewPic.setUploadtime(uploadTime);
			            reviewPic.setFileUser(fileUserId);
			            fileresult = new ReviewService().updateReviewFile(reviewPic,reviewNo);  
			         }
				}
			}
			if(fileName != null && fileresult > 0 ) {
				response.sendRedirect("/review/mylist");
			} else {
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html");
				view.forward(request, response);
			}
		}else {
			request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html").forward(request, response);
		}
	
	}

}