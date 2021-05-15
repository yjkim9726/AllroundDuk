package teacher.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.compiler.TldCache;

import common.JDBCTemplate;
import teacher.model.dao.TeacherDAO;
import teacher.model.vo.Teacher;

public class TeacherService {
	
	private JDBCTemplate factory;
	
	public TeacherService() {
		factory = JDBCTemplate.getConnection();
	}

	public ArrayList<Teacher> printTeacherList(int partnerCode) {
		Connection conn = null;
		ArrayList<Teacher> tList = null;
		
		try {
			conn = factory.createConnection();
			tList = new TeacherDAO().selectTeacherList(conn, partnerCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(conn);
		}

		return tList;
	}
	
	public int modifyTeacher(Teacher teacher) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new TeacherDAO().modifyTeacher(conn, teacher);
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
	

	public int insertTeacher(Teacher teacher) {
		Connection conn = null;
		int result = 0;
		try {
			conn =factory.createConnection();
			result = new TeacherDAO().insertTeacher(conn,teacher);
			if(result > 0) {
				JDBCTemplate.commit(conn);
			} else {
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
