package partner.model.vo;

public class Partner {

	private int partnerCode; //업체코드
	private String partnerName; // 업체명
	private String partnerType; // 운동종목
	private String partnerAddress; //주소명
	private String partnerPhone; //연락처
	private String partnerHours; // 영업시간
	private String partnerParking; //주차여부
	private String station;
	private String addContent;
	private String partnerPrice;
	private int num;
	

	private void Partner() {}
	
	public int getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(int partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
	public String getPartnerAddress() {
		return partnerAddress;
	}
	public void setPartnerAddress(String partnerAddress) {
		this.partnerAddress = partnerAddress;
	}
	public String getPartnerPhone() {
		return partnerPhone;
	}
	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}
	public String getPartnerHours() {
		return partnerHours;
	}
	public void setPartnerHours(String partnerHours) {
		this.partnerHours = partnerHours;
	}
	public String getPartnerParking() {
		return partnerParking;
	}
	public void setPartnerParking(String partnerParking) {
		this.partnerParking = partnerParking;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
	public String getAddContent() {
		return addContent;
	}
	
	public void setAddContent(String addContent) {
		this.addContent = addContent;
	}

	
	public String getPartnerPrice() {
		return partnerPrice;
	}

	public void setPartnerPrice(String partnerPrice) {
		this.partnerPrice = partnerPrice;
	}
	

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Partner [partnerCode=" + partnerCode + ", partnerName=" + partnerName + ", partnerType=" + partnerType
				+ ", partnerAddress=" + partnerAddress + ", partnerPhone=" + partnerPhone + ", partnerHours="
				+ partnerHours + ", partnerParking=" + partnerParking + ", station=" + station + ", addContent="
				+ addContent + ", partnerPrice=" + partnerPrice + ", num=" + num + "]";
	}

	

	
	
	
	
}