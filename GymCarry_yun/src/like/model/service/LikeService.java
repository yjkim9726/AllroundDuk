package like.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import like.model.dao.LikeDAO;
import like.model.vo.Like;
import market.model.vo.MPageData;
import market.model.vo.Market;

public class LikeService {

	private JDBCTemplate factory;
	
	public LikeService() {
		factory = JDBCTemplate.getConnection();
	}
	
	public MPageData selectAllLikeList(int uniqId, int currentPage) {
//		ArrayList<Market> list = null;
		MPageData pd = new MPageData();
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			pd.setMarketList(new LikeDAO().selectAllLikeList(conn, uniqId, currentPage));
			pd.setPageNavi(new LikeDAO().getPageNavi(conn, currentPage));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		
		return pd;
	}
	
	public Like selectOneLike(int uniqId, int marketNo) {
		Like list = null;
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			list = new LikeDAO().selectOneLike(conn,uniqId,marketNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		
		return list;
	}
	
	public int insertLike(int marketNo, int uniqId) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			result = new LikeDAO().insertLike(conn, marketNo,uniqId);
			if(result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}

	public int deleteLikeList(String[] marketNo, int uniqId) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			for(int i = 0; i < marketNo.length; i++)
				result += new LikeDAO().deleteLike(conn, Integer.parseInt(marketNo[i]),uniqId);
			if(result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}
	
	public int deleteLike(int marketNo, int uniqId) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			result = new LikeDAO().deleteLike(conn, marketNo,uniqId);
			if(result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}


}
