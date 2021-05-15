package like.model.vo;

public class Like {
	private int likeNo;
	private int marketNo;
	private int uniqId;
	
	public Like() {}

	public int getLikeNo() {
		return likeNo;
	}

	public void setLikeNo(int likeNo) {
		this.likeNo = likeNo;
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

	@Override
	public String toString() {
		return "Like [likeNo=" + likeNo + ", marketNo=" + marketNo + ", uniqId=" + uniqId + "]";
	}
	
	
}
