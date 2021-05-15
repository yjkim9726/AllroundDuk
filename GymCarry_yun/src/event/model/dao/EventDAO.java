package event.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.sun.org.glassfish.external.statistics.annotations.Reset;

import common.JDBCTemplate;
import event.model.vo.Event;
import event.model.vo.EventPic;

public class EventDAO {

	public ArrayList<Event> selectEventList(Connection conn, int currentPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Event> eList = null;
		String query = "SELECT * FROM(SELECT ROW_NUMBER() OVER(ORDER BY EVENT_NO DESC) AS NUM, "
						+ "EVENT_NO, PARTNER_NAME, EVENT_ADDRESS, EVENT_TITLE, "
						+ "SUBSTR(START_DATE, 1, 10)AS START_DATE, "
						+ "SUBSTR(END_DATE, 1, 10)AS END_DATE,"
						+ "(SELECT FILENAME FROM EVENTPIC WHERE EVENT_NO = EVENT.EVENT_NO AND ROWNUM = 1)AS FILENAME "
					+ "FROM EVENT WHERE CONFIRM = 'Y')WHERE NUM BETWEEN ? AND ?";
		
		int recordCountPerPage = 6;
		int start = currentPage * recordCountPerPage - (recordCountPerPage -1);
		int end = currentPage * recordCountPerPage;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			eList = new ArrayList<Event>();
			if (rset != null) {
				while (rset.next()) {
					Event event = new Event();
					event.setEventNo(rset.getInt("EVENT_NO"));
					event.setPartnerName(rset.getString("PARTNER_NAME"));
					event.setEventAddress(rset.getString("EVENT_ADDRESS"));
					event.setEventTitle(rset.getString("EVENT_TITLE"));
					event.setStartDate(rset.getString("START_DATE"));
					event.setEndDate(rset.getString("END_DATE"));
					event.setFileName(rset.getString("FILENAME"));
					eList.add(event);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return eList;
	}
	///////////////////////////////관리자페이지 이벤트//////////////////////////////////
	public ArrayList<Event> selectEventListToAdmin(Connection conn, int currentPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Event> eList = null;
		String query = "SELECT * FROM(SELECT ROW_NUMBER() OVER(ORDER BY EVENT_NO DESC) AS NUM, "
				+ "EVENT_NO, PARTNER_NAME, EVENT_ADDRESS, EVENT_TITLE, "
				+ "SUBSTR(START_DATE, 1, 10)AS START_DATE, SUBSTR(END_DATE, 1, 10)AS END_DATE, CONFIRM FROM EVENT)WHERE NUM BETWEEN ? AND ?";
		
		int recordCountPerPage = 6;
		int start = currentPage * recordCountPerPage - (recordCountPerPage -1);
		int end = currentPage * recordCountPerPage;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			eList = new ArrayList<Event>();
			if (rset != null) {
				while (rset.next()) {
					Event event = new Event();
					event.setEventNo(rset.getInt("EVENT_NO"));
					event.setPartnerName(rset.getString("PARTNER_NAME"));
					event.setEventAddress(rset.getString("EVENT_ADDRESS"));
					event.setEventTitle(rset.getString("EVENT_TITLE"));
					event.setStartDate(rset.getString("START_DATE"));
					event.setEndDate(rset.getString("END_DATE"));
					event.setConfirm(rset.getString("CONFIRM"));
					eList.add(event);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return eList;
	}
	////////////////////////메인페이지 이벤트목록 -///////////////////////////////// 
	public ArrayList<Event> selectEventListToMain(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Event> eList = null;
		String query = "SELECT * FROM(SELECT ROW_NUMBER() OVER(ORDER BY EVENT_NO DESC) AS NUM, EVENT_NO, "
				+ "PARTNER_NAME, EVENT_ADDRESS, EVENT_TITLE, "
				+ "SUBSTR(START_DATE, 1, 10)AS START_DATE, SUBSTR(END_DATE, 1, 10)AS END_DATE, CONFIRM, "
				+ "(SELECT FILENAME FROM EVENTPIC WHERE EVENT_NO = EVENT.EVENT_NO AND ROWNUM = 1)AS FILENAME "
				+ "FROM EVENT  WHERE CONFIRM = 'Y')WHERE NUM BETWEEN 1 AND 3";
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			eList = new ArrayList<Event>();
			if (rset != null) {
				while (rset.next()) {
					Event event = new Event();
					event.setEventNo(rset.getInt("EVENT_NO"));
					event.setPartnerName(rset.getString("PARTNER_NAME"));
					event.setEventAddress(rset.getString("EVENT_ADDRESS"));
					event.setEventTitle(rset.getString("EVENT_TITLE"));
					event.setStartDate(rset.getString("START_DATE"));
					event.setEndDate(rset.getString("END_DATE"));
					event.setFileName(rset.getString("FILENAME"));
					eList.add(event);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return eList;
	}

	public String getPageNavi(Connection conn, int currentPage) {
		int recordTotalCount = totalCount(conn);
		int recordCountPerPage = 6;
		int pageTotalCount = 0;
		//�������� 0�� �ƴѰ�� : 3������ �������� - �������� �߰��������
		if (recordTotalCount % recordCountPerPage > 0) { 
			pageTotalCount = recordTotalCount / recordCountPerPage + 1;
		}else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}
		
		// ���������ڵ�
		if (currentPage<1) {
			currentPage = 1;
		}else if (currentPage >pageTotalCount) {
			currentPage = pageTotalCount;
		}
		
		// ���� ���������� �������� ������ ��� �����ٰ���
		// 1 2 3 4 5, 6 7 8
		int naviCountPerPage = 5;
		int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		if (endNavi > pageTotalCount) {
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
			sb.append("<a href='/geundeal/list?currentPage=" + (startNavi-1) + "' class='btn_arr prev'>  </a>");
		}
		for (int i = startNavi; i <= endNavi; i++) {
			if(i == currentPage) {
				sb.append("<a href='/geundeal/list?currentPage=" + i + "' class='on'>" + i + " </a>");
			}else {
				sb.append("<a href='/geundeal/list?currentPage=" + i + "'>" + i + " </a>");
			};
		}
		if (needNext) {
			sb.append("<a href='/geundeal/list?currentPage=" + (endNavi + 1) + "' class='btn_arr next'>  </a>");
		}
		return sb.toString();
	}
	 // 관리자 이벤트목록페이지네비
	public String getPageNaviToAdmin(Connection conn, int currentPage) {
		int recordTotalCount = totalCountToAdmin(conn);
		int recordCountPerPage = 6;
		int pageTotalCount = 0;
		if (recordTotalCount % recordCountPerPage > 0) { 
			pageTotalCount = recordTotalCount / recordCountPerPage + 1;
		}else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}
		
		if (currentPage<1) {
			currentPage = 1;
		}else if (currentPage >pageTotalCount) {
			currentPage = pageTotalCount;
		}
		
		// 1 2 3 4 5, 6 7 8
		int naviCountPerPage = 5;
		int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		if (endNavi > pageTotalCount) {
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
			sb.append("<a href='/admin/eventlist?currentPage=" + (startNavi-1) + "' class='btn_arr prev'>  </a>");
		}
		for (int i = startNavi; i <= endNavi; i++) {
			if(i == currentPage) {
				sb.append("<a href='/admin/eventlist?currentPage=" + i + "' class='on'>" + i + " </a>");
			}else {
				sb.append("<a href='/admin/eventlist?currentPage=" + i + "'>" + i + " </a>");
			};
		}
		if (needNext) {
			sb.append("<a href='/admin/eventlist?currentPage=" + (endNavi + 1) + "' class='btn_arr next'>  </a>");
		}
		return sb.toString();
	}

	///////////////////////////////근딜리스트 총개수
	private int totalCount(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT COUNT(*) AS TOTALCOUNT FROM EVENT WHERE CONFIRM ='Y'";
		int recordTotalCount = 0;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if (rset.next()) {
				recordTotalCount = rset.getInt("TOTALCOUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		return recordTotalCount;
	}
	
	///////////////////////////////관리자 근딜리스트 총개수(Y, N)
	private int totalCountToAdmin(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT COUNT(*) AS TOTALCOUNT FROM EVENT";
		int recordTotalCount = 0;
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if (rset.next()) {
				recordTotalCount = rset.getInt("TOTALCOUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		return recordTotalCount;
	}
	
	


	public int insertEvent(Connection conn, Event event) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO EVENT VALUES(SEQ_EVENT.NEXTVAL, ?,?,?,?,?,?,'N')";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, event.getPartnerName());
			pstmt.setString(2, event.getEventAddress());
			pstmt.setString(3, event.getEventTitle());
			pstmt.setString(4, event.getEventContent());
			pstmt.setString(5, event.getStartDate());
			pstmt.setString(6, event.getEndDate());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int inserEventFileInfo(Connection conn, EventPic eventPic) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO EVENTPIC VALUES(?,(SELECT MAX(EVENT_NO) FROM EVENT WHERE PARTNER_NAME=?), ?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, eventPic.getFileName());
			pstmt.setString(2, eventPic.getFileUser());
			pstmt.setString(3, eventPic.getFilePath());
			pstmt.setLong(4, eventPic.getFileSize());
			pstmt.setString(5, eventPic.getFileUser());
			pstmt.setTimestamp(6, eventPic.getUploadTime());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public Event selectOneEvent(Connection conn, int eventNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Event event = null;
		String query = "SELECT * FROM EVENT WHERE EVENT_NO = ?"; // 위치홀더는 물음표
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, eventNo);
			rset = pstmt.executeQuery(); //이거 안적으면 쿼리문실행 안됨
			if (rset.next()) { // 한개만 가져올테니 if문으로 체크
				event = new Event();
				event.setEventNo(rset.getInt("EVENT_NO"));
				event.setPartnerName(rset.getString("PARTNER_NAME"));
				event.setEventAddress(rset.getString("EVENT_ADDRESS"));
				event.setEventTitle(rset.getString("EVENT_TITLE"));
				event.setEventContent(rset.getString("EVENT_CONTENT"));
				event.setStartDate(rset.getString("START_DATE"));
				event.setEndDate(rset.getString("END_DATE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return event;
	}
	
	//////////////////////////////////////////////////////////사진가져오기
	public ArrayList<EventPic> selectOneEventFile(Connection conn, int eventNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<EventPic> eList = null;
		String query = "SELECT FILENAME FROM EVENTPIC WHERE EVENT_NO =?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, eventNo);
			rset = pstmt.executeQuery();
			eList = new ArrayList<EventPic>();
			if (rset != null) {
				while(rset.next()) {
					EventPic eventPic = new EventPic();
					eventPic.setFileName(rset.getString("FILENAME"));
					eList.add(eventPic);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return eList;
	}

	////////////////검색/////////////////////////////
	public ArrayList<Event> selectEventSearchList(Connection conn, String search, int currentPage, String catename) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		if(catename.equals("EVENT_NO")) {
			query = "SELECT * FROM" + 
					"    (SELECT ROW_NUMBER() OVER(ORDER BY EVENT_NO DESC) AS NUM," + 
					"        EVENT_NO, PARTNER_NAME, CONFIRM, EVENT_TITLE," + 
					"        SUBSTR(START_DATE, 1, 10)AS START_DATE," + 
					"        SUBSTR(END_DATE, 1, 10)AS END_DATE" + 
					"    FROM EVENT" + 
					"    WHERE EVENT_NO LIKE ?)" + 
					"WHERE NUM BETWEEN ? AND ?";
		}else if(catename.equals("EVENT_TITLE")) {
			query = "SELECT * FROM" + 
					"    (SELECT ROW_NUMBER() OVER(ORDER BY EVENT_NO DESC) AS NUM," + 
					"        EVENT_NO, PARTNER_NAME, CONFIRM, EVENT_TITLE," + 
					"        SUBSTR(START_DATE, 1, 10)AS START_DATE," + 
					"        SUBSTR(END_DATE, 1, 10)AS END_DATE" + 
					"    FROM EVENT" + 
					"    WHERE EVENT_TITLE LIKE ?)" + 
					"WHERE NUM BETWEEN ? AND ?";
		}else if(catename.equals("PARTNER_NAME")) {
			query = "SELECT * FROM" + 
					"    (SELECT ROW_NUMBER() OVER(ORDER BY EVENT_NO DESC) AS NUM," + 
					"        EVENT_NO, PARTNER_NAME, CONFIRM, EVENT_TITLE," + 
					"        SUBSTR(START_DATE, 1, 10)AS START_DATE," + 
					"        SUBSTR(END_DATE, 1, 10)AS END_DATE" + 
					"    FROM EVENT" + 
					"    WHERE PARTNER_NAME LIKE ?)" + 
					"WHERE NUM BETWEEN ? AND ?";
		}
		
		ArrayList<Event> eList = null;
		int recordCountPerPage = 6;
		int start = currentPage * recordCountPerPage - (recordCountPerPage - 1);
		int end = currentPage * recordCountPerPage;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + search + "%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rset = pstmt.executeQuery();
			eList = new ArrayList<Event>();
			while (rset.next()) {
				Event event = new Event();
				event.setEventNo(rset.getInt("EVENT_NO"));
				event.setPartnerName(rset.getString("PARTNER_NAME"));
				event.setEventTitle(rset.getString("EVENT_TITLE"));
				event.setStartDate(rset.getString("START_DATE"));
				event.setEndDate(rset.getString("END_DATE"));
				event.setConfirm(rset.getString("CONFIRM"));
				eList.add(event);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return eList;
	}

	/////////////////////////관리자 근딜 검색 목록 
	public String getSearchPageNavi(Connection conn, String search, int currentPage, String cate) {
		int recordCountPerPage = 6;
		int naviCountPerPage = 5;
		int recordTotalCount = 0;
		
		// 설렉트 값에 따라서 다른 토탈 카운트를 가져오는 코드
		recordTotalCount = searchTotalCount(conn, search, cate);
		
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
			sb.append("<a href='/admin/eventsearch?searchKeyword=" + search + "&currentPage=" + (startNavi-1) + "&cate=" + cate + "' class='btn_arr prev'>  </a>");
		}
		for (int i = startNavi; i <= endNavi; i++) {
			if(i == currentPage) {
				sb.append("<a href='/admin/eventsearch?searchKeyword=" + search + "&currentPage=" + i + "&cate=" + cate + "' class='on'> " + i + " </a>");
			}else {
				sb.append("<a href='/admin/eventsearch?searchKeyword=" + search + "&currentPage=" + i + "&cate=" + cate + "'> " + i + " </a>");
			}
		}
		if (needNext) {
			sb.append("<a href='/admin/eventsearch?searchKeyword=" + search + "&currentPage=" + (endNavi+1) + "&cate=" + cate + "' class='btn_arr next'>  </a>");
		}
		return sb.toString();
		
	}

	///////////////////////////검색창 토탈카운트
	private int searchTotalCount(Connection conn, String search, String catename) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		if(catename.equals("EVENT_NO")) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM EVENT WHERE EVENT_NO LIKE ?";
		}else if (catename.equals("EVENT_TITLE")) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM EVENT WHERE EVENT_TITLE LIKE ?";
		}else {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM EVENT WHERE PARTNER_NAME LIKE ?";
		}
		int recordTotalCount = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + search + "%");
			rset = pstmt.executeQuery();
			if (rset.next()) {
				recordTotalCount = rset.getInt("TOTALCOUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return recordTotalCount;
	}
	
	/////////////////////////관리자 이벤트 승인
	public int updateEventConfirm(Connection conn, Event event) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "UPDATE EVENT SET CONFIRM='Y' WHERE EVENT_NO= ?";
		int result=0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, event.getEventNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	public int deleteEvent(Connection conn, int eventNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "DELETE FROM EVENT WHERE EVENT_NO=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, eventNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	
}
