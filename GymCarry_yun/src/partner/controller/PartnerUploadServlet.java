package partner.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import partner.model.service.PartnerService;
import partner.model.vo.Partner;
import partner.model.vo.PartnerPic;
import partner.model.vo.PricePic;
import teacher.model.service.TeacherService;
import teacher.model.vo.Teacher;

@WebServlet("/partner/upload")
public class PartnerUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 관리자 인증?
//		HttpSession session = request.getSession();
//		if(session != null && (session.getAttribute("uniqId")).equals("admin")) {
//		}

		// 폴더에 파일을 저장
		String uploadFilePath = request.getServletContext().getRealPath("/resources/PUpload");
		int sizeLimit = 50 * 1024 * 1024; // 저장할 수 있는 용량의 한도 (50MB)
		String encType = "UTF-8";
		MultipartRequest multi = new MultipartRequest(request, uploadFilePath, sizeLimit, encType,
				new DefaultFileRenamePolicy());
		
		// 업체정보
		String category = multi.getParameter("category");
		if(category.equals("기타")) {
			category = multi.getParameter("category2");
		}
		
		String partnerName = multi.getParameter("partnerName");
		String partnerAddress = multi.getParameter("partnerAddress");
		String station = multi.getParameter("station");
		String partnerPrice = multi.getParameter("partnerPrice");
		String partnerHours = multi.getParameter("partnerHours");
		String partnerParking = multi.getParameter("parking");
		String partnerPhone = multi.getParameter("partnerPhone");
		String addContent = multi.getParameter("addContent");

		Partner partner = new Partner();
		partner.setPartnerName(partnerName);
		partner.setPartnerAddress(partnerAddress);
		partner.setStation(station);
		partner.setPartnerPrice(partnerPrice);
		partner.setPartnerHours(partnerHours);
		partner.setPartnerParking(partnerParking);
		partner.setPartnerPhone(partnerPhone);
		partner.setAddContent(addContent);
		partner.setPartnerType(category);

		int partnerCode = new PartnerService().addPartner(partner);

		if (partnerCode != -1) {
			// 업체정보 등록이 정상적으로 되었을 때의 진행경로
			System.out.println("message: 업체등록 성공");

	         try {
	         Teacher teacher = new Teacher();
	         teacher.setPartnerCode(partnerCode);
	         teacher.setTchName(multi.getParameter("tchrName"));
	         teacher.setTchClass(multi.getParameter("tchrClass"));
	         teacher.setTchrCareer(multi.getParameter("tchrCareer"));
	         int tResult = new TeacherService().insertTeacher(teacher);
	         if (tResult > 0)
	            System.out.println("message: 강사등록성공");
	         else
	            System.out.println("message: 강사등록실패");
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
			Enumeration files = multi.getFileNames();
			String fileName = null;
			int picResult = 0;
			while (files.hasMoreElements()) {
				try {
					fileName = (String) files.nextElement();
					File uploadPic = multi.getFile(fileName);
					String fileSaveName = multi.getFilesystemName(fileName);
					String filePath = uploadPic.getPath();
					long fileSize = uploadPic.length();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Timestamp uploadTime = Timestamp.valueOf(formatter.format(Calendar.getInstance().getTimeInMillis()));
					if (!fileName.contains("Price") && fileName.contains("Pic")) {
						PartnerPic partnerPic = new PartnerPic();
						partnerPic.setFileName(fileSaveName);
						partnerPic.setFilePath(filePath);
						partnerPic.setFileSize(fileSize);
						partnerPic.setPartnerCode(partnerCode);
						partnerPic.setUploadTime(uploadTime);

						picResult = new PartnerService().insertPartnerPic(partnerPic);
						if (picResult > 0)
							System.out.println("message: 사진업로드성공");
					} else if (fileName.contains("Price")) {
						PricePic pricePic = new PricePic();
						pricePic.setFileName(fileSaveName);
						pricePic.setFilePath(filePath);
						pricePic.setFileSize(fileSize);
						pricePic.setPartnerCode(partnerCode);
						pricePic.setUploadTime(uploadTime);
						picResult = new PartnerService().insertPricePic(pricePic);
						if (picResult > 0)
							System.out.println("message: 사진업로드성공");
					}
				} catch (NullPointerException e) {
				}
			}
			request.setAttribute("msg", "업체 등록이 완료되었습니다");
			request.getRequestDispatcher("/admin/partnerlist").forward(request, response);
		}
	}

}
