package event.model.vo;

import java.sql.Date;

public class Event {
	private int eventNo;
	private String partnerName;
	private String eventAddress;
	private String eventTitle;
	private String eventContent;
	private String startDate;
	private String endDate;
	private String confirm;
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Event() {}

	public int getEventNo() {
		return eventNo;
	}

	public void setEventNo(int eventNo) {
		this.eventNo = eventNo;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	@Override
	public String toString() {
		return "Event [eventNo=" + eventNo + ", partnerName=" + partnerName + ", eventAddress=" + eventAddress
				+ ", eventTitle=" + eventTitle + ", eventContent=" + eventContent + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", confirm=" + confirm + ", fileName=" + fileName + "]";
	}

	
	
	
}
