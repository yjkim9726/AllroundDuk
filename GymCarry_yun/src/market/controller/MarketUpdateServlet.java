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
 * Servlet implementation class MarketUpdateServlet
 */
@WebServlet("/market/modify")
public class MarketUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MarketUpdateServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int marketNo =Integer.parseInt(request.getParameter("marketNo"));
		Market market = new MarketService().printOneMarket(marketNo);
		if(market != null) {
			request.setAttribute("market", market);
			request.getRequestDispatcher("/WEB-INF/views/mypage/mypageModifyWrite.jsp").forward(request, response);
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
		String uploadfilePath = request.getServletContext().getRealPath("/resources/MUpload");
		int uploadPicSizeLimit = 5*1024*1024;
		String encType = "UTF-8";
		MultipartRequest multi = new MultipartRequest(request, uploadfilePath ,uploadPicSizeLimit, encType, new DefaultFileRenamePolicy());
		
		int marketNo = Integer.parseInt(multi.getParameter("marketNo"));
		String title = multi.getParameter("title");
		String field = multi.getParameter("field");
		String price = multi.getParameter("price");
		String content = multi.getParameter("editordata");
		
		Market market = new Market();
		market.setMarketNo(marketNo);
		market.setMarketTitle(title);
		market.setMarketPrice(price);
		market.setMarketContent(content);
		market.setMarketField(field);
		int result = new MarketService().modifyMarket(market);
		
		if(result > 0) {
			Enumeration files = multi.getFileNames();
			String fileName = null;
			int fileresult = 0;
			// 실수한 부분: 기존의 있던 파일을 삭제한 후에 새롭게 파일을 업로드하는 방식인데
			// 				파일이 존재하지 않을때 그 파일이 오류인지 파일이 존재하지 않는지에 대한 
			//				코드를 작성하지 않음, if(deleteresult > 0) 을 뺴고 적어줌
			int deleteresult = new MarketService().deleteMarketFile(marketNo);
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
			            fileresult = new MarketService().updateMarketFile(marketPic,marketNo);  
			       }
			}
			if(fileName != null && fileresult >0) {
			response.sendRedirect("/mypage/writeList");
			} else {
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html");
				view.forward(request, response);
			}
		} else {
			request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html").forward(request, response);
		}
	}

}