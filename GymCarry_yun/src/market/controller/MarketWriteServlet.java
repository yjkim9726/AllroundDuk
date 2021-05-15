package market.controller;

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
import market.model.vo.Market;
import market.model.vo.MarketPic;
import member.model.vo.Member;

/**
 * Servlet implementation class MarketWriteServlet
 */
@WebServlet("/market/write")
public class MarketWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MarketWriteServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/gymmarket/marketWrite.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(); // 로그인한 사용자니까 세션에서 정보 가져오기
		if(session != null && (session.getAttribute("member")) != null) {
			Member member = (Member)session.getAttribute("member");
			int uniqId = member.getUniqId();
			String fileUserId =member.getUserId();
			String uploadfilePath = request.getServletContext().getRealPath("/resources/MUpload");
			int uploadPicSizeLimit = 5*1024*1024;
			String encType = "UTF-8";
			MultipartRequest multi = new MultipartRequest(request, uploadfilePath ,uploadPicSizeLimit, encType, new DefaultFileRenamePolicy());
			
			String title = multi.getParameter("title");
			String field = multi.getParameter("field");
			String price = multi.getParameter("price");
			String content = multi.getParameter("editordata");
			
			Market market = new Market();
			market.setUniqId(uniqId);
			market.setMarketTitle(title);
			market.setMarketField(field);
			market.setMarketPrice(price);
			market.setMarketContent(content);
			
			int result = new MarketService().insertMarket(market);
			
			if(result > 0) {
				Enumeration files = multi.getFileNames();
				String fileName = null;
				int fileresult = 0;
				while (files.hasMoreElements()) {
					fileName = (String)files.nextElement();
					File uploadPic = multi.getFile(fileName);
					String fileSaveName = multi.getFilesystemName(fileName);
					MarketPic marketPic = new MarketPic();
					System.out.println("test" + uploadPic);
					if(uploadPic != null) {
						String filePath = uploadPic.getPath();
			            long fileSize = uploadPic.length();
			            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); 
			            Timestamp uploadTime = Timestamp.valueOf(formatter.format(Calendar.getInstance().getTimeInMillis()));
			            
			            marketPic.setFileName(fileSaveName);
			            marketPic.setFilePath(filePath);
			            marketPic.setFileSize(fileSize);
			            marketPic.setUploadTime(uploadTime);
			            marketPic.setFileUser(fileUserId);
			            fileresult = new MarketService().insertMarketFile(marketPic,uniqId);
					}
				} 
				if(fileName != null && fileresult >0) {
					response.sendRedirect("/market/list"); 
				} else {
					RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/view/market/error.html");
					view.forward(request, response);
				}
			} else {
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/view/market/error.html");
				view.forward(request, response);
			}
//			if(result > 0) {
//				response.sendRedirect("/market/list"); // 전달값을 url로 넘겨줌
//			} else {
//				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/market/error.html");
//				view.forward(request, response);
//			}
			
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/view/market/error.html");
			view.forward(request, response);
		}
		
		
	}

}