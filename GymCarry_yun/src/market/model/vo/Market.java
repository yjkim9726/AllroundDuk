package market.model.vo;

import java.sql.Date;

public class Market {
	private int marketNo;
	private int uniqId;
	private String nickName;
	private String marketTitle;
	private String marketPrice;
	private String marketContent;
	private String marketField;
	private Date marketDate;
	private int num;
	
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getMarketField() {
		return marketField;
	}
	public void setMarketField(String marketField) {
		this.marketField = marketField;
	}
	public int getMarketNo() {
		return marketNo;
	}
	public void setMarketNo(int marketNo) {
		this.marketNo = marketNo;
	}
	public int getUniqId() {
		return uniqId;
	}
	public void setUniqId(int uniqId) {
		this.uniqId = uniqId;
	}
	public String getMarketTitle() {
		return marketTitle;
	}
	public void setMarketTitle(String marketTitle) {
		this.marketTitle = marketTitle;
	}
	public String getMarketContent() {
		return marketContent;
	}
	public void setMarketContent(String marketContent) {
		this.marketContent = marketContent;
	}
	
	public Date getMarketDate() {
		return marketDate;
	}
	public void setMarketDate(Date marketDate) {
		this.marketDate = marketDate;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "Market [marketNo=" + marketNo + ", uniqId=" + uniqId + ", nickName=" + nickName + ", marketTitle="
				+ marketTitle + ", marketPrice=" + marketPrice + ", marketContent=" + marketContent + ", marketField="
				+ marketField + ", marketDate=" + marketDate + ", num=" + num + "]";
	}
	
	
	
}
