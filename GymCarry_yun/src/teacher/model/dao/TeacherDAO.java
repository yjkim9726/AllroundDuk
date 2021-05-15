package teacher.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import teacher.model.vo.Teacher;

public class TeacherDAO {

	public ArrayList<Teacher> selectTeacherList(Connection conn, int partnerCode) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Teacher>tList = null;
		String query = "SELECT * FROM TEACHER WHERE PARTNER_CODE = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partnerCode);
			rset = pstmt.executeQuery();
			tList = new ArrayList<Teacher>();
			while(rset.next()) {
				Teacher teacher = new Teacher();
				teacher.setTchrNo(rset.getInt("TCHR_NO"));
				teacher.setPartnerCode(rset.getInt("PARTNER_CODE"));
				teacher.setTchName(rset.getString("TCHR_NAME"));
				teacher.setTchrCareer(rset.getString("TCHR_CAREER"));
				teacher.setTchClass(rset.getString("TCHR_CLASS"));
				teacher.setFileName(rset.getString("FILENAME"));
				teacher.setFilePath(rset.getString("FILEPATH"));
				teacher.setFileSize(rset.getInt("FILESIZE"));
				teacher.setUploadtime(rset.getTimestamp("UPLOADTIME"));
				tList.add(teacher);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return tList;
	}
	
	public int modifyTeacher(Connection conn, Teacher teacher) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "UPDATE TEACHER SET TCHR_NAME = ?, TCHR_CLASS = ?, "
				+ "TCHR_CAREER = ? WHERE PARTNER_CODE = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, teacher.getTchName());
			pstmt.setString(2, teacher.getTchClass());
			pstmt.setString(3, teacher.getTchrCareer());
			pstmt.setInt(4, teacher.getPartnerCode());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	public int insertTeacher(Connection conn, Teacher teacher) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO TEACHER (TCHR_NO,PARTNER_CODE,TCHR_NAME,TCHR_CAREER,TCHR_CLASS) VALUES (TEACHER_SEQ.NEXTVAL,?,?,?,?)"; 
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,teacher.getPartnerCode());
			pstmt.setString(2, teacher.getTchName());
			pstmt.setString(3, teacher.getTchrCareer());
			pstmt.setString(4, teacher.getTchClass());
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
