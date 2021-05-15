package partner.model.vo;

import java.util.ArrayList;

import org.apache.catalina.tribes.transport.nio.ParallelNioSender;

public class PartnerPageData {
	private final int countPerPage = 4;
	private int currentPage;
	private int startPage;
	private int endPage;
	private int lastPage;
	private int total;
	private ArrayList<Partner> partnerList;
	private String pageNavi;
	
	//기본생성자
	public PartnerPageData() {}
	//커스텀 생성자
	public PartnerPageData(int total, int currentPage) {
		this.total = total;
		this.currentPage = currentPage; //currentPage에는 1이상의 값이 있어야함
		lastPage = (int)(Math.ceil((double)total/(double)countPerPage));
		endPage = (int)(Math.ceil((double)currentPage/(double)countPerPage)*countPerPage);
		if(endPage > lastPage) {
			endPage = lastPage;
		}
		startPage = endPage - currentPage + 1;
		if(startPage < 1) {
			startPage = 1;
		}
		//페이지 수를 구하는데 그냥 정수로 나누면 문제가 생기기 때문에
		//실수로 바꾸고 결과값을 올림한 뒤 다시 정수로 바꿔준다

	}
	//페이지당 보여지는 수는 고정값이기 때문에 getter/setter 필요없음
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	public ArrayList<Partner> getPartnerList() {
		return partnerList;
	}
	public void setPartnerList(ArrayList<Partner> partnerList) {
		this.partnerList = partnerList;
	}
	public String getPageNavi() {
		return pageNavi;
	}
	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}
	@Override
	public String toString() {
		return "PartnerPageData [countPerPage=" + countPerPage + ", currentPage=" + currentPage + ", startPage="
				+ startPage + ", endPage=" + endPage + ", lastPage=" + lastPage + ", total=" + total + ", partnerList="
				+ partnerList + ", pageNavi=" + pageNavi + "]";
	}
	
}
