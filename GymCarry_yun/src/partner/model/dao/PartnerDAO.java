package partner.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import event.model.vo.Event;
import partner.model.vo.Partner;
import partner.model.vo.PartnerPic;
import partner.model.vo.PricePic;
import teacher.model.vo.Teacher;

public class PartnerDAO {

	public ArrayList<Partner> selectPartnerList(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT PARTNER_CODE, PARTNER_NAME, PARTNER_TYPE, PARTNER_ADDRESS, PARTNER_HOURS FROM PARTNER_DETAIL";
		ArrayList<Partner> pList = new ArrayList<Partner>();
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			while (rset.next()) {
				Partner partner = new Partner();
				partner.setPartnerCode(rset.getInt("PARTNER_CODE"));
				partner.setPartnerName(rset.getString("PARTNER_NAME"));
				partner.setPartnerType(rset.getString("PARTNER_TYPE"));
				partner.setPartnerAddress(rset.getString("PARTNER_ADDRESS"));
				partner.setPartnerHours(rset.getString("PARTNER_HOURS"));
				pList.add(partner);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(stmt);
			JDBCTemplate.close(rset);
		}
		return pList;
	}

	public ArrayList<Partner> selectByStation(Connection conn, String station) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT PARTNER_CODE, PARTNER_NAME, PARTNER_TYPE, PARTNER_ADDRESS, PARTNER_HOURS FROM PARTNER_DETAIL WHERE STATION = ?";
		ArrayList<Partner> pList = new ArrayList<Partner>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, station);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				Partner partner = new Partner();
				partner.setPartnerCode(rset.getInt("PARTNER_CODE"));
				partner.setPartnerName(rset.getString("PARTNER_NAME"));
				partner.setPartnerType(rset.getString("PARTNER_TYPE"));
				partner.setPartnerAddress(rset.getString("PARTNER_ADDRESS"));
				partner.setPartnerHours(rset.getString("PARTNER_HOURS"));
				pList.add(partner);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return pList;
	}

	public ArrayList<Partner> selectByName(Connection conn, String name) {
		// 향상된 검색기능 검색어에 공백" "이 들어가도 검색되게 해줌(검색 결과의 정확도가 높아짐)
		// 공백 3개까지 검색 가능
		// 공백이 없으면 평범하게 검색
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		ArrayList<Partner> pList = new ArrayList<Partner>();

		String[] multipleSearch = name.split(" ", 3);
		int numberOfQuery = multipleSearch.length;

		try {
			switch (numberOfQuery) {
			case 1:
				query = "SELECT PARTNER_CODE, PARTNER_NAME, PARTNER_TYPE, PARTNER_ADDRESS, PARTNER_HOURS FROM PARTNER_DETAIL WHERE PARTNER_NAME LIKE ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + multipleSearch[0] + "%");
				break;
			case 2:
				query = "SELECT PARTNER_CODE, PARTNER_NAME, PARTNER_TYPE, PARTNER_ADDRESS, PARTNER_HOURS FROM PARTNER_DETAIL WHERE PARTNER_NAME LIKE ? AND PARTNER_NAME LIKE ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + multipleSearch[0] + "%");
				pstmt.setString(2, "%" + multipleSearch[1] + "%");
				break;
			case 3:
				query = "SELECT PARTNER_CODE, PARTNER_NAME, PARTNER_TYPE, PARTNER_ADDRESS, PARTNER_HOURS FROM PARTNER_DETAIL WHERE PARTNER_NAME LIKE ? AND PARTNER_NAME LIKE ? AND PARTNER_NAME LIKE ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + multipleSearch[0] + "%");
				pstmt.setString(2, "%" + multipleSearch[1] + "%");
				pstmt.setString(3, "%" + multipleSearch[2] + "%");
				break;
			default:
				query = "SELECT PARTNER_CODE, PARTNER_NAME, PARTNER_TYPE, PARTNER_ADDRESS, PARTNER_HOURS FROM PARTNER_DETAIL WHERE PARTNER_NAME LIKE ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + name + "%");
				break;
			}

			rset = pstmt.executeQuery();
			while (rset.next()) {
				Partner partner = new Partner();
				partner.setPartnerCode(rset.getInt("PARTNER_CODE"));
				partner.setPartnerName(rset.getString("PARTNER_NAME"));
				partner.setPartnerType(rset.getString("PARTNER_TYPE"));
				partner.setPartnerAddress(rset.getString("PARTNER_ADDRESS"));
				partner.setPartnerHours(rset.getString("PARTNER_HOURS"));
				pList.add(partner);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return pList;
	}

	public int insertPartnerPic(Connection conn, PartnerPic pic) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO PARTNERPIC VALUES(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pic.getFileName());
			pstmt.setInt(2, pic.getPartnerCode());
			pstmt.setString(3, pic.getFilePath());
			pstmt.setLong(4, pic.getFileSize());
			pstmt.setTimestamp(5, pic.getUploadTime());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	public Partner selectByCode (Connection conn, int partnerCode) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM PARTNER_DETAIL WHERE PARTNER_CODE = ?";
		Partner partner = new Partner();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partnerCode);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				partner.setPartnerCode(rset.getInt("PARTNER_CODE"));
				partner.setPartnerName(rset.getString("PARTNER_NAME"));
				partner.setPartnerType(rset.getString("PARTNER_TYPE"));
				partner.setPartnerAddress(rset.getString("PARTNER_ADDRESS"));
				partner.setPartnerPhone(rset.getString("PARTNER_PHONE"));
				partner.setPartnerHours(rset.getString("PARTNER_HOURS"));
				partner.setPartnerParking(rset.getString("PARTNER_PARKING"));
				partner.setAddContent(rset.getString("ADD_CONTENT"));
				partner.setPartnerPrice(rset.getString("PARTNER_PRICE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return partner;
	}
	

	public PartnerPic selectPartnerPic(Connection conn, int partnerCode) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM PARTNERPIC WHERE PARTNER_CODE = ? AND ROWNUM = 1";
		PartnerPic partnerPic = new PartnerPic();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partnerCode);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				partnerPic.setPartnerCode(rset.getInt("PARTNER_CODE"));
				partnerPic.setFileName(rset.getString("FILENAME"));
				partnerPic.setFilePath(rset.getString("FILEPATH"));
				partnerPic.setFileSize(rset.getInt("FILESIZE"));
				partnerPic.setUploadTime(rset.getTimestamp("UPLOADTIME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return partnerPic;
	}

	//////////////////////////관리자 목록 페이지
	public ArrayList<Partner> selectPartnerListToAdmin(Connection conn, int currentPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Partner> pList = null;
		String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY PARTNER_CODE DESC) AS NUM, PARTNER_CODE, PARTNER_TYPE, PARTNER_NAME FROM PARTNER_DETAIL) WHERE NUM BETWEEN ? AND ?";
		int recordCountPerPage = 6;
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			pList = new ArrayList<Partner>();
			while (rset.next()) {
				Partner partner = new Partner();
				partner.setNum(totalCountToAdmin(conn) - rset.getInt("NUM") + 1);
				partner.setPartnerCode(rset.getInt("PARTNER_CODE"));
				partner.setPartnerType(rset.getString("PARTNER_TYPE"));
				partner.setPartnerName(rset.getString("PARTNER_NAME"));
				pList.add(partner);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		System.out.println("pListDAO : " + pList);
		return pList;
	}

	private int totalCountToAdmin(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT COUNT(*) AS TOTALCOUNT FROM PARTNER_DETAIL";
		int recordTotalCount = 0;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if (rset.next()) {
				recordTotalCount = rset.getInt("TOTALCOUNT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		return recordTotalCount;
	}

	public String getPageNaviToAdmin(Connection conn, int currentPage) {
		int recordTotalCount = totalCountToAdmin(conn);
		int recordCountPerPage = 6;
		int pageTotalCount = 0;
		if(recordTotalCount % recordCountPerPage > 0) {
			pageTotalCount = recordTotalCount / recordCountPerPage + 1;
		} else {
			pageTotalCount = recordTotalCount/ recordCountPerPage;
		}
		// 오류방지 코드
		if(currentPage < 1) {
			currentPage = 1;
		} else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		
		int naviCountPerPage = 5;
		int startNavi = ((currentPage-1)/naviCountPerPage) * naviCountPerPage +1;
		int endNavi = startNavi + naviCountPerPage -1;

		// 오류 방지 코드
		// 페이지 수 만큼 짤라줌 
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi == 1) {
			needPrev = false;
		}
		if(endNavi == pageTotalCount) {
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();
		if (needPrev) {
			sb.append("<a href='/admin/partnerlist?currentPage="+ (startNavi-1)+ "' class='btn_arr prev'> < </a>");
		}
		for(int i = startNavi; i<=endNavi; i++) {
			if(i == currentPage) {
				sb.append("<a href='/admin/partnerlist?currentPage="+ i +"' class='on'>" + i + " </a>");
			}else {
				sb.append("<a href='/admin/partnerlist?currentPage="+ i +"'>" + i + " </a>");
			}
		}
		if(needNext) {
			sb.append("<a href='/admin/partnerlist?currentPage="+ (endNavi+1) + "' class='btn_arr next'> > </a>");
		}
		return sb.toString();
		
	}

	public ArrayList<Partner> selectPartnerSearchList(Connection conn, String searchKeyword, int currentPage,
			String selectOption) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		if (selectOption.equals("partnerCode")) {
			query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY PARTNER_CODE DESC) AS NUM, " + 
					"PARTNER_CODE, PARTNER_NAME, PARTNER_TYPE " + 
					"FROM PARTNER_DETAIL WHERE PARTNER_CODE LIKE ?) WHERE NUM BETWEEN ? AND ?";
		}else if (selectOption.equals("category")) {
			query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY PARTNER_CODE DESC) AS NUM, " + 
					"PARTNER_CODE, PARTNER_NAME, PARTNER_TYPE " + 
					"FROM PARTNER_DETAIL WHERE PARTNER_TYPE LIKE ?) WHERE NUM BETWEEN ? AND ?";
		}else if (selectOption.equals("partnerName")) {
			query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY PARTNER_CODE DESC) AS NUM, " + 
					"PARTNER_CODE, PARTNER_NAME, PARTNER_TYPE " + 
					"FROM PARTNER_DETAIL WHERE PARTNER_NAME LIKE ?) WHERE NUM BETWEEN ? AND ?";
		}
		
		ArrayList<Partner> pList = null;
		int recordCountPerPage = 6;
		int start = currentPage * recordCountPerPage - (recordCountPerPage - 1);
		int end = currentPage * recordCountPerPage;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + searchKeyword + "%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rset = pstmt.executeQuery();
			pList = new ArrayList<Partner>();
			while (rset.next()) {
				Partner partner = new Partner();
				partner.setNum(searchTotalCountToAdmin(conn, searchKeyword, selectOption) - rset.getInt("NUM") + 1);
				partner.setPartnerCode(rset.getInt("PARTNER_CODE"));
				partner.setPartnerType(rset.getString("PARTNER_TYPE"));
				partner.setPartnerName(rset.getString("PARTNER_NAME"));
				pList.add(partner);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return pList;
	}

	//////////////관리자 검색 토탈카운트
	private int searchTotalCountToAdmin(Connection conn, String searchKeyword, String selectOption) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		if (selectOption.equals("partnerCode")) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM PARTNER_DETAIL WHERE PARTNER_CODE LIKE ?";
		}else if (selectOption.equals("category")) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM PARTNER_DETAIL WHERE PARTNER_TYPE LIKE ?";
		}else if (selectOption.equals("partnerName")) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM PARTNER_DETAIL WHERE PARTNER_NAME LIKE ?";
		}
		int recordTotalCount = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + searchKeyword + "%");
			rset = pstmt.executeQuery();
			if (rset.next()) {
				recordTotalCount = rset.getInt("TOTALCOUNT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return recordTotalCount;
	}

	public String getSearchPageNavi(Connection conn, String searchKeyword, int currentPage, String selectOption) {
		int recordCountPerPage = 6;
		int naviCountPerPage = 5;
		int recordTotalCount = 0;
		
		recordTotalCount = searchTotalCountToAdmin(conn, searchKeyword, selectOption);
		
		int pageTotalCount = 0; //전체페이지 개수
		if (recordTotalCount % recordCountPerPage > 0) {
			pageTotalCount = recordTotalCount/ recordCountPerPage + 1;
		}else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}
		
		//안전장치
		if (currentPage <1) {
			currentPage =1;
		}else if (currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		
		int startNavi = ((currentPage -1) / naviCountPerPage) * naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		if (endNavi >pageTotalCount) {
			endNavi = pageTotalCount;
		}
		boolean needPrev = true;
		boolean needNext = true;
		if (startNavi == 1) {
			needPrev = false;
		}
		if (endNavi == pageTotalCount) {
			needNext = false;
		}
		StringBuilder sb = new StringBuilder();
		if (needPrev) {
			sb.append("<a href='/admin/partnersearch?searchKeyword=" + searchKeyword + "&currentPage=" + (startNavi-1) + "&selectOption=" + selectOption + "' class='btn_arr prev'>  </a>");
		}
		for (int i = startNavi; i <= endNavi; i++) {
			if(i == currentPage) {
				sb.append("<a href='/admin/partnersearch?searchKeyword=" + searchKeyword + "&currentPage=" + i + "&selectOption=" + selectOption + "' class='on'> " + i + " </a>");
			}else {
				sb.append("<a href='/admin/partnersearch?searchKeyword=" + searchKeyword + "&currentPage=" + i + "&selectOption=" + selectOption + "'> " + i + " </a>");
			}
		}
		if (needNext) {
			sb.append("<a href='/admin/partnersearch?searchKeyword=" + searchKeyword + "&currentPage=" + (endNavi+1) + "&selectOption=" + selectOption + "' class='btn_arr next'>  </a>");
		}
		return sb.toString();
	}
	

	public int deletePartnerToAdmin(Connection conn, int partnerCode) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "DELETE FROM PARTNER_DETAIL WHERE PARTNER_CODE = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partnerCode);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	public ArrayList<PartnerPic> selectAllPic(Connection conn, int partnerCode) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM PARTNERPIC WHERE PARTNER_CODE = ?";
		ArrayList<PartnerPic> pPics = new ArrayList<PartnerPic>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partnerCode);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				PartnerPic partnerPic = new PartnerPic();
				partnerPic.setPartnerCode(rset.getInt("PARTNER_CODE"));
				partnerPic.setFileName(rset.getString("FILENAME"));
				partnerPic.setFilePath(rset.getString("FILEPATH"));
				partnerPic.setFileSize(rset.getInt("FILESIZE"));
				partnerPic.setUploadTime(rset.getTimestamp("UPLOADTIME"));
				pPics.add(partnerPic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return pPics;
	}
	
	   public int insertPricePic(Connection conn, PricePic pic) {
		      PreparedStatement pstmt = null;
		      int result = 0;
		      String query = "INSERT INTO PRICEPIC VALUES(?,?,?,?,?)";
		      try {
		         pstmt = conn.prepareStatement(query);
		         pstmt.setString(1, pic.getFileName());
		         pstmt.setInt(2, pic.getPartnerCode());
		         pstmt.setString(3, pic.getFilePath());
		         pstmt.setLong(4, pic.getFileSize());
		         pstmt.setTimestamp(5, pic.getUploadTime());
		         result = pstmt.executeUpdate();
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
		         JDBCTemplate.close(pstmt);
		      }
		      return result;
		   }
	
	   
	   public Teacher printTeacherList(Connection conn, int partnerCode) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = "SELECT * FROM TEACHER WHERE PARTNER_CODE = ?";
			Teacher teacher = new Teacher();
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, partnerCode);
				rset = pstmt.executeQuery();
				while (rset.next()) {
					teacher.setTchrNo(rset.getInt("TCHR_NO"));
					teacher.setPartnerCode(rset.getInt("PARTNER_CODE"));
					teacher.setTchName(rset.getString("TCHR_NAME"));
					teacher.setTchrCareer(rset.getString("TCHR_CAREER"));
					teacher.setTchClass(rset.getString("TCHR_CLASS"));
					teacher.setFileName(rset.getString("FILENAME"));
					teacher.setFilePath(rset.getString("FILEPATH"));
					teacher.setFileSize(rset.getInt("FILESIZE"));
					teacher.setUploadtime(rset.getTimestamp("UPLOADTIME"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(pstmt);
				JDBCTemplate.close(rset);
			}
			return teacher;
		}

	public ArrayList<PricePic> selectPricePic(Connection conn, int partnerCode) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM PRICEPIC WHERE PARTNER_CODE = ?";
		ArrayList<PricePic> pricePics = new ArrayList<PricePic>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partnerCode);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				PricePic pricePic = new PricePic();
				pricePic.setPartnerCode(rset.getInt("PARTNER_CODE"));
				pricePic.setFileName(rset.getString("FILENAME"));
				pricePic.setFilePath(rset.getString("FILEPATH"));
				pricePic.setFileSize(rset.getLong("FILESIZE"));
				pricePic.setUploadTime(rset.getTimestamp("UPLOADTIME"));
				pricePics.add(pricePic);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return pricePics;
	}
	
	public int modifyPartner(Connection conn, Partner partner) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "UPDATE PARTNER_DETAIL SET PARTNER_NAME = ?, PARTNER_TYPE = ?, "
				+ "PARTNER_ADDRESS = ?, PARTNER_PHONE = ?, PARTNER_HOURS = ?, "
				+ "PARTNER_PARKING = ?, PARTNER_PRICE = ?, ADD_CONTENT = ? "
				+ "WHERE PARTNER_CODE = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, partner.getPartnerName());
			pstmt.setString(2, partner.getPartnerType());
			pstmt.setString(3, partner.getPartnerAddress());
			pstmt.setString(4, partner.getPartnerPhone());
			pstmt.setString(5, partner.getPartnerHours());
			pstmt.setString(6, partner.getPartnerParking());
			pstmt.setString(7, partner.getPartnerPrice());
			pstmt.setString(8, partner.getAddContent());
			pstmt.setInt(9, partner.getPartnerCode());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int deletePartnerFile(Connection conn, int partnerCode) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "DELETE FROM PARTNERPIC WHERE PARTNER_CODE = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partnerCode);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deletePriceFile(Connection conn, int partnerCode) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "DELETE FROM PRICEPIC WHERE PARTNER_CODE = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partnerCode);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int updatePartnerFile(Connection conn, PartnerPic partnerPic, int partnerCode) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO PARTNERPIC VALUES (?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, partnerPic.getFileName());
			pstmt.setInt(2, partnerCode);
			pstmt.setString(3, partnerPic.getFilePath());
			pstmt.setLong(4, partnerPic.getFileSize());
			pstmt.setTimestamp(5, partnerPic.getUploadTime());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updatePriceFile(Connection conn, PricePic pricePic, int partnerCode) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO PRICEPIC VALUES (?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partnerCode);
			pstmt.setString(2, pricePic.getFileName());
			pstmt.setString(3, pricePic.getFilePath());
			pstmt.setLong(4, pricePic.getFileSize());
			pstmt.setTimestamp(5, pricePic.getUploadTime());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	//업체 등록
		public int getSquenceNo(Connection conn) {
			Statement stmt = null;
			ResultSet rset = null;
			int partnerCode = 0;
			String query = "SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'PARTNER_DETAIL_SEQ'";
			try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				partnerCode = rset.getInt("LAST_NUMBER");
			}
			} catch (Exception e) {
				e.printStackTrace();
				partnerCode = -1;
			}
			return partnerCode;
		}
		//업체코드명을 반환해야 다음 차례에서 사용할 수 있음
		public int insertPartner(Connection conn, Partner partner) {
			PreparedStatement pstmt = null;
			String query = "INSERT INTO PARTNER_DETAIL VALUES(PARTNER_DETAIL_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?)";
			int partnerCode = getSquenceNo(conn);
			if(partnerCode != -1) {
				try {
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, partner.getPartnerName());
					pstmt.setString(2, partner.getPartnerType());
					pstmt.setString(3, partner.getPartnerAddress());
					pstmt.setString(4, partner.getPartnerPhone());
					pstmt.setString(5, partner.getPartnerHours());
					pstmt.setString(6, partner.getPartnerParking());
					pstmt.setString(7, partner.getStation());
					pstmt.setString(8, partner.getAddContent());
					pstmt.setString(9, partner.getPartnerPrice());
					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					JDBCTemplate.close(pstmt);
					return partnerCode;
				}
			}
			return partnerCode;
		}
		
	
	
	
}
