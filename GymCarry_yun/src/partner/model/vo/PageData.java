package partner.model.vo;

import java.util.ArrayList;

public class PageData {
	private ArrayList<Partner> partnerList;
	private String pageNavi;
	
	public PageData() {}

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
		return "PageData [partnerList=" + partnerList + ", pageNavi=" + pageNavi + "]";
	}

	

}
