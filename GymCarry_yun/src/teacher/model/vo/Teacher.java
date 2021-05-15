package teacher.model.vo;

import java.sql.Timestamp;

public class Teacher {

	private int tchrNo;
	private int partnerCode;
	private String tchName;
	private String tchrCareer;
	private String tchrClass;
	private String fileName;
	private String filePath;
	private int fileSize;
	private Timestamp uploadtime;
	
	
	public int getTchrNo() {
		return tchrNo;
	}
	public void setTchrNo(int tchrNo) {
		this.tchrNo = tchrNo;
	}
	public int getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(int partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getTchName() {
		return tchName;
	}
	public void setTchName(String tchName) {
		this.tchName = tchName;
	}
	public String getTchrCareer() {
		return tchrCareer;
	}
	public void setTchrCareer(String tchrCareer) {
		this.tchrCareer = tchrCareer;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public Timestamp getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(Timestamp uploadtime) {
		this.uploadtime = uploadtime;
	}
	
	public String getTchClass() {
		return tchrClass;
	}
	public void setTchClass(String tchClass) {
		this.tchrClass = tchClass;
	}
	@Override
	public String toString() {
		return "Teacher [tchrNo=" + tchrNo + ", partnerCode=" + partnerCode + ", tchName=" + tchName + ", tchrCareer="
				+ tchrCareer + ", tchrClass=" + tchrClass + ", fileName=" + fileName + ", filePath=" + filePath
				+ ", fileSize=" + fileSize + ", uploadtime=" + uploadtime + "]";
	}
	
	
	

}
