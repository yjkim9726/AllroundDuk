package market.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.JDBCTemplate;
import market.model.vo.Market;
import market.model.vo.MarketPic;

public class MarketDAO {

	//////////////마켓 목록 페이지
	public ArrayList<Market> printAllMarketList(Connection conn,int currentPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Market> mList = null;
		String query="SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY MARKET_NO DESC) AS NUM, "
				+ "MARKET_NO, NICKNAME, MARKET_TITLE, MARKET_PRICE, MARKET_CONTENT, MARKET_FILED,"
				+ "MARKET_DATE FROM MARKET JOIN MEMBER USING (UNIQ_ID)) WHERE NUM BETWEEN ? AND ?";
		int recordCountPerPage = 6;
		int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage*recordCountPerPage;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			mList = new ArrayList<Market>();
			while(rset.next()) {
				Market market = new Market();
				market.setNum(totalCount(conn) - rset.getInt("NUM") + 1);
				market.setMarketNo(rset.getInt("MARKET_NO"));
				market.setNickName(rset.getString("NICKNAME"));
				market.setMarketTitle(rset.getString("MARKET_TITLE"));
				market.setMarketPrice(rset.getString("MARKET_PRICE"));
				market.setMarketContent(rset.getString("MARKET_CONTENT"));
				market.setMarketField(rset.getString("MARKET_FILED"));
				market.setMarketDate(rset.getDate("MARKET_DATE"));
				mList.add(market);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return mList;
	}

	//////메인페이지에서 보이는 마켓리스트
	public ArrayList<Market> printAllMarketListToMain(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Market> mList = null;
		String query="SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY MARKET_NO DESC) AS NUM, MARKET_NO, NICKNAME, MARKET_TITLE, MARKET_PRICE, MARKET_CONTENT, MARKET_FILED,MARKET_DATE FROM MARKET JOIN MEMBER USING (UNIQ_ID)) WHERE NUM BETWEEN 1 AND 3";
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			mList = new ArrayList<Market>();
			while(rset.next()) {
				Market market = new Market();
				market.setNum(totalCount(conn) - rset.getInt("NUM") + 1);
				market.setMarketNo(rset.getInt("MARKET_NO"));
				market.setNickName(rset.getString("NICKNAME"));
				market.setMarketTitle(rset.getString("MARKET_TITLE"));
				market.setMarketPrice(rset.getString("MARKET_PRICE"));
				market.setMarketContent(rset.getString("MARKET_CONTENT"));
				market.setMarketField(rset.getString("MARKET_FILED"));
				market.setMarketDate(rset.getDate("MARKET_DATE"));
				mList.add(market);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return mList;
	}
	
	// 마이페이지에서 내가 쓴 양도게시글 불러오는 코드 
		public ArrayList<Market> printIdMarketList(Connection conn, int currentPage, int uniqId) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ArrayList<Market> mList = null;
			String query="SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY MARKET_NO DESC) AS NUM, MARKET_NO, NICKNAME, MARKET_TITLE, MARKET_PRICE, MARKET_CONTENT, MARKET_FILED,MARKET_DATE FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE UNIQ_ID = ?) WHERE NUM BETWEEN ? AND ?";
			int recordCountPerPage = 6;
			int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
			int end = currentPage*recordCountPerPage;
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, uniqId);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				rset = pstmt.executeQuery();
				mList = new ArrayList<Market>();
				while(rset.next()) {
					Market market = new Market();
					market.setNum(totalIdCount(conn,uniqId) - rset.getInt("NUM") + 1);
					market.setMarketNo(rset.getInt("MARKET_NO"));
					market.setNickName(rset.getString("NICKNAME"));
					market.setMarketTitle(rset.getString("MARKET_TITLE"));
					market.setMarketPrice(rset.getString("MARKET_PRICE"));
					market.setMarketContent(rset.getString("MARKET_CONTENT"));
					market.setMarketField(rset.getString("MARKET_FILED"));
					market.setMarketDate(rset.getDate("MARKET_DATE"));
					mList.add(market);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
			
			return mList;
		}
		// 마이페이지 내가 쓴 양도 게시글 페이징처리 
		public String getIdPageNavi(Connection conn, int currentPage, int uniqId) {
			int recordTotalCount = totalIdCount(conn,uniqId);
			System.out.println(recordTotalCount);
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
			if(needPrev) {
				sb.append("<a href='/mypage/writeList?currentPage="+ (startNavi-1)+ "' class='btn_arr prev'> < </a>");
			}
			for(int i = startNavi; i<=endNavi; i++) {
				if(i == currentPage) {
		            sb.append("<a href='/mypage/writeList?currentPage=" + i + "' class='on'>" + i + " </a>");
		         }else {
		            sb.append("<a href='/mypage/writeList?currentPage="+ i +"'>  "+ i + "  </a>");
		         };
			}
			if(needNext) {
				sb.append("<a href='/mypage/writeList?currentPage="+ (endNavi+1) + "' class='btn_arr next'> > </a>");
			}
			return sb.toString();
		}
	
	public String getPageNavi(Connection conn, int currentPage) {
		int recordTotalCount = totalCount(conn);
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
		if(needPrev) {
			sb.append("<a href='/market/list?currentPage="+ (startNavi-1)+ "'> < </a>");
		}
		for(int i = startNavi; i<=endNavi; i++) {
			sb.append("<a href='/market/list?currentPage="+ i +"'> "+ i + " </a>");
		}
		if(needNext) {
			sb.append("<a href='/market/list?currentPage="+ (endNavi+1) + "'> > </a>");
		}
		return sb.toString();
	}
	
	public String getPageNaviToAdmin(Connection conn, int currentPage) {
		int recordTotalCount = totalCount(conn);
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
		if(needPrev) {
			sb.append("<a href='/admin/marketlist?currentPage="+ (startNavi-1)+ "' class='btn_arr prev'> < </a>");
		}
		for(int i = startNavi; i<=endNavi; i++) {
			if(i == currentPage) {
				sb.append("<a href='/admin/marketlist?currentPage="+ i +"' class='on'>" + i + " </a>");
			}else {
				sb.append("<a href='/admin/marketlist?currentPage="+ i +"'>" + i + " </a>");
			}
		}
		if(needNext) {
			sb.append("<a href='/admin/marketlist?currentPage="+ (endNavi+1) + "' class='btn_arr next'> > </a>");
		}
		return sb.toString();
	}
	public int totalIdCount(Connection conn, int uniqId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT COUNT(*) AS TOTALCOUNT FROM MARKET WHERE UNIQ_ID=?";
		int recordTotalCount = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,uniqId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
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
	
	public int totalCount(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT COUNT(*) AS TOTALCOUNT FROM MARKET";
		int recordTotalCount = 0;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
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

	public int insertMarket(Connection conn, Market market) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO MARKET VALUES (SEQ_MARKETNO.NEXTVAL,?,?,?,?,?, SYSDATE)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,market.getUniqId());
			pstmt.setString(2,market.getMarketTitle());
			pstmt.setString(3,market.getMarketPrice());
			pstmt.setString(4,market.getMarketContent());
			pstmt.setString(5,market.getMarketField());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public Market printOneMarket(Connection conn, int marketNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Market market = null;
		String query = "SELECT MARKET_NO, NICKNAME, UNIQ_ID, MARKET_TITLE,MARKET_PRICE,MARKET_CONTENT,MARKET_FILED,MARKET_DATE FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE MARKET_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, marketNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				market = new Market();
				market.setMarketNo(rset.getInt("MARKET_NO"));
				market.setNickName(rset.getString("NICKNAME"));
				market.setUniqId(rset.getInt("UNIQ_ID"));
				market.setMarketTitle(rset.getString("MARKET_TITLE"));
				market.setMarketPrice(rset.getString("MARKET_PRICE"));
				market.setMarketContent(rset.getString("MARKET_CONTENT"));
				market.setMarketField(rset.getString("MARKET_FILED"));
				market.setMarketDate(rset.getDate("MARKET_DATE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return market;
	}

	public int printOneMarketFile(Connection conn, MarketPic marketPic, int uniqId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO MARKETPIC VALUES (?,(SELECT MAX(MARKET_NO) FROM MARKET WHERE UNIQ_ID=?),?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, marketPic.getFileName());
			pstmt.setInt(2, uniqId);
			pstmt.setString(3, marketPic.getFilePath());
			pstmt.setLong(4, marketPic.getFileSize());
			pstmt.setString(5, marketPic.getFileUser());
			pstmt.setTimestamp(6, marketPic.getUploadTime());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<MarketPic> printListPic(Connection conn, int marketNo) {
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		ArrayList<MarketPic> list = null;
		String query = "SELECT * FROM MARKETPIC WHERE MARKET_NO = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, marketNo);
			rset = pstmt.executeQuery();
			list = new ArrayList<MarketPic>();
			while(rset.next()) {
				MarketPic mPic = new MarketPic();
				mPic.setFileName(rset.getString("FILENAME"));
				mPic.setFilePath(rset.getString("FILEPATH"));
				mPic.setFileSize(rset.getInt("FILESIZE"));
				mPic.setUploadTime(rset.getTimestamp("UPLOADTIME"));
				list.add(mPic);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	// 찾기 기능@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		public String getSearchPageNavi(Connection conn, String search, String selectOption, int currentPage) {
			int recordCountPerPage = 6; 
			int naviCountPerPage = 5; 
			int recordTotalCount = 0;
			
			// 설렉트 값에 따라서 다른 토탈 카운트를 가져오는 코드
			if(selectOption.equals("title")) {
				recordTotalCount = searchTotalCountTitle(conn,search);		
			} else if(selectOption.equals("writer")) {
				recordTotalCount = searchTotalCountWriter(conn,search);
			} else {
				recordTotalCount = searchTotalCountContent(conn,search);
			}
			
			int pageTotalCount = 0; // 전체 페이지의 개수
			if(recordTotalCount % recordCountPerPage > 0) {
				pageTotalCount = recordTotalCount / recordCountPerPage +1;
			}else {
				pageTotalCount = recordTotalCount / recordCountPerPage;			
			}
			// 안전장치
			if(currentPage < 1) {
				currentPage = 1;
			} else if(currentPage > pageTotalCount) {
				currentPage = pageTotalCount;
			}
			int startNavi = ((currentPage-1)/naviCountPerPage) * naviCountPerPage +1;
			int endNavi = startNavi + naviCountPerPage -1;
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
			if(needPrev) {
				sb.append("<a href='/market/search?searchKeyword="+search + "&searchOption="+ selectOption+ "&currentPage="+ (startNavi-1)+ "' class='btn_arr prev'> < </a>");
			}
			for(int i = startNavi; i<=endNavi; i++) {
				if(i == currentPage) {
		            sb.append("<a href='/market/search?searchKeyword="+search + "&searchOption="+ selectOption+"&currentPage=" + i + "' class='on'>" + i + " </a>");
		         }else {
		            sb.append("<a href='/market/search?searchKeyword="+search + "&searchOption="+ selectOption+"&currentPage="+ i +"'>  "+ i + "  </a>");
		         };
			}
			if(needNext) {
				sb.append("<a href='/market/search?searchKeyword="+search + "&searchOption="+ selectOption+"&currentPage="+ (endNavi+1) + "' class='btn_arr next'> > </a>");
			}
			return sb.toString();
		}
		
		public ArrayList<Market> selectSearchTitle(Connection conn, String search, int currentPage) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY MARKET_NO DESC)as NUM, MARKET_NO, MARKET_TITLE, NICKNAME, MARKET_DATE FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE MARKET_TITLE LIKE ?) "
					+ "WHERE NUM BETWEEN ? AND ?";
			ArrayList<Market> mList = null;
			int recordCountPerPage = 6;
			int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
			int end = currentPage*recordCountPerPage;
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+search+"%");
				pstmt.setInt(2,start);
				pstmt.setInt(3,end);
				rset = pstmt.executeQuery();
				mList = new ArrayList<Market>();
				while(rset.next()) {
					Market market = new Market();
					market.setNum(searchTotalCountTitle(conn,search) - rset.getInt("NUM") + 1);
					market.setMarketNo(rset.getInt("MARKET_NO"));
					market.setMarketTitle(rset.getString("MARKET_TITLE"));
					market.setNickName(rset.getString("NICKNAME"));
					market.setMarketDate(rset.getDate("MARKET_DATE"));
					mList.add(market);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
			return mList;
		}


		public ArrayList<Market> selectSearchWriter(Connection conn, String search, int currentPage) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY MARKET_NO DESC)as NUM, MARKET_NO, MARKET_TITLE, NICKNAME, MARKET_DATE FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE NICKNAME LIKE ?) "
					+ "WHERE NUM BETWEEN ? AND ?";
			ArrayList<Market> mList = null;
			int recordCountPerPage = 6;
			int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
			int end = currentPage*recordCountPerPage;
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+search+"%");
				pstmt.setInt(2,start);
				pstmt.setInt(3,end);
				rset = pstmt.executeQuery();
				mList = new ArrayList<Market>();
				while(rset.next()) {
					Market market = new Market();
					market.setNum(searchTotalCountWriter(conn,search) - rset.getInt("NUM") + 1);
					market.setMarketNo(rset.getInt("MARKET_NO"));
					market.setMarketTitle(rset.getString("MARKET_TITLE"));
					market.setNickName(rset.getString("NICKNAME"));
					market.setMarketDate(rset.getDate("MARKET_DATE"));
					mList.add(market);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
			return mList;
		}

		public ArrayList<Market> selectSearchContent(Connection conn, String search, int currentPage) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY MARKET_NO DESC)as NUM, MARKET_NO, MARKET_TITLE, MARKET_CONTENT, NICKNAME, MARKET_DATE FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE MARKET_CONTENT LIKE ?) "
					+ "WHERE NUM BETWEEN ? AND ?";
			ArrayList<Market> mList = null;
			int recordCountPerPage = 6;
			int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
			int end = currentPage*recordCountPerPage;
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+search+"%");
				pstmt.setInt(2,start);
				pstmt.setInt(3,end);
				rset = pstmt.executeQuery();
				mList = new ArrayList<Market>();
				while(rset.next()) {
					Market market = new Market();
					market.setNum(searchTotalCountContent(conn,search) - rset.getInt("NUM") + 1);
					market.setMarketNo(rset.getInt("MARKET_NO"));
					market.setMarketTitle(rset.getString("MARKET_TITLE"));
					market.setMarketContent(rset.getString("MARKET_CONTENT"));
					market.setNickName(rset.getString("NICKNAME"));
					market.setMarketDate(rset.getDate("MARKET_DATE"));
					mList.add(market);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
			return mList;
		}
		
		private int searchTotalCountTitle(Connection conn, String search) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = "SELECT COUNT(*) AS TOTALCOUNT FROM MARKET WHERE MARKET_TITLE LIKE ?";
			int recordTotalCount = 0;
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+search+"%");
				rset = pstmt.executeQuery();
				if(rset.next()) {
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
		private int searchTotalCountWriter(Connection conn, String search) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = "SELECT COUNT(*) AS TOTALCOUNT FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE NICKNAME LIKE ?";
			int recordTotalCount = 0;
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+search+"%");
				rset = pstmt.executeQuery();
				if(rset.next()) {
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
		private int searchTotalCountContent(Connection conn, String search) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = "SELECT COUNT(*) AS TOTALCOUNT FROM MARKET WHERE MARKET_CONTENT LIKE ?";
			int recordTotalCount = 0;
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+search+"%");
				rset = pstmt.executeQuery();
				if(rset.next()) {
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
		
		
	

		public int deleteMarketToAdmin(Connection conn, int marketNo) {
			PreparedStatement pstmt = null;
			int result = 0;
			String query = "DELETE FROM MARKET WHERE MARKET_NO =?";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, marketNo);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(pstmt);
			}
			return result;
		}

		//////////////////////////////관리자페이지 검색
		public ArrayList<Market> selectSearchContentToAdmin(Connection conn, String searchKeyword, int currentPage, String selectOption) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = null;
			if(selectOption.equals("title")) {
				query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY MARKET_NO DESC)as NUM, "
						+ "MARKET_NO, MARKET_TITLE, NICKNAME, MARKET_DATE, MARKET_CONTENT "
						+ "FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE MARKET_TITLE LIKE ?) "
						+ "WHERE NUM BETWEEN ? AND ?";
			}else if (selectOption.equals("writer")) {
				query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY MARKET_NO DESC)as NUM, "
						+ "MARKET_NO, MARKET_TITLE, NICKNAME, MARKET_DATE, MARKET_CONTENT "
						+ "FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE NICKNAME LIKE ?) "
						+ "WHERE NUM BETWEEN ? AND ?";
			}else if(selectOption.equals("subject")) {
				query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY MARKET_NO DESC)as NUM, "
						+ "MARKET_NO, MARKET_TITLE, NICKNAME, MARKET_DATE, MARKET_CONTENT "
						+ "FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE MARKET_CONTENT LIKE ?) "
						+ "WHERE NUM BETWEEN ? AND ?";
			}
			ArrayList<Market> mList = null;
			int recordCountPerPage = 6;
			int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
			int end = currentPage*recordCountPerPage;
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+searchKeyword+"%");
				pstmt.setInt(2,start);
				pstmt.setInt(3,end);
				rset = pstmt.executeQuery();
				mList = new ArrayList<Market>();
				while(rset.next()) {
					Market market = new Market();
					market.setNum(searchTotalCountToAdmin(conn,searchKeyword,selectOption) - rset.getInt("NUM") + 1);
					market.setMarketNo(rset.getInt("MARKET_NO"));
					market.setMarketTitle(rset.getString("MARKET_TITLE"));
					market.setMarketContent(rset.getString("MARKET_CONTENT"));
					market.setNickName(rset.getString("NICKNAME"));
					market.setMarketDate(rset.getDate("MARKET_DATE"));
					mList.add(market);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
			return mList;
		}
		///////////////////////////관리자페이지 검색값 내비
		public String getSearchPageNaviToAdmin(Connection conn, String searchKeyword, int currentPage, String selectOption) {
			int recordCountPerPage = 6;
			int naviCountPerPage = 5;
			int recordTotalCount = 0;
			
			// 설렉트 값에 따라서 다른 토탈 카운트를 가져오는 코드
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
				sb.append("<a href='/admin/marketsearch?searchKeyword=" + searchKeyword + "&currentPage=" + (startNavi-1) + "&selectOption=" + selectOption + "' class='btn_arr prev'> < </a>");
			}
			for (int i = startNavi; i <= endNavi; i++) {
				if(i == currentPage) {
					sb.append("<a href='/admin/marketsearch?searchKeyword=" + searchKeyword + "&currentPage=" + i + "&selectOption=" + selectOption + "' class='on'> " + i + " </a>");
				}else {
					sb.append("<a href='/admin/marketsearch?searchKeyword=" + searchKeyword + "&currentPage=" + i + "&selectOption=" + selectOption + "'> " + i + " </a>");
				}
			}
			if (needNext) {
				sb.append("<a href='/admin/marketsearch?searchKeyword=" + searchKeyword + "&currentPage=" + (endNavi+1) + "&selectOption=" + selectOption + "' class='btn_arr next'> > </a>");
			}
			return sb.toString();
			
		}

		/////////////////관리자페이지 검색 토탈타운트
		private int searchTotalCountToAdmin(Connection conn, String searchKeyword, String selectOption) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			String query = null;
			if (selectOption.equals("title")) {
				query = "SELECT COUNT(*) AS TOTALCOUNT FROM MARKET WHERE MARKET_TITLE LIKE ?";
			}else if (selectOption.equals("writer")) { 
				query = "SELECT COUNT(*) AS TOTALCOUNT FROM MARKET JOIN MEMBER USING (UNIQ_ID) WHERE NICKNAME LIKE ?";
			}else {
				query = "SELECT COUNT(*) AS TOTALCOUNT FROM MARKET WHERE MARKET_CONTENT LIKE ?";
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
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
			return recordTotalCount;
		}
		
		// 양도게시글 수정 코드
		public int modifyMarket(Connection conn, Market market) {
			PreparedStatement pstmt = null;
			int result = 0;
			String query = "UPDATE MARKET SET MARKET_TITLE = ?, MARKET_PRICE = ?, MARKET_CONTENT =? ,MARKET_FILED = ? WHERE MARKET_NO = ?"
					+ "";
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, market.getMarketTitle());
				pstmt.setString(2, market.getMarketPrice());
				pstmt.setString(3, market.getMarketContent());
				pstmt.setString(4, market.getMarketField());
				pstmt.setInt(5, market.getMarketNo());
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(pstmt);
			}
			return result;
		}
		
		// 양도게시글 파일(사진) 삭제 코드
		public int deleteMarketFile(Connection conn, int marketNo) {
			PreparedStatement pstmt = null;
			int result = 0;
			String query = "DELETE FROM MARKETPIC WHERE MARKET_NO = ?";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1,marketNo);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(pstmt);
			}
			
			return result;
		}

		// 사진 수정하는 코드 DAO
		public int updateMarketFile(Connection conn, MarketPic marketPic, int marketNo) {
			PreparedStatement pstmt = null;
			int result = 0;
			String query = "INSERT INTO MARKETPIC VALUES (?,?,?,?,?,?)";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, marketPic.getFileName());
				pstmt.setInt(2, marketNo);
				pstmt.setString(3, marketPic.getFilePath());
				pstmt.setLong(4, marketPic.getFileSize());
				pstmt.setString(5, marketPic.getFileUser());
				pstmt.setTimestamp(6, marketPic.getUploadTime());
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(pstmt);
			}
			return result;
		}

		public int deleteMarket(Connection conn, int uniqId, int marketNo) {
			PreparedStatement pstmt = null;
			int result = 0;
			String query = "DELETE FROM MARKET WHERE UNIQ_ID=? AND MARKET_NO =?";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, uniqId);
				pstmt.setInt(2, marketNo);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(pstmt);
			}
			return result;
		}


	
	
	
	
	
	
	
	
	
}