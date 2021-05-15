package message.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import message.model.vo.Message;

public class MessageDAO {

	public ArrayList<Message> printAllList(Connection conn, int uniq_id) {
		// TODO Auto-generated method stub=
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Message> mList = null;
		
		String query = "SELECT MESSAGE_NO, NICKNAME, RECEIVER_ID, SENDER_ID, MESSAGE_CONTENT, " +  
				"TO_CHAR(MESSAGE_DATE, 'YYYY-MM-DD hh:mm:ss') as MDATE, READ_COUNT " + 
				"FROM MESSAGE JOIN MEMBER ON SENDER_ID = UNIQ_ID " + 
				"WHERE RECEIVER_ID = ? AND MOD(MESSAGE_NO, 2) = 1" +
				"ORDER BY MDATE DESC";
		
//		String query = "SELECT MESSAGE_NO, RECEIVER_ID, SENDER_ID, MESSAGE_CONTENT, "
//				+ "TO_CHAR(MESSAGE_DATE, 'YYYY-MM-DD') as MDATE, READ_COUNT FROM MESSAGE "
//				+ "WHERE RECEIVER_ID = ? ORDER BY MDATE DESC";
		
//		String flag = "1";  // 1은 받은 편지함 2는 보낸 편지함 
//		if(flag.equals("1")) {
//			query += "WHERE RECEIVER_ID = ? ";
//		} else {
//			query += "WHERE SENDER_ID = ? ";
//		}
//		query += "order by MESSAGE_DATE desc";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uniq_id);
			rset = pstmt.executeQuery();
			mList = new ArrayList<Message>();
			while(rset.next()) {
				Message message = new Message();
				message.setMessageNo(rset.getInt("MESSAGE_NO"));
				message.setNickName(rset.getString("NICKNAME"));
				message.setReceiverId(rset.getInt("RECEIVER_ID"));
				message.setSenderId(rset.getInt("SENDER_ID"));
				message.setMessageContent(rset.getString("MESSAGE_CONTENT"));
				message.setMessageDate(rset.getString("MDATE"));
				message.setReadCount(rset.getInt("READ_COUNT"));
				mList.add(message);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return mList;
	}
	

	public ArrayList<Message> printSentList(Connection conn, int uniq_id) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Message> mList = null;
		
		String query = "SELECT MESSAGE_NO, NICKNAME, RECEIVER_ID, SENDER_ID, MESSAGE_CONTENT, " + 
				"TO_CHAR(MESSAGE_DATE, 'YYYY-MM-DD hh:mm:ss') as MDATE, READ_COUNT " + 
				"FROM MESSAGE JOIN MEMBER ON RECEIVER_ID = UNIQ_ID " +
				"WHERE SENDER_ID = ? AND MOD(MESSAGE_NO, 2) = 0 " +
				"ORDER BY MDATE DESC";
//		String query = "SELECT MESSAGE_NO, RECEIVER_ID, SENDER_ID, MESSAGE_CONTENT, "
//				+ "TO_CHAR(MESSAGE_DATE, 'YYYY-MM-DD') as MDATE, READ_COUNT FROM MESSAGE "
//				+ "WHERE SENDER_ID = ? ORDER BY MDATE DESC";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uniq_id);
			rset = pstmt.executeQuery();
			mList = new ArrayList<Message>();
			while(rset.next()) {
				Message message = new Message();
				message.setMessageNo(rset.getInt("MESSAGE_NO"));
				message.setNickName(rset.getString("NICKNAME"));
				message.setReceiverId(rset.getInt("RECEIVER_ID"));
				message.setSenderId(rset.getInt("SENDER_ID"));
				message.setMessageContent(rset.getString("MESSAGE_CONTENT"));
				message.setMessageDate(rset.getString("MDATE"));
				message.setReadCount(rset.getInt("READ_COUNT"));
				mList.add(message);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return mList;
	}
	
	public int writeSenderMessage(Connection conn, Message message) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO MESSAGE VALUES(MESSAGE_SEQ.NEXTVAL,?,?,?,SYSDATE,0)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, message.getReceiverId());	
			pstmt.setInt(2, message.getSenderId());
			pstmt.setString(3, message.getMessageContent());
			result = pstmt.executeUpdate();
			System.out.println(message.getReceiverId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	public int writeReceiverMessage(Connection conn, Message message) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO MESSAGE VALUES(MESSAGE_SEQ.NEXTVAL,?,?,?,SYSDATE,0)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, message.getReceiverId());	
			pstmt.setInt(2, message.getSenderId());
			pstmt.setString(3, message.getMessageContent());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int replySenderMessage(Connection conn, Message message) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO MESSAGE VALUES(MESSAGE_SEQ.NEXTVAL,?,?,?,SYSDATE,0)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, message.getReceiverId());	
			pstmt.setInt(2, message.getSenderId());
			pstmt.setString(3, message.getMessageContent());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	
	public int replyReceiverMessage(Connection conn, Message message) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO MESSAGE VALUES(MESSAGE_SEQ.NEXTVAL,?,?,?,SYSDATE,0)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, message.getReceiverId());	
			pstmt.setInt(2, message.getSenderId());
			pstmt.setString(3, message.getMessageContent());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	public int selectReciverId(Connection conn, String nickName) {
		PreparedStatement pstmt = null;
		int uniqId = 0;
		String query = "SELECT UNIQ_ID FROM MEMBER WHERE NICKNAME = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, nickName);
			uniqId = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return uniqId;
	}

	public Message printOneMessage(Connection conn, int messageNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Message message = null;
		String query = "SELECT MESSAGE_NO, NICKNAME, SENDER_ID, MESSAGE_CONTENT, TO_CHAR(MESSAGE_DATE, 'YYYY-MM-DD') as MDATE "
				+ "FROM MESSAGE LEFT OUTER JOIN MEMBER ON (SENDER_ID = UNIQ_ID) "
				+ "WHERE MESSAGE_NO = ?";
		
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, messageNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				message = new Message();
				message.setMessageNo(rset.getInt("MESSAGE_NO"));
				message.setNickName(rset.getString("NICKNAME"));
				message.setSenderId(rset.getInt("SENDER_ID"));
				message.setMessageContent(rset.getString("MESSAGE_CONTENT"));
				message.setMessageDate(rset.getString("MDATE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	public int addCount(Connection conn, Message message) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "UPDATE MESSAGE SET READ_COUNT = READ_COUNT+1 WHERE MESSAGE_NO = ?+1";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, message.getMessageNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public Message printSentMessage(Connection conn, int messageNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Message message = null;
		String query = "SELECT MESSAGE_NO, NICKNAME, MESSAGE_CONTENT, TO_CHAR(MESSAGE_DATE, 'YYYY-MM-DD') as MDATE "
				+ "FROM MESSAGE LEFT OUTER JOIN MEMBER ON (RECEIVER_ID = UNIQ_ID) "
				+ "WHERE MESSAGE_NO = ?";
		
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, messageNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				message = new Message();
				message.setMessageNo(rset.getInt("MESSAGE_NO"));
				message.setNickName(rset.getString("NICKNAME"));
				message.setMessageContent(rset.getString("MESSAGE_CONTENT"));
				message.setMessageDate(rset.getString("MDATE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}


	public int deleteMessage(Connection conn, int messageNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "DELETE FROM MESSAGE WHERE MESSAGE_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, messageNo);
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
