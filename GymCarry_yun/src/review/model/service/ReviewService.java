package review.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import review.model.dao.ReviewDAO;
import review.model.vo.Review;
import review.model.vo.ReviewPageDate;
import review.model.vo.ReviewPic;

public class ReviewService {
   
   private JDBCTemplate factory;
   
   public ReviewService() {
      factory = JDBCTemplate.getConnection();
   }

   public ArrayList<Review> printReviewList(int partnerCode) {
      Connection conn = null;
      ArrayList<Review> rList = null;
      
      try {
         conn = factory.createConnection();
         rList = new ReviewDAO().selectReviewList(conn, partnerCode);
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         JDBCTemplate.close(conn);
      }
      return rList;
   }

   public ReviewPageDate printAllList(int currentPage, int partnerCode) {
      Connection conn = null;
      ReviewPageDate pd = new ReviewPageDate();
      
      try {
         conn = factory.createConnection();
         pd.setrList(new ReviewDAO().selectAllList(conn, currentPage, partnerCode));
         pd.setPageNavi(new ReviewDAO().getPageNavi(conn, currentPage, partnerCode));
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCTemplate.close(conn);
      }
      return pd;
   }
   

   // 마이페이지에서 내가 쓴 리뷰 불러오기 코드
      public ArrayList<Review> printReviewListById(int uniqId) {
         Connection conn =null;
         ArrayList<Review> list = null;
         
         try {
            conn = factory.createConnection();
            list = new ReviewDAO().printReviewListById(conn, uniqId);
            
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } finally {
            JDBCTemplate.close(conn);
         }
         return list;
      }

      public int deleteReview(int uniqId, int reviewNo) {
         Connection conn = null;
         int result = 0;
         
         try {
            conn = factory.createConnection();
            result = new ReviewDAO().deleteReview(conn,uniqId,reviewNo);
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

      public Review printOneReview(int reviewNo) {
         Connection conn =null;
         Review review = null;
         
         try {
            conn = factory.createConnection();
            review  = new ReviewDAO().printOneReview(conn,reviewNo);
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } finally {
            JDBCTemplate.close(conn);
         }
         return review;
      }

      public int modifyReview(Review review) {
         Connection conn = null;
         int result = 0;
         
         try {
            conn = factory.createConnection();
            result= new ReviewDAO().modifyReview(conn,review);
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
      
      // 리뷰 수정시 사진 삭제 후 다시 재 업로드 하는 방식
      public int deleteReviewFile(int reviewNo) {
         Connection conn = null;
         int result = 0;
         
         try {
            conn = factory.createConnection();
            result = new ReviewDAO().deleteReviewFile(conn,reviewNo);
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


      public int updateReviewFile(ReviewPic reviewPic, int reviewNo) {
         Connection conn = null;
         int result =0;
         
         try {
            conn = factory.createConnection();
            result = new ReviewDAO().updateReviewFile(conn, reviewPic, reviewNo);
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

      public ArrayList<ReviewPic> printReviewPics(ArrayList<Review> rList) {
         // 리뷰사진 출력 메소드
         Connection conn = null;
         ArrayList<ReviewPic> rPics = new ArrayList<ReviewPic>();
         try {
            conn = factory.createConnection();
            for(int i = 0; i < rList.size(); i++) {
               //리뷰번호를 인자로 전달하여 리뷰사진을 가져옴
               ReviewPic reviewPic = new ReviewDAO().selectReviewPic(conn, rList.get(i).getRvNo());
               //가져온 리뷰사진을 배열에 추가
               rPics.add(reviewPic);
            }

         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            JDBCTemplate.close(conn);
         }
         return rPics;
      }
      
      public int getReviewCount(int partnerCode) {
            int total = 0;
            Connection conn = null;
            try {
               conn = factory.createConnection();
               total = new ReviewDAO().totalCount(conn, partnerCode);
            } catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            } finally {
               JDBCTemplate.close(conn);
            }
            return total;
         }
      
      public Review insertReview(int partnerCode) {
         Connection conn = null;
         Review review = null;
          try {
            conn = factory.createConnection();
            review = new ReviewDAO().insertReview(conn, partnerCode);
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }finally {
            JDBCTemplate.close(conn);
         }
         return review;
      }
      
      public int insertWriteReview(Review review2) {
         Connection conn = null;
         int result = 0;
         try {
            conn = factory.createConnection();
            result = new ReviewDAO().insertWriteReview(conn, review2);
            if (result > 0) {
               
            } else {
               JDBCTemplate.rollback(conn);
            }
         System.out.println("결과값 :" + result);
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }finally {
            JDBCTemplate.close(conn);
         }
         return result;
      }
      
      public int insertReviewFile(ReviewPic reviewPic, int uniqId) {
         Connection conn = null;
         int result = 0;
         
         try {
            conn = factory.createConnection();
            result = new ReviewDAO().printOneReview(conn, reviewPic, uniqId);
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

	public ArrayList<Review> printReviewListToAdmin(int partner) {
		Connection conn =null;
        ArrayList<Review> list = null;
        
        try {
			conn = factory.createConnection();
			list = new ReviewDAO().printReviewListToAdmin(conn, partner);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
        return list;
	}

	public int deleteReviewToAdmin(int reviewNo) {
		Connection conn = null;
        int result = 0;
        
        try {
			conn = factory.createConnection();
			result = new ReviewDAO().deleteReviewToAdmin(conn, reviewNo);
			if (result >0 ) {
				JDBCTemplate.commit(conn);
			}else {
	               JDBCTemplate.rollback(conn);
	            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
	}
      
      

   }
