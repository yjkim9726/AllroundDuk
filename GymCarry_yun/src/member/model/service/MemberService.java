package member.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import market.model.dao.MarketDAO;
import market.model.vo.MPageData;
import member.model.dao.MemberDAO;
import member.model.vo.Member;
import member.model.vo.MemberPageData;

public class MemberService {

	private JDBCTemplate factory;

	public MemberService() {
		factory = JDBCTemplate.getConnection();
	}

//로그인
	public Member selectOneUser(String userId, String userPwd) {
		Member member = null;
		Connection conn = null;

		try {
			conn = factory.createConnection();
			member = new MemberDAO().selectOneUser(conn, userId, userPwd);
			System.out.println(member.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}

		return member;
	}

//회원가입
	public int registerMember(Member member) {
		// TODO Auto-generated method stub
		int result = 0;
		Connection conn = null;

		try {
			conn = factory.createConnection();
			result = new MemberDAO().insertMember(conn, member);
			System.out.println(member.toString());

			if (result > 0) {
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

//회원정보 수정
	public int modifyMember(Member member) {
		// TODO Auto-generated method stub
		Connection conn = null;
		int result = 0;

		try {
			conn = factory.createConnection();
			result = new MemberDAO().updateMember(conn, member);
			System.out.println("결과값 : " + result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}

//회원정보 삭제
	public int deleteMember(String userId) {
		// TODO Auto-generated method stub
		Connection conn = null;
		int result = 0;

		try {
			conn = factory.createConnection();
			result = new MemberDAO().deleteMember(conn, userId);
			if (result > 0) {
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

//회원정보 리스트 조회
	public ArrayList<Member> selectMemberList() {
		// TODO Auto-generated method stub
		ArrayList<Member> list = null;
		Connection conn = null;

		try {
			conn = factory.createConnection();
			list = new MemberDAO().selectMemberList(conn); // 리스트는 전달값 필요X
			System.out.println(list.get(0).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}

		return list;
	}

//아이디 찾기
	public Member selectOneById(String userName, String userPhone) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Member member = null;

		try {
			conn = factory.createConnection();
			member = new MemberDAO().selectOneById(conn, userName, userPhone);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}

		return member;
	}

//일반페이지에서 페이지 넘기기
	public MemberPageData printAllMemberList(int currentPage) {
		Connection conn = null;
		MemberPageData pd = new MemberPageData();

		try {
			conn = factory.createConnection();
			pd.setMemberList(new MemberDAO().printAllMemberList(conn, currentPage));
			pd.setPageNavi(new MarketDAO().getPageNavi(conn, currentPage));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}

// 비밀번호 초기화
	public int resetPwd(String id, String pwd) {
		Connection conn = null;
		int result = 0;

		try {
			conn = factory.createConnection();
			result = new MemberDAO().resetPwd(conn, id, pwd);

			if (result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.commit(conn);
		}
		return result;
	}
	
// 관리자페이지에서 페이지 넘기기
		public MemberPageData printAllMemberListToAdmin(int currentPage) {
			Connection conn = null;
			MemberPageData pd = new MemberPageData();

			try {
				conn = factory.createConnection();
				pd.setMemberList(new MemberDAO().printAllMemberList(conn, currentPage)); // 기존 페이지 데이터를 같이 가져온 후,
				pd.setPageNavi(new MemberDAO().getPageNavi(conn, currentPage)); // 새롭게 정의

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCTemplate.close(conn);
			}
			return pd;
		}

// 관리자페이지에서 삭제
	public int deleteMarketToAdmin(int memberNo) {
		int result = 0;
		Connection conn = null;

		try {
			conn = factory.createConnection();
			result = new MemberDAO().deleteMemberToAdmin(conn, memberNo);
			if (result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.commit(conn);
		}
		return result;
	}

// 관리자페이지에서 검색하기
	public MemberPageData printMemberSearchToAdmin(String searchKeyword, int currentPage, String selectOption) {
		Connection conn = null;
		MemberPageData pd = new MemberPageData();
		try {
			conn = factory.createConnection();
			pd.setMemberList(new MemberDAO().selectSearchContentToAdmin(conn, searchKeyword, currentPage, selectOption));
			pd.setPageNavi(new MemberDAO().getSearchPageNaviToAdmin(conn, searchKeyword, currentPage, selectOption));
			System.out.println(selectOption);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pd;
	}
	public Member selectOneById(String userId) {
		Member member = null;
		Connection conn = null;
		
		try {
			conn = factory.createConnection();
			member = new MemberDAO().selectOneById(conn,userId);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		
		return member;
	}
}