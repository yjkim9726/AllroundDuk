package member.model.vo;
// 사용자 
public class Member {
	
	// 생성자
	public Member() {}

    // 회원고유번호 
    private int uniqId;

    // 아이디 
    private String userId;

    // 비밀번호 
    private String userPwd;

    // 닉네임 
    private String nickname;

    // 이름 
    private String name;

    // 성별 
    private String gender;

    // 이메일 
    private String email;

    // 연락처 
    private String phone;

    // 시 
    private String addressCity;

    // 구 
    private String addressGu;

    // 관리자여부 
    private String adminYn;
    
    // 페이지수
    private int num;

    public int getUniqId() {
        return uniqId;
    }

    public void setUniqId(int uniqId) {
        this.uniqId = uniqId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressGu() {
        return addressGu;
    }

    public void setAddressGu(String addressGu) {
        this.addressGu = addressGu;
    }

    public String getAdminYn() {
        return adminYn;
    }

    public void setAdminYn(String adminYn) {
        this.adminYn = adminYn;
    }
    
    public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	// Member 모델 복사
    public void CopyData(Member parameter)
    {
        this.uniqId = parameter.getUniqId();
        this.userId = parameter.getUserId();
        this.userPwd = parameter.getUserPwd();
        this.nickname = parameter.getNickname();
        this.name = parameter.getName();
        this.gender = parameter.getGender();
        this.email = parameter.getEmail();
        this.phone = parameter.getPhone();
        this.addressCity = parameter.getAddressCity();
        this.addressGu = parameter.getAddressGu();
        this.adminYn = parameter.getAdminYn();
    }

	@Override
	public String toString() {
		return "Member [uniqId=" + uniqId + ", userId=" + userId + ", userPwd=" + userPwd + ", nickname=" + nickname
				+ ", name=" + name + ", gender=" + gender + ", email=" + email + ", phone=" + phone + ", addressCity="
				+ addressCity + ", addressGu=" + addressGu + ", adminYn=" + adminYn + "]";
	}
    
    
}