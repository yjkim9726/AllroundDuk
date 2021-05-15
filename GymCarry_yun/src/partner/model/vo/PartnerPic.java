package partner.model.vo;

import java.sql.Timestamp;

public class PartnerPic {
	private String fileName;
	private int partnerCode;
	private String filePath;
	private long fileSize;
	private Timestamp uploadTime;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(int partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public Timestamp getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
	@Override
	public String toString() {
		return "PartnerPic [fileName=" + fileName + ", partnerCode=" + partnerCode + ", filePath=" + filePath
				+ ", fileSize=" + fileSize + ", uploadTime=" + uploadTime + "]";
	}
}
