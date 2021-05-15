package like.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.JDBCTemplate;
import like.model.vo.Like;
import market.model.vo.Market;

public class LikeDAO {

	public ArrayList<Market> selectAllLikeList(Connection conn, int uniqId, int currentPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Market> list = null; 
		String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY LIKE_NO DESC) AS NUM, " +
				"MARKET_NO, MARKET_TITLE, MARKET_PRICE ,MARKET_FILED, NICKNAME, MARKET_DATE " +
				"FROM MARKET MA JOIN MEMBER M USING (UNIQ_ID) JOIN TB_LIKE L USING (MARKET_NO) " + 
				"WHERE L.UNIQ_ID = ?) WHERE NUM BETWEEN ? AND ?";
		int recordCountPerPage = 10;
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uniqId);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rset = pstmt.executeQuery();
			list = new ArrayList<Market>();
			while(rset.next()) {
				Market market = new Market();
				market.setNum(totalCount(conn) - rset.getInt("NUM") - 2);
				market.setMarketNo(rset.getInt("MARKET_NO"));
				
				market.setMarketTitle(rset.getString("MARKET_TITLE"));
				market.setMarketPrice(rset.getString("MARKET_PRICE"));
				market.setMarketField(rset.getString("MARKET_FILED"));
				market.setNickName(rset.getString("NICKNAME"));
				market.setMarketDate(rset.getDate("MARKET_DATE"));
				list.add(market);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}
	public String getPageNavi(Connection conn, int currentPage) {
		int recordTotalCount = totalCount(conn);
		int recordCountPerPage = 10;
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
		
		int naviCountPerPage = 10;
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
			sb.append("<a href='/Like/list?currentPage="+ (startNavi-1)+ "' class='btn_arr prev'> < </a>");
		}
		for(int i = startNavi; i<=endNavi; i++) {
			if(i == currentPage) {
				sb.append("<a href='/Like/list?currentPage=" + i + "' class='on'>" + i + " </a>");
			}else {
				sb.append("<a href='/Like/list?currentPage="+ i +"'>  "+ i + "  </a>");
			};
		}
		if(needNext) {
			sb.append("<a href='/market/list?currentPage="+ (endNavi+1) + "' class='btn_arr next'> > </a>");
		}
		return sb.toString();
	}
	
	public int totalCount(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT COUNT(*) AS TOTALCOUNT FROM TB_LIKE";
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

	public Like selectOneLike(Connection conn, int uniqId, int marketNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Like like = null;
		String query = "SELECT * FROM TB_LIKE WHERE UNIQ_ID =? AND MARKET_NO =?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uniqId);
			pstmt.setInt(2, marketNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				like = new Like();
				like.setUniqId(rset.getInt("UNIQ_ID"));
				like.setMarketNo(rset.getInt("MARKET_NO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return like;
	}
	
	public int insertLike(Connection conn, int marketNo, int uniqId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO TB_LIKE VALUES (SEQ_LIKENO.NEXTVAL,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uniqId);
			pstmt.setInt(2, marketNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deleteLike(Connection conn, int marketNo, int uniqId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "DELETE FROM TB_LIKE WHERE UNIQ_ID=? AND MARKET_NO =?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uniqId);
			pstmt.setInt(2, marketNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}


	
}
