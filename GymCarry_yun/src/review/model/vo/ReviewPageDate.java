package review.model.vo;

import java.util.ArrayList;

public class ReviewPageDate {

	private ArrayList<Review> rList;
	private String pageNavi;
	
	public ReviewPageDate() {}

	public ArrayList<Review> getrList() {
		return rList;
	}

	public void setrList(ArrayList<Review> rList) {
		this.rList = rList;
	}

	public String getPageNavi() {
		return pageNavi;
	}

	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}
	
	
}
