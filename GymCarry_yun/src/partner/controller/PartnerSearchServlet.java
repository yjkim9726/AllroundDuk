package partner.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import partner.model.service.PartnerService;
import partner.model.vo.Partner;
import partner.model.vo.PartnerPageData;
import partner.model.vo.PartnerPic;

/**
 * Servlet implementation class PartnerSearchServler
 */
@WebServlet("/partner/search")
public class PartnerSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PartnerSearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//짐팟 검색 기능***************=******************************************
		String searchQuery = null;
		String station = "";
		ArrayList<Partner> pList = null;

		searchQuery = (String) request.getParameter("query");
		if (searchQuery != null) {
			// 역 정보로 검색하기(단, 역 이름이 검색어의 맨 앞에 있어야 정상 작동함)
			if (searchQuery.indexOf("역") != -1 && searchQuery.indexOf("역") != 0) {
				station = searchQuery.substring(0, searchQuery.indexOf("역") + 1);
				pList = new PartnerService().searchByStation(station);
			} else {
				// 이름으로 검색하기
				station = "";
				pList = new PartnerService().searchPartnerList(searchQuery);
			}
		} else {
			// 페이지를 처음 로드했을 때 기본값(default) 설정
			station = "종각역";
			pList = new PartnerService().searchByStation(station);
		}
		if (!pList.isEmpty()) {
			//페이징 처리는 여기서
			int currentPage;
	
			if(request.getParameter("page") != null) {
				currentPage = Integer.parseInt(request.getParameter("page"));
			} else {
				currentPage = 1;
			}
			PartnerPageData pPages = new PartnerService().getPageData(pList.size(), currentPage);
			ArrayList<PartnerPic> pPics = new ArrayList<PartnerPic>(); //이미지 가져올 변수
			
			int pListCntMin = (currentPage - 1) * 4;
			int pListCntMax = (currentPage) * 4;
			if(pListCntMax > pList.size()) {
				pListCntMax = pList.size();
			}
			for(int i = pListCntMin; i < pListCntMax; i++) {
				//해당하는 업체 코드
				
				try {
					int partnerCode = pList.get(i).getPartnerCode();
					PartnerPic partnerPic = new PartnerService().printOnePic(partnerCode);
					pPics.add(partnerPic);
				} catch (IndexOutOfBoundsException e) {
				}
			}
			//페이징을 하고 그 데이터를 토대로 업체코드를 가져올겁니다.
			//그러면 그 업체코드를 가지고 사진을 가져옵니다.
			
			request.setAttribute("pList", pList);
			request.setAttribute("pPages", pPages);
			request.setAttribute("pPics", pPics);
			request.setAttribute("station",station);
			request.setAttribute("query", searchQuery);
			request.getRequestDispatcher("/WEB-INF/views/gympot/gympot.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/WEB-INF/views/gympot/error.html").forward(request, response);
		}
		//************************************************************************
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
