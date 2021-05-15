package member.model.vo;

import java.util.ArrayList;

public class MemberPageData {
	private ArrayList<Member> memberList;
	private String pageNavi;

	public ArrayList<Member> getMemberList() {
		return memberList;
	}

	public String getPageNavi() {
		return pageNavi;
	}

	public void setMemberList(ArrayList<Member> memberList) {
		this.memberList = memberList;
	}

	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}

	@Override
	public String toString() {
		return "PageData [noticeList=" + memberList + ", pageNavi=" + pageNavi + "]";
	}

}
