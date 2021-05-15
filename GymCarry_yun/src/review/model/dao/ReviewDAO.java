package review.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import oracle.net.aso.n;
import partner.model.vo.Partner;
import review.model.vo.Review;
import review.model.vo.ReviewPic;

public class ReviewDAO {

   public ArrayList<Review> selectReviewList(Connection conn, int partnerCode) {
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList<Review> rList = null;
      String query = "SELECT REVIEW.*, MEMBER.NICKNAME FROM REVIEW JOIN MEMBER ON REVIEW.UNIQ_ID = MEMBER.UNIQ_ID WHERE PARTNER_CODE = ?";
      
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, partnerCode);
         rset = pstmt.executeQuery();
         rList = new ArrayList<Review>();
         while (rset.next()) {
            Review review = new Review();
            review.setRvNo(rset.getInt("RV_NO"));
            review.setUniqId(rset.getInt("UNIQ_ID"));
            review.setPartnerCode(rset.getInt("PARTNER_CODE"));
            review.setRvContent(rset.getString("RV_CONTENT"));
            review.setRvRecommend(rset.getString("RV_RECOMMEND"));
            review.setRvDate(rset.getString("RV_DATE"));
            review.setNickName(rset.getString("NICKNAME"));
            rList.add(review); //저장소
         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         JDBCTemplate.close(rset);
         JDBCTemplate.close(pstmt);
      }
      return rList;
   }

   public ArrayList<Review> selectAllList(Connection conn, int currentPage, int partnerCode) {
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList<Review> rList = null;
      String query = "SELECT * FROM( SELECT ROW_NUMBER() OVER(ORDER BY RV_NO DESC) AS NUM, REVIEW.*, MEMBER.NICKNAME FROM REVIEW JOIN MEMBER ON REVIEW.UNIQ_ID = MEMBER.UNIQ_ID WHERE PARTNER_CODE=?) WHERE NUM BETWEEN ? AND ?" ;
      int recodeCountPerPage = 2;
      int start = currentPage*recodeCountPerPage - (recodeCountPerPage-1);
      int end = currentPage*recodeCountPerPage; 
      
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, partnerCode);
         pstmt.setInt(2, start);
         pstmt.setInt(3, end);
         rset = pstmt.executeQuery();
         rList = new ArrayList<Review>();
         while (rset.next()) {
            Review review = new Review();
            review.setRvNo(rset.getInt("RV_NO"));
            review.setUniqId(rset.getInt("UNIQ_ID"));
            review.setPartnerCode(rset.getInt("PARTNER_CODE"));
            review.setRvContent(rset.getString("RV_CONTENT"));
            review.setRvRecommend(rset.getString("RV_RECOMMEND"));
            review.setRvDate(rset.getString("RV_DATE"));
            review.setNickName(rset.getString("NICKNAME"));
            rList.add(review); //저장소
            
         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         JDBCTemplate.close(rset);
         JDBCTemplate.close(pstmt);
      }
      
      return rList;
   }

   public String getPageNavi(Connection conn, int currentPage, int partnerCode) {
      int recordTotalCount = totalCount(conn, partnerCode);
      int recordCountPerPage = 2;
      int pageTotalCount = 0;
      if (recordTotalCount % recordCountPerPage > 0) {
         pageTotalCount = recordTotalCount / recordCountPerPage + 1;
      }else {
         pageTotalCount = recordTotalCount / recordCountPerPage;
      }
      
      if (currentPage < 1) {
         currentPage = 1;
      } else if (currentPage > pageTotalCount) {
         currentPage = pageTotalCount;
      }
      int naviCountPerPage = 3;
      int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;
      int endNavi = startNavi + naviCountPerPage - 1;
      
      //오류방ㅈㅣ
      if (endNavi > pageTotalCount) {
         endNavi = pageTotalCount;
      }
      boolean prev = true;
      boolean next = true;
      if (startNavi == 1) {
         prev = false;
      }
      if (endNavi == pageTotalCount) {
         next = false;
      }
      
      StringBuilder sb = new StringBuilder();
      if (prev) {
         sb.append("<a href='/partner/detail?code="+partnerCode+"&currentPage=" + (startNavi-1) + "'> < </a>");
      }
      for (int i = startNavi; i <= endNavi; i++) {
         sb.append("<a href='/partner/detail?code="+partnerCode+"&currentPage=" + i + "'>" + i + " </a>"); 
      }
      if (next) {
         sb.append("<a href='/partner/detail?code="+partnerCode+"&currentPage=" + (endNavi +1) + "'> > </a>");
      }
      return sb.toString();
   }


// 내가 쓴 리뷰 리스트 불러오기 
	// review vo에 filename을 불러오기위해 추가해줬으니 다인님과다를것 있는지 확인할것! 
	public ArrayList<Review> printReviewListById(Connection conn, int uniqId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT PARTNER_NAME, RV_CONTENT, RV_NO, FILENAME ,RV_RECOMMEND,UNIQ_ID FROM REVIEW "
				+ "JOIN PARTNER_DETAIL USING (PARTNER_CODE) JOIN REVIEWPIC USING (RV_NO) WHERE UNIQ_ID=?";
		ArrayList<Review> list = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uniqId);
			rset = pstmt.executeQuery();
			list = new ArrayList<Review>();
			while(rset.next()) {
				Review review = new Review();
				review.setRvNo(rset.getInt("RV_NO"));
				review.setPartnerName(rset.getString("PARTNER_NAME"));
				review.setRvContent(rset.getString("RV_CONTENT"));
				review.setRvRecommend(rset.getString("RV_RECOMMEND"));
				review.setFileName(rset.getString("FILENAME"));
				review.setUniqId(uniqId);
				list.add(review);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

   // 내가 쓴 리뷰 지우기 
   public int deleteReview(Connection conn, int uniqId, int reviewNo) {
      PreparedStatement pstmt = null;
      int result = 0;
      String query = "DELETE FROM REVIEW WHERE UNIQ_ID=? AND RV_NO =?";
      
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, uniqId);
         pstmt.setInt(2, reviewNo);
         result = pstmt.executeUpdate();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         JDBCTemplate.close(pstmt);
      }
      return result;
   }

   public Review printOneReview(Connection conn, int reviewNo) {
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      Review review = null;
      String query = "SELECT UNIQ_ID,PARTNER_CODE,RV_CONTENT,RV_RECOMMEND,RV_DATE,PARTNER_NAME,FILENAME FROM REVIEW JOIN PARTNER_DETAIL USING (PARTNER_CODE) JOIN REVIEWPIC USING (RV_NO) WHERE RV_NO = ?";
      
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, reviewNo);
         rset = pstmt.executeQuery();
         
         if(rset.next()) {
            review = new Review();
            review.setRvNo(reviewNo);
            review.setUniqId(rset.getInt("UNIQ_ID"));
            review.setPartnerName(rset.getString("PARTNER_NAME"));
            review.setPartnerCode(rset.getInt("PARTNER_CODE"));
            review.setRvContent(rset.getString("RV_CONTENT"));
            review.setRvRecommend(rset.getString("RV_RECOMMEND"));
            review.setRvDate(rset.getString("RV_DATE"));
            review.setFileName(rset.getString("FILENAME"));
         }
               
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         JDBCTemplate.close(rset);
         JDBCTemplate.close(pstmt);
      }
      
      return review;
   }
   // 리뷰 수정코드 
   public int modifyReview(Connection conn, Review review) {
      PreparedStatement pstmt = null;
      int result = 0;
      String query = "UPDATE REVIEW SET RV_CONTENT =?,RV_RECOMMEND=? WHERE RV_NO=?";
      
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, review.getRvContent());
         pstmt.setString(2, review.getRvRecommend());
         pstmt.setInt(3, review.getRvNo());
         result = pstmt.executeUpdate();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return result;
   }
   // 리뷰 사진 삭제후 재업로드하기 때문에 삭제 코드 필요
   public int deleteReviewFile(Connection conn, int reviewNo) {
      PreparedStatement pstmt = null;
      int result = 0;
      String query = "DELETE FROM REVIEWPIC WHERE RV_NO = ?";
      
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1,reviewNo);
         result = pstmt.executeUpdate();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         JDBCTemplate.close(pstmt);
      }
      
      return result;
   }
   // 리뷰 사진  삭제 후 재업로드 코드 
   public int updateReviewFile(Connection conn, ReviewPic reviewPic, int reviewNo) {
      PreparedStatement pstmt = null;
      int result = 0;
      String query = "INSERT INTO REVIEWPIC VALUES (?,?,?,?,?,?)";
      
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, reviewPic.getFileName());
         pstmt.setInt(2, reviewNo);
         pstmt.setString(3, reviewPic.getFilePath());
         pstmt.setLong(4, reviewPic.getFileSize());
         pstmt.setString(5, reviewPic.getFileUser());
         pstmt.setTimestamp(6, reviewPic.getUploadtime());
         result = pstmt.executeUpdate();
         
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         JDBCTemplate.close(pstmt);
      }
      return result;
   }

   public ReviewPic selectReviewPic(Connection conn, int rvNo) {
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ReviewPic reviewPic = null;
      String query = "SELECT * FROM REVIEWPIC WHERE RV_NO = ?";
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, rvNo);
         rset = pstmt.executeQuery();
         if(rset.next()) {
            reviewPic = new ReviewPic();
            reviewPic.setFileName(rset.getString("FILENAME"));
            reviewPic.setFilePath(rset.getString("FILEPATH"));
            reviewPic.setFileSize(rset.getLong("FILESIZE"));
            reviewPic.setFileUser(rset.getString("FILEUSER"));
            reviewPic.setUploadtime(rset.getTimestamp("UPLOADTIME"));
         }
         
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         JDBCTemplate.close(pstmt);
      }
      return reviewPic;
   }
   
      public int totalCount(Connection conn, int partnerCode) {
            PreparedStatement pstmt = null;
            ResultSet rset = null;
            String query = "SELECT COUNT(*) AS TOTALCOUNT FROM REVIEW WHERE PARTNER_CODE = ?";
            int recordTotalCount = 0;
            
            try {
               pstmt = conn.prepareStatement(query);
               pstmt.setInt(1, partnerCode);
               rset = pstmt.executeQuery();
               if(rset.next()) {
                  recordTotalCount = rset.getInt("TOTALCOUNT");
               }
            } catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            } finally {
               JDBCTemplate.close(rset);
               JDBCTemplate.close(pstmt);
            }
            return recordTotalCount;
         }
      
      public Review insertReview(Connection conn, int partnerCode) {
         PreparedStatement pstmt = null;
         ResultSet rset = null;
         Review review = null;
         String query = "SELECT REVIEW.*, PARTNER_DETAIL.PARTNER_NAME FROM REVIEW JOIN PARTNER_DETAIL ON REVIEW.PARTNER_CODE = PARTNER_DETAIL.PARTNER_CODE WHERE REVIEW.PARTNER_CODE = ?";
         try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, partnerCode);
            rset = pstmt.executeQuery();
            if (rset.next()) {
               review = new Review();
               review.setRvNo(rset.getInt("RV_NO"));
               review.setUniqId(rset.getInt("UNIQ_ID"));
               review.setPartnerCode(rset.getInt("PARTNER_CODE"));
               review.setRvContent(rset.getString("RV_CONTENT"));
               review.setRvRecommend(rset.getString("RV_RECOMMEND"));
               review.setRvDate(rset.getString("RV_DATE"));
               review.setPartnerName(rset.getString("PARTNER_NAME"));
            }
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
         }
         return review;
      }
      
      public int insertWriteReview(Connection conn, Review review2) {
         PreparedStatement pstmt = null;
         int result = 0;
         String query = "INSERT INTO REVIEW VALUES (REVIEW_SEQ.NEXTVAL,?,?,?,?,SYSDATE)";
         
         try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, review2.getUniqId());
            pstmt.setInt(2, review2.getPartnerCode());
            pstmt.setString(3, review2.getRvContent());
            pstmt.setString(4, review2.getRvRecommend());
            result = pstmt.executeUpdate();
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } finally {
            JDBCTemplate.close(pstmt);
         }
         
         return result;
      }

      public int printOneReview(Connection conn, ReviewPic reviewPic, int uniqId) {
          PreparedStatement pstmt = null;
          int result = 0;
          String query = "INSERT INTO REVIEWPIC VALUES (?,(SELECT MAX(RV_NO) FROM REVIEW WHERE UNIQ_ID=?),?,?,?,?)";
          try {
             pstmt = conn.prepareStatement(query);
             pstmt.setString(1, reviewPic.getFileName());
             pstmt.setInt(2, uniqId);
             pstmt.setString(3, reviewPic.getFilePath());
             pstmt.setLong(4, reviewPic.getFileSize());
             pstmt.setString(5, reviewPic.getFileUser());
             pstmt.setTimestamp(6, reviewPic.getUploadtime());
             result = pstmt.executeUpdate();
             
          } catch (SQLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
          } finally {
             JDBCTemplate.close(pstmt);
          }
          
          return result;
       }

	public ArrayList<Review> printReviewListToAdmin(Connection conn, int partner) {
		PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    String query = "SELECT PARTNER_NAME, RV_CONTENT, RV_NO, FILENAME ,RV_RECOMMEND, UNIQ_ID FROM REVIEW JOIN PARTNER_DETAIL USING (PARTNER_CODE) JOIN REVIEWPIC USING (RV_NO) WHERE PARTNER_CODE = ?";
	    ArrayList<Review> list = null;
	    
	    try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, partner);
			 rset = pstmt.executeQuery();
	         list = new ArrayList<Review>();
	         while(rset.next()) {
	            Review review = new Review();
	            review.setRvNo(rset.getInt("RV_NO"));
	            review.setPartnerName(rset.getString("PARTNER_NAME"));
	            review.setRvContent(rset.getString("RV_CONTENT"));
	            review.setRvRecommend(rset.getString("RV_RECOMMEND"));
	            review.setFileName(rset.getString("FILENAME"));
	            list.add(review);
	         }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	         JDBCTemplate.close(rset);
	         JDBCTemplate.close(pstmt);
	      }
	      return list;
	}

	public int deleteReviewToAdmin(Connection conn, int reviewNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "DELETE FROM REVIEW WHERE RV_NO = ? ";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
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