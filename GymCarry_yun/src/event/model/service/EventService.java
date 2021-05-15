package event.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import event.model.dao.EventDAO;
import event.model.vo.Event;
import event.model.vo.EventPic;
import event.model.vo.PageData;

public class EventService {
	
	private JDBCTemplate factory;
	
	public EventService() {
		factory = JDBCTemplate.getConnection();
	}
	//근딜 목록 조회
	public PageData selectEventList(int currentPage) {
		Connection conn = null;
		PageData pd = new PageData();
		
		try {
			conn = factory.createConnection();
			pd.setEventList(new EventDAO().selectEventList(conn, currentPage));
			pd.setPageNavi(new EventDAO().getPageNavi(conn, currentPage));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}
	
	//관리자 - 근딜 목록 조회
		public PageData selectEventListToAdmin(int currentPage) {
			Connection conn = null;
			PageData pd = new PageData();
			
			try {
				conn = factory.createConnection();
				pd.setEventList(new EventDAO().selectEventListToAdmin(conn, currentPage));
				pd.setPageNavi(new EventDAO().getPageNaviToAdmin(conn, currentPage));
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(conn);
			}
			return pd;
		}
	
	/////////////////메인페이지 이벤트목록
	public ArrayList<Event> selectEventListToMain() {
		Connection conn = null;
		ArrayList<Event> eList = null;
		try {
			conn = factory.createConnection();
			eList = new EventDAO().selectEventListToMain(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return eList;
	}

	public int registerEvent(Event event) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			result = new EventDAO().insertEvent(conn, event);
			conn.setAutoCommit(false);
			if (result>0) {
//				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
			System.out.println("결과값1 :" + result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}

	////////////////////////////////////////////////////////사진등록
	public int registerEventFile(EventPic eventPic) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new EventDAO().inserEventFileInfo(conn, eventPic);
			if (result > 0) {
				JDBCTemplate.commit(conn);
			}else {
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
	
	public Event selectOneEvent(int eventNo) {
		Connection conn = null;
		Event event = null;
		
		try {
			conn = factory.createConnection();
			event = new EventDAO().selectOneEvent(conn, eventNo);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return event;
	}

	/////////////////////////////////////////////////////사진 보여주기
	public ArrayList<EventPic> selectOneEventFile(int eventNo) {
		Connection conn = null;
		ArrayList<EventPic> eventPic = null;
		
		try {
			conn = factory.createConnection();
			eventPic = new EventDAO().selectOneEventFile(conn, eventNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(conn);
		}
		return eventPic;
	}

	//////////////////////////////////////관리자 검색목록
	public PageData printSearchList(String search, int currentPage, String cate) {
		Connection conn = null;
		PageData pd = new PageData();
		try {
			conn = factory.createConnection();
			pd.setEventList(new EventDAO().selectEventSearchList(conn, search, currentPage, cate));
			pd.setPageNavi(new EventDAO().getSearchPageNavi(conn, search, currentPage, cate));
			System.out.println(cate);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}
	
	////////////////////관리자 근딜 승인/////////////////////
	public int confirmOneEvent(Event event) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new EventDAO().updateEventConfirm(conn, event);
			conn.setAutoCommit(false);
			if (result>0) {
				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}
	
	//////////////////관리자 이벤트 삭제
	public int deleteEvent(int eventNo) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new EventDAO().deleteEvent(conn, eventNo);
			conn.setAutoCommit(false); 
			if (result>0) {
				JDBCTemplate.commit(conn);
			}else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	
	}

}
