package admin.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import market.model.vo.MarketPic;
import partner.model.service.PartnerService;
import partner.model.vo.Partner;
import partner.model.vo.PartnerPic;
import partner.model.vo.PricePic;
import review.model.service.ReviewService;
import teacher.model.service.TeacherService;
import teacher.model.vo.Teacher;

/**
 * Servlet implementation class adminPartnerModifyServlet
 */
@WebServlet("/admin/partnermodify")
public class adminPartnerModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminPartnerModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int partnerCode = Integer.parseInt(request.getParameter("partnerCode"));
		Partner partner = new PartnerService().printOnePartner(partnerCode);
		Teacher teacher = new PartnerService().printTeacherList(partnerCode);
		
		if(partner != null && teacher != null) {
			request.setAttribute("partner", partner);
			request.setAttribute("teacher", teacher);
			request.getRequestDispatcher("/WEB-INF/views/admin/adminPartnerModify.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String uploadfilePath = request.getSession().getServletContext().getRealPath("/resources/PUpload");
		
		int uploadPicSizeLimit = 10*1024*1024;
		String encType = "UTF-8";
		MultipartRequest multi = new MultipartRequest(request, uploadfilePath, uploadPicSizeLimit, encType, new DefaultFileRenamePolicy());
		
		int partnerCode = Integer.parseInt(multi.getParameter("code"));
		String partnerName = multi.getParameter("partnerName");
		String partnerType = multi.getParameter("partnerType");
		String partnerAddress = multi.getParameter("partnerAddress");
		String partnerPhone = multi.getParameter("partnerPhone");
		String partnerHours = multi.getParameter("partnerHours");
		String partnerPrice = multi.getParameter("partnerPrice");
		String partnerParking = multi.getParameter("partnerParking");
		String addContent = multi.getParameter("addContent");
		String station = multi.getParameter("station");
		
		Partner partner = new Partner();
		partner.setPartnerName(partnerName);
		partner.setPartnerType(partnerType);
		partner.setPartnerAddress(partnerAddress);
		partner.setPartnerPhone(partnerPhone);
		partner.setPartnerHours(partnerHours);
		partner.setPartnerPrice(partnerPrice);
		partner.setPartnerParking(partnerParking);
		partner.setAddContent(addContent);
		partner.setStation(station);
		partner.setPartnerCode(partnerCode);
		
		int tchrNo = Integer.parseInt(multi.getParameter("tchrNo"));
		String tchName = multi.getParameter("tchName");
		String tchrClass = multi.getParameter("tchrClass");
		String tchrCareer = multi.getParameter("tchrCareer");
		
		Teacher teacher = new Teacher();
		
		teacher.setTchrNo(tchrNo);
		teacher.setTchName(tchName);
		teacher.setTchClass(tchrClass);
		teacher.setTchrCareer(tchrCareer);
		teacher.setPartnerCode(partnerCode);
		
		int partnerResult = new PartnerService().modifyPartner(partner);
		int teacherResult = new TeacherService().modifyTeacher(teacher);
		
		if(partnerResult > 0 && teacherResult > 0) {
			Enumeration files = multi.getFileNames();
			String fileName = null;
			int PartnerFileResult = 0;
			int PriceFileResult = 0;
			int deletePartnerPic = new PartnerService().deletePartnerFile(partnerCode);
			int deletePricePic = new PartnerService().deletePriceFile(partnerCode);
			while(files.hasMoreElements()) {
				fileName = (String)files.nextElement();
				File uploadPic = multi.getFile(fileName);
				String fileSaveName = multi.getFilesystemName(fileName);
				PartnerPic partnerPic = new PartnerPic();
				PricePic pricePic = new PricePic();
				
				System.out.println("test " + uploadPic);
				if(uploadPic != null) {
					String filePath = uploadPic.getPath();
		            long fileSize = uploadPic.length();
		            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); 
		            Timestamp uploadTime = Timestamp.valueOf(formatter.format(Calendar.getInstance().getTimeInMillis()));
		            
		            partnerPic.setFileName(fileSaveName);
		            partnerPic.setFilePath(filePath);
		            partnerPic.setFileSize(fileSize);
		            partnerPic.setUploadTime(uploadTime);
		            PartnerFileResult = new PartnerService().updatePartnerFile(partnerPic, partnerCode);
		            
		            pricePic.setFileName(fileSaveName);
		            pricePic.setFilePath(filePath);
		            pricePic.setFileSize(fileSize);
		            pricePic.setUploadTime(uploadTime);
		            PriceFileResult = new PartnerService().updatePriceFile(pricePic, partnerCode);
				}
			}
			if(fileName != null && PartnerFileResult > 0 && PriceFileResult > 0) {
			response.sendRedirect("/admin/partnerlist");
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html");
			view.forward(request, response);
		}
	} else {
		request.getRequestDispatcher("/WEB-INF/views/gymmarket/error.html").forward(request, response);
	}
}

}