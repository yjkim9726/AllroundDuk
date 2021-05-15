package partner.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import partner.model.dao.PartnerDAO;
import partner.model.vo.PageData;
import partner.model.vo.Partner;
import partner.model.vo.PartnerPageData;
import partner.model.vo.PartnerPic;
import partner.model.vo.PricePic;
import teacher.model.dao.TeacherDAO;
import teacher.model.vo.Teacher;

public class PartnerService {
	
	private JDBCTemplate factory;
	
	public PartnerService() {
		factory = JDBCTemplate.getConnection();
	}
	
	public ArrayList<Partner> printAllPartnerList() {
		ArrayList<Partner> pList = null;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			pList = new PartnerDAO().selectPartnerList(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pList;
	}

	public ArrayList<Partner> searchByStation(String station) {
		ArrayList<Partner> pList = null;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			pList = new PartnerDAO().selectByStation(conn,station);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pList;
	}

	public ArrayList<Partner> searchPartnerList(String name) {
		ArrayList<Partner> pList = null;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			pList = new PartnerDAO().selectByName(conn,name);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pList;
	}

	public int insertPartnerPic(PartnerPic pic) {
		Connection conn = null;
		int result = 0;
		try {
			conn =factory.createConnection();
			result = new PartnerDAO().insertPartnerPic(conn,pic);
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

	
	public PartnerPic printOnePic(int partnerCode) {
		PartnerPic partnerPic = null;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			partnerPic = new PartnerDAO().selectPartnerPic(conn, partnerCode);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return partnerPic;
	}
	
	public PartnerPageData getPageData(int total, int currentPage) {
		//업체리스트 전체를 불러오고 그중 일부를 보여주는 형식
		//이 부분에서 데이터베이스 연결은 필요 없을 것 같다
		return new PartnerPageData(total, currentPage);
	}



	//관리자 - 업체페이지 조회
	public PageData selectPartnerListToAdmin(int currentPage) {
		Connection conn = null;
		PageData pageData = new PageData();
		
		try {
			conn = factory.createConnection();
			pageData.setPartnerList(new PartnerDAO().selectPartnerListToAdmin(conn, currentPage));
			pageData.setPageNavi(new PartnerDAO().getPageNaviToAdmin(conn, currentPage));
			System.out.println("pagedata" + pageData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pageData;
	}

	//관리자 - 업체페이지 검색
	public PageData printSearchListToAdmin(String searchKeyword, int currentPage, String selectOption) {
		Connection conn = null;
		PageData pageData = new PageData();
		try {
			conn = factory.createConnection();
			pageData.setPartnerList(new PartnerDAO().selectPartnerSearchList(conn, searchKeyword, currentPage, selectOption));
			pageData.setPageNavi(new PartnerDAO().getSearchPageNavi(conn, searchKeyword, currentPage, selectOption));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pageData;
	}

	public int modifyPartner(Teacher teacher) {
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

	public ArrayList<PartnerPic> printAllPic(int partnerCode) {
		ArrayList<PartnerPic> pPics = null;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			pPics = new PartnerDAO().selectAllPic(conn, partnerCode);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pPics;
	}
	
	public int insertPricePic(PricePic pic) {
	      Connection conn = null;
	      int result = 0;
	      try {
	         conn =factory.createConnection();
	         result = new PartnerDAO().insertPricePic(conn,pic);
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

	public ArrayList<PricePic> printPricePic(int partnerCode) {
		ArrayList<PricePic> pricePics = null;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			pricePics = new PartnerDAO().selectPricePic(conn, partnerCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return pricePics;
	}
	
	public Partner printOnePartner(int partnerCode) {
		Partner partner = null;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			partner = new PartnerDAO().selectByCode(conn, partnerCode);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return partner;
	}

	public Teacher printTeacherList(int partnerCode) {
		Teacher teacher = null;
		Connection conn = null;

		try {
			conn = factory.createConnection();
			teacher = new PartnerDAO().printTeacherList(conn, partnerCode);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return teacher;
	}


	public int deletePartnerToAdmin(int partnerCode) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new PartnerDAO().deletePartnerToAdmin(conn, partnerCode);
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

	public int modifyPartner(Partner partner) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new PartnerDAO().modifyPartner(conn, partner);
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

	public int deletePartnerFile(int partnerCode) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new PartnerDAO().deletePartnerFile(conn, partnerCode);
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

	public int deletePriceFile(int partnerCode) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new PartnerDAO().deletePriceFile(conn, partnerCode);
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

	public int updatePartnerFile(PartnerPic partnerPic, int partnerCode) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new PartnerDAO().updatePartnerFile(conn, partnerPic, partnerCode);
			if(result > 0) {
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

	public int updatePriceFile(PricePic pricePic, int partnerCode) {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = factory.createConnection();
			result = new PartnerDAO().updatePriceFile(conn, pricePic, partnerCode);
			if(result == 0 ) {
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

	public int addPartner(Partner partner) {
		int partnerCode = 0;
		Connection conn = null;
		try {
			conn = factory.createConnection();
			partnerCode = new PartnerDAO().insertPartner(conn, partner);
		} catch (SQLException e) {
			e.printStackTrace();
			partnerCode = -1;
		} finally {
			JDBCTemplate.close(conn);
		}
		return partnerCode;
	}

}


