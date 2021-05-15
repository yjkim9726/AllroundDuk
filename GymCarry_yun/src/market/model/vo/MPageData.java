package market.model.vo;

import java.util.ArrayList;

public class MPageData {
	
	private ArrayList<Market> marketList;
	private String pageNavi;
	
	public MPageData() {}

	public ArrayList<Market> getMarketList() {
		return marketList;
	}

	public void setMarketList(ArrayList<Market> marketList) {
		this.marketList = marketList;
	}

	public String getPageNavi() {
		return pageNavi;
	}

	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}

	@Override
	public String toString() {
		return "PageData [noticeList=" + marketList + ", pageNavi=" + pageNavi + "]";
	}
	
	
}