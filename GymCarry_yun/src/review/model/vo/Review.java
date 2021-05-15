package review.model.vo;

public class Review {

		private int rvNo;
		private int uniqId;
		private int partnerCode;
		private String rvContent;
		private String rvRecommend;
		private String rvDate;
		private String nickName;
		private String partnerName;
		private String FileName;
		
		
		public String getFileName() {
			return FileName;
		}

		public void setFileName(String fileName) {
			FileName = fileName;
		}

		public String getPartnerName() {
			return partnerName;
		}

		public void setPartnerName(String partnerName) {
			this.partnerName = partnerName;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public Review() {}

		public int getRvNo() {
			return rvNo;
		}

		public void setRvNo(int rvNo) {
			this.rvNo = rvNo;
		}

		public int getUniqId() {
			return uniqId;
		}

		public void setUniqId(int uniqId) {
			this.uniqId = uniqId;
		}

		public int getPartnerCode() {
			return partnerCode;
		}

		public void setPartnerCode(int partnerCode) {
			this.partnerCode = partnerCode;
		}

		public String getRvContent() {
			return rvContent;
		}

		public void setRvContent(String rvContent) {
			this.rvContent = rvContent;
		}

		public String getRvRecommend() {
			return rvRecommend;
		}

		public void setRvRecommend(String rvRecommend) {
			this.rvRecommend = rvRecommend;
		}

		public String getRvDate() {
			return rvDate;
		}

		public void setRvDate(String rvDate) {
			this.rvDate = rvDate;
		}

		@Override
		public String toString() {
			return "Review [rvNo=" + rvNo + ", uniqId=" + uniqId + ", partnerCode=" + partnerCode + ", rvContent="
					+ rvContent + ", rvRecommend=" + rvRecommend + ", rvDate=" + rvDate + ", nickName=" + nickName
					+ ", partnerName=" + partnerName + ", FileName=" + FileName + "]";
		}

		
		
		
}
