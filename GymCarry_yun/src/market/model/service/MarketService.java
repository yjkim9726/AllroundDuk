package market.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import event.model.dao.EventDAO;
import event.model.vo.PageData;
import market.model.dao.MarketDAO;
import market.model.vo.MPageData;
import market.model.vo.Market;
import market.model.vo.MarketPic;


public class MarketService {
	
	private JDBCTemplate factory;
	
	public MarketService() {
		factory = JDBCTemplate.getConnection();
	}
	
	public MPageData printAllMarketList(int currentPage) {
		Connection conn = null;
		MPageData pd = new MPageData();
		
		try {
			conn = factory.createConnection();
			pd.setMarketList(new MarketDAO().printAllMarketList(conn,currentPage));
			pd.setPageNavi(new MarketDAO().getPageNavi(conn, currentPage));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}
	
	//////////////////////메인페이지에서 보이는 마켓리스트
	public ArrayList<Market> printAllMarketListToMain() {
		Connection conn = null;
		ArrayList<Market> mList = null;
		try {
			conn = factory.createConnection();
			mList = new MarketDAO().printAllMarketListToMain(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return mList;
	}
	
	/////////////////////관리자페이지에서 보여지는 마켓리스트
	public MPageData printAllMarketListToAdmin(int currentPage) {
		Connection conn = null;
		MPageData pd = new MPageData();
		
		try {
			conn = factory.createConnection();
			pd.setMarketList(new MarketDAO().printAllMarketList(conn,currentPage)); /////////기존 페이지 데이터 같이 가져오기
			pd.setPageNavi(new MarketDAO().getPageNaviToAdmin(conn, currentPage)); ////////////새롭게 정의
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}
	
	public MPageData printIdMarketList(int currentPage, int uniqId) {
		Connection conn = null;
		MPageData pd = new MPageData();
		
		try {
			conn = factory.createConnection();
			pd.setMarketList(new MarketDAO().printIdMarketList(conn,currentPage,uniqId));
			pd.setPageNavi(new MarketDAO().getIdPageNavi(conn, currentPage,uniqId));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}
	
	public Market printOneMarket(int marketNo) {
		Connection conn = null;
		Market market =null;
		
		try {
			conn = factory.createConnection();
			market = new MarketDAO().printOneMarket(conn,marketNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		
		return market;
	}

	public ArrayList<MarketPic> printListPic(int marketNo) {
		Connection conn = null;
		ArrayList<MarketPic> mPic = null;
		
		try {
			conn = factory.createConnection();
			mPic = new MarketDAO().printListPic(conn,marketNo);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return mPic;
	}
	
	public int insertMarket(Market market) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new MarketDAO().insertMarket(conn, market);
			if(result > 0 ) {
				// 최종 커밋은 사진까지 업로드 후에 해준다.
			} else {
				JDBCTemplate.rollback(conn);
			}
		 System.out.println("결과값 :" + result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}


	public int insertMarketFile(MarketPic marketPic, int uniqId) {
		Connection conn = null;
		int result =0;
		
		try {
			conn = factory.createConnection();
			result = new MarketDAO().printOneMarketFile(conn, marketPic, uniqId);
			if(result > 0 ) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
			System.out.println("결과값2 :" + result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}


	public MPageData printSearcTitle(String search, String selectOption ,int currentPage) {
		Connection conn = null;
		MPageData pd = new MPageData();
		
		try {
			conn = factory.createConnection();
			pd.setMarketList(new MarketDAO().selectSearchTitle(conn,search,currentPage));
			pd.setPageNavi(new MarketDAO().getSearchPageNavi(conn,search,selectOption,currentPage));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}

	public MPageData printSearcWriter(String search, String selectOption, int currentPage) {
		Connection conn = null;
		MPageData pd = new MPageData();
		
		try {
			conn = factory.createConnection();
			pd.setMarketList(new MarketDAO().selectSearchWriter(conn, search, currentPage));
			pd.setPageNavi(new MarketDAO().getSearchPageNavi(conn,search,selectOption,currentPage));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}

	public MPageData printSearcContent(String search, String selectOption, int currentPage) {
		Connection conn = null;
		MPageData pd = new MPageData();
		
		try {
			conn = factory.createConnection();
			pd.setMarketList(new MarketDAO().selectSearchContent(conn,search,currentPage));
			pd.setPageNavi(new MarketDAO().getSearchPageNavi(conn,search,selectOption,currentPage));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}

	/////////삭제
	public int deleteMarketToAdmin( int marketNo) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			result = new MarketDAO().deleteMarketToAdmin(conn, marketNo);
			if(result > 0) {
				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return result;
	}

	////////////////////관리자페이지 검색
	public MPageData printMarketSearchToAdmin(String searchKeyword, int currentPage, String selectOption) {
		Connection conn = null;
		MPageData pd = new MPageData();
		try {
			conn = factory.createConnection();
			pd.setMarketList(new MarketDAO().selectSearchContentToAdmin(conn, searchKeyword, currentPage, selectOption));
			pd.setPageNavi(new MarketDAO().getSearchPageNaviToAdmin(conn, searchKeyword, currentPage, selectOption));
			System.out.println(selectOption);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}
	
	public int modifyMarket(Market market) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new MarketDAO().modifyMarket(conn, market);
			if(result > 0) {
				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return result;
	}


	// 양도글 수정 페이지에서 사진 삭제 후 다시 재업로드
			public int deleteMarketFile(int marketNo) {
				Connection conn = null;
				int result = 0;
				
				try {
					conn = factory.createConnection();
					result = new MarketDAO().deleteMarketFile(conn,marketNo);
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

			// 사진 수정하는 코드 
			public int updateMarketFile(MarketPic marketPic, int marketNo) {
				Connection conn = null;
				int result =0;
				
				try {
					conn = factory.createConnection();
					result = new MarketDAO().updateMarketFile(conn, marketPic, marketNo);
					if(result > 0 ) {
						JDBCTemplate.commit(conn);
					} else {
						JDBCTemplate.rollback(conn);
					}
					System.out.println("결과값2 :" + result);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					JDBCTemplate.close(conn);
				}
				return result;
			}

			public int deleteMarket(int uniqId, int marketNo) {
				int result = 0;
				Connection conn = null;
				
				try {
					conn = factory.createConnection();
					result = new MarketDAO().deleteMarket(conn, uniqId, marketNo);
					if(result > 0) {
						JDBCTemplate.commit(conn);
					}else {
						JDBCTemplate.rollback(conn);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
				return result;
			}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}