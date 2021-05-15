package member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.JDBCTemplate;
import member.model.vo.Member;

public class MemberDAO {

	public MemberDAO() {
	}

	// 싱글턴 적용하기
	// 1. 자기자신의 참조변수를 static으로 소유
	// 2. 생성자를 private으로 감춤
	// 3. 1에 대한 getter를 만들되, 1이 null이면 객체를 할당

//로그인
	public Member selectOneUser(Connection conn, String userId, String userPwd) {
		Statement stmt = null;
		ResultSet rset = null;
		Member Member = null;
		String query = "SELECT * FROM MEMBER WHERE USER_ID = '" + userId + "' AND USER_PWD = '" + userPwd + "'";

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			if (rset.next()) {
				Member = new Member();
				Member.setUniqId(rset.getInt("UNIQ_ID"));
				Member.setUserId(rset.getString("USER_ID"));
				Member.setUserPwd(rset.getString("USER_PWD"));
				Member.setNickname(rset.getString("NICKNAME"));
				Member.setName(rset.getString("NAME"));
				Member.setGender(rset.getString("GENDER"));
				Member.setEmail(rset.getString("EMAIL"));
				Member.setPhone(rset.getString("PHONE"));
				Member.setAddressCity(rset.getString("ADDRESS_CITY"));
				Member.setAddressGu(rset.getString("ADDRESS_GU"));
				Member.setAdminYn(rset.getString("ADMIN_YN"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		return Member;
	}

// 회원가입
	public int insertMember(Connection conn, Member Member) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO MEMBER VALUES(MEMBER_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,'N')";
		
		try {
			pstmt = conn.prepareStatement(query);
			// uniqId, userId, userPwd, nickname, name, gender, email, phone, addressCity, addressGu, adminYn
			pstmt.setString(1, Member.getUserId());
			pstmt.setString(2, Member.getUserPwd());
			pstmt.setString(3, Member.getNickname());
			pstmt.setString(4, Member.getName());
			pstmt.setString(5, Member.getGender());
			pstmt.setString(6, Member.getEmail());
			pstmt.setString(7, Member.getPhone());
			pstmt.setString(8, Member.getAddressCity());
			pstmt.setString(9, Member.getAddressGu());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

// 아이디 찾기
	public Member selectOneById(Connection conn, String userName, String userPhone) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member Member = null;
		String query = "SELECT * FROM MEMBER WHERE NAME = ? AND PHONE = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setString(2, userPhone);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				Member = new Member();
				Member.setUniqId(rset.getInt("UNIQ_ID"));
				Member.setUserId(rset.getString("USER_ID"));
				Member.setUserPwd(rset.getString("USER_PWD"));
				Member.setNickname(rset.getString("NICKNAME"));
				Member.setName(rset.getString("NAME"));
				Member.setGender(rset.getString("GENDER"));
				Member.setEmail(rset.getString("EMAIL"));
				Member.setPhone(rset.getString("PHONE"));
				Member.setAddressCity(rset.getString("ADDRESS_CITY"));
				Member.setAddressGu(rset.getString("ADDRESS_GU"));
				Member.setAdminYn(rset.getString("ADMIN_YN"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		System.out.println("결과값 : " + Member);
		return Member;
	}
	
	// 회원정보 수정
		public int updateMember(Connection conn, Member member) {
			// TODO Auto-generated method stub
			PreparedStatement pstmt = null;
			int result = 0;
			String query = "UPDATE MEMBER SET USER_PWD=?, NICKNAME=?,NAME=?,EMAIL=?,PHONE=?,ADDRESS_CITY=?,ADDRESS_GU=? WHERE USER_ID=?";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, member.getUserPwd());
				pstmt.setString(2, member.getNickname());
				pstmt.setString(3, member.getName());
				pstmt.setString(4, member.getEmail());
				pstmt.setString(5, member.getPhone());
				pstmt.setString(6, member.getAddressCity());
				pstmt.setString(7, member.getAddressGu());
				pstmt.setString(8, member.getUserId());
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(pstmt);
			}
			return result;
		}


	
// 회원정보 삭제
	public int deleteMember(Connection conn, String userId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "DELETE FROM MEMBER WHERE USER_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

// 회원 리스트 조회
	public ArrayList<Member> selectMemberList(Connection conn) {
		// TODO Auto-generated method stub
		
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM MEMBER";
		ArrayList<Member> list = null;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			while (rset.next()) {
				Member Member = new Member();
				
				Member.setUserId(rset.getString("USER_ID"));
				Member.setUserPwd(rset.getString("USER_PWD"));
				Member.setNickname(rset.getString("NICKNAME"));
				Member.setName(rset.getString("NAME"));
				Member.setGender(rset.getString("GENDER"));
				Member.setEmail(rset.getString("EMAIL"));
				Member.setPhone(rset.getString("PHONE"));
				Member.setAddressCity(rset.getString("ADDRESS_CITY"));
				Member.setAddressGu(rset.getString("ADDRESS_GU"));
				
				list.add(Member);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		
		return list;
	}

// 회원 아이디 조회
	public Member selectOneUser(Connection conn, String userId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member Member = null;
		String query = "SELECT * FROM MEMBER WHERE USER_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, query);
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				Member = new Member();
				Member.setUniqId(rset.getInt("UNIQ_ID"));
				Member.setUserId(rset.getString("USER_ID"));
				Member.setUserPwd(rset.getString("USER_PWD"));
				Member.setNickname(rset.getString("NICKNAME"));
				Member.setName(rset.getString("NAME"));
				Member.setGender(rset.getString("GENDER"));
				Member.setEmail(rset.getString("EMAIL"));
				Member.setPhone(rset.getString("PHONE"));
				Member.setAddressCity(rset.getString("ADDRESS_CITY"));
				Member.setAddressGu(rset.getString("ADDRESS_GU"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return Member;
	}

//멤버 목록 페이지
	public ArrayList<Member> printAllMemberList(Connection conn, int currentPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Member> mList = null;
		String query="SELECT * FROM(SELECT ROW_NUMBER() OVER (ORDER BY UNIQ_ID DESC) AS NUM,"
				+ " UNIQ_ID, USER_ID, USER_PWD, NICKNAME, NAME, GENDER, EMAIL, PHONE, ADDRESS_CITY, ADDRESS_GU, ADMIN_YN "
				+ "FROM MEMBER) WHERE NUM BETWEEN ? AND ?";
		int recordCountPerPage = 6;
		int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage*recordCountPerPage;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			Member Member = null;
			rset = pstmt.executeQuery();
			mList = new ArrayList<Member>();
			while(rset.next()) {
				Member = new Member();
				Member.setUniqId(rset.getInt("UNIQ_ID"));
				Member.setUserId(rset.getString("USER_ID"));
				Member.setUserPwd(rset.getString("USER_PWD"));
				Member.setNickname(rset.getString("NICKNAME"));
				Member.setName(rset.getString("NAME"));
				Member.setGender(rset.getString("GENDER"));
				Member.setEmail(rset.getString("EMAIL"));
				Member.setPhone(rset.getString("PHONE"));
				Member.setAddressCity(rset.getString("ADDRESS_CITY"));
				Member.setAddressGu(rset.getString("ADDRESS_GU"));
				Member.setAdminYn(rset.getString("ADMIN_YN"));
				mList.add(Member);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		System.out.println("결과 : " + mList);
		return mList;
		
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
		// 페이지 수 만큼 자르기
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
			sb.append("<a href='/Member/list?currentPage="+ (startNavi-1)+ "'> < </a>");
		}
		for(int i = startNavi; i<=endNavi; i++) {
			sb.append("<a href='/Member/list?currentPage="+ i +"'> "+ i + " </a>");
		}
		if(needNext) {
			sb.append("<a href='/Member/list?currentPage="+ (endNavi+1) + "'> > </a>");
		}
		return sb.toString();
	}

	private int totalCount(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT COUNT(*) AS TOTALCOUNT FROM MEMBER";
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

	public int resetPwd(Connection conn, String id, String pwd) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "UPDATE MEMBER SET USER_PWD = ? WHERE USER_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pwd);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		System.out.println("결과는 : " + result);
		return result;
	}

//관리자페이지에서 삭제
	public int deleteMemberToAdmin(Connection conn, int MemberNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "DELETE FROM MEMBER WHERE UNIQ_ID =?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, MemberNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		System.out.println("삭제가 되려고 하나요?" + result);
		return result;
	}

// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ 관리자 페이지 검색 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	public ArrayList<Member> selectSearchContentToAdmin(Connection conn, String searchKeyword, int currentPage,
			String selectOption) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		if(selectOption.equals("nickname")) {
			query = "SELECT * FROM(SELECT ROW_NUMBER() "
					+ "OVER (ORDER BY UNIQ_ID DESC) AS NUM, UNIQ_ID, USER_ID, USER_PWD, NICKNAME, "
					+ "NAME, GENDER, EMAIL, PHONE, ADDRESS_CITY, ADDRESS_GU, ADMIN_YN "
					+ "FROM MEMBER WHERE NICKNAME LIKE ?) "
					+ "WHERE NUM BETWEEN ? AND ?";
		}else if(selectOption.equals("id")) {
			query = "SELECT * FROM(SELECT ROW_NUMBER() "
					+ "OVER (ORDER BY UNIQ_ID DESC) AS NUM, UNIQ_ID, USER_ID, USER_PWD, NICKNAME, "
					+ "NAME, GENDER, EMAIL, PHONE, ADDRESS_CITY, ADDRESS_GU, ADMIN_YN "
					+ "FROM MEMBER WHERE USER_ID LIKE ?) "
					+ "WHERE NUM BETWEEN ? AND ?";
		}
		ArrayList<Member> mList = null;
		int recordCountPerPage = 6;
		int start = currentPage*recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage*recordCountPerPage;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+searchKeyword+"%");
			pstmt.setInt(2,start);
			pstmt.setInt(3,end);
			rset = pstmt.executeQuery();
			mList = new ArrayList<Member>();
			while(rset.next()) {
				Member member = new Member();
				member.setNum(searchTotalCountToAdmin(conn,searchKeyword,selectOption) - rset.getInt("NUM") + 1);
				member.setUniqId(rset.getInt("UNIQ_ID"));
				member.setUserId(rset.getString("USER_ID"));
				member.setUserPwd(rset.getString("USER_PWD"));
				member.setNickname(rset.getString("NICKNAME"));
				member.setName(rset.getString("NAME"));
				member.setGender(rset.getString("GENDER"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddressCity(rset.getString("ADDRESS_CITY"));
				member.setAddressGu(rset.getString("ADDRESS_GU"));
				member.setAdminYn(rset.getString("ADMIN_YN"));
				mList.add(member);
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

//관리자페이지 검색 시 토탈 카운트하기
	private int searchTotalCountToAdmin(Connection conn, String searchKeyword, String selectOption) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = null;
		if (selectOption.equals("nickname")) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM MEMBER WHERE NICKNAME LIKE ?";
		}else {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM MEMBER WHERE USER_ID LIKE ?";
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

//관리자페이지 검색값 네비게이션
	public String getSearchPageNaviToAdmin(Connection conn, String searchKeyword, int currentPage,
			String selectOption) {
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
			sb.append("<a href='/admin/membersearch?searchKeyword=" + searchKeyword + "&currentPage=" + (startNavi-1) + "&selectOption=" + selectOption + "' class='btn_arr prev'> < </a>");
		}
		for (int i = startNavi; i <= endNavi; i++) {
			if(i == currentPage) {
				sb.append("<a href='/admin/membersearch?searchKeyword=" + searchKeyword + "&currentPage=" + i + "&selectOption=" + selectOption + "' class='on'> " + i + " </a>");
			}else {
				sb.append("<a href='/admin/membersearch?searchKeyword=" + searchKeyword + "&currentPage=" + i + "&selectOption=" + selectOption + "'> " + i + " </a>");
			}
		}
		if (needNext) {
			sb.append("<a href='/admin/membersearch?searchKeyword=" + searchKeyword + "&currentPage=" + (endNavi+1) + "&selectOption=" + selectOption + "' class='btn_arr next'> > </a>");
		}
		return sb.toString();
	}
	
	public Member selectOneById(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM MEMBER WHERE USER_ID=?";
		Member member = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				member = new Member();
				member.setUniqId(rset.getInt("UNIQ_ID"));
				member.setUserId(rset.getString("USER_ID"));
				member.setUserPwd(rset.getString("USER_PWD"));
				member.setNickname(rset.getString("NICKNAME"));
				member.setName(rset.getString("NAME"));
				member.setGender(rset.getString("GENDER"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddressCity(rset.getString("ADDRESS_CITY"));
				member.setAddressGu(rset.getString("ADDRESS_GU"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt); // preparedStatement는 statement 를 상속해서 만든 것이라서 다형성을 이용해서 close
		}
		return member;
	}

}
