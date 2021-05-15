package event.model.vo;

import java.util.ArrayList;

public class PageData {
	private ArrayList<Event> eventList;
	private String pageNavi;
	
	public PageData() {}

	public ArrayList<Event> getEventList() {
		return eventList;
	}

	public void setEventList(ArrayList<Event> eventList) {
		this.eventList = eventList;
	}

	public String getPageNavi() {
		return pageNavi;
	}

	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}

}
