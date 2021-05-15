package message.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import message.model.dao.MessageDAO;
import message.model.vo.Message;

public class MessageService {
	
	private JDBCTemplate factory;
	
	public MessageService() {
		factory = JDBCTemplate.getConnection();
	}

	public ArrayList<Message> printAllList(int uniq_id) {
		Connection conn = null;
		ArrayList<Message> mList = null;
		
		try {
			conn = factory.createConnection();
			mList = new MessageDAO().printAllList(conn, uniq_id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return mList;
	}
	public ArrayList<Message> printSentList(int uniq_id) {
		Connection conn = null;
		ArrayList<Message> mList = null;
		
		try {
			conn = factory.createConnection();
			mList = new MessageDAO().printSentList(conn, uniq_id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return mList;
	}

	public int writeSenderMessage(Message message) {
		Connection conn = null;
		int result = 0;
		try {
			conn = factory.createConnection();
			result = new MessageDAO().writeSenderMessage(conn, message);
			
			System.out.println(result);
			if(result > 0) {
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
	
	public int writeReceiverMessage(Message message) {
		Connection conn = null;
		int result = 0;
		try {
			conn = factory.createConnection();
			result = new MessageDAO().writeReceiverMessage(conn, message);
			System.out.println(result);
			if(result > 0) {
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
	

	public int selectReceiverId(String nickName) {
		Connection conn = null;
		int uniqId = 0;
		
		try {
			conn = factory.createConnection();
			uniqId = new MessageDAO().selectReciverId(conn, nickName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uniqId;
	}

	public Message printOneMessage(int messageNo) {
		Connection conn = null;
		Message message = null;
		
		try {
			conn = factory.createConnection();
			message = new MessageDAO().printOneMessage(conn, messageNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return message;
	}
	
	public int addCount(Message message) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new MessageDAO().addCount(conn, message);
			if(result > 0) {
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

	public Message printSentMessage(int messageNo) {
		Connection conn = null;
		Message message = null;
		
		try {
			conn = factory.createConnection();
			message = new MessageDAO().printSentMessage(conn, messageNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return message;
	}

	public int deleteMessage(int messageNo) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new MessageDAO().deleteMessage(conn, messageNo);
			if(result > 0) {
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

	public int replySenderMessage(Message message) {
		Connection conn = null;
		int result = 0;
		try {
			conn = factory.createConnection();
			result = new MessageDAO().replySenderMessage(conn, message);
			
			System.out.println(result);
			if(result > 0) {
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

	public int replyReceiverMessage(Message message) {
		Connection conn = null;
		int result = 0;
		try {
			conn = factory.createConnection();
			result = new MessageDAO().replyReceiverMessage(conn, message);
			
			System.out.println(result);
			if(result > 0) {
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

}
