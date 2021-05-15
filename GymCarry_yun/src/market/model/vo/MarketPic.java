package market.model.vo;

import java.sql.Timestamp;

public class MarketPic {
	
	private String fileName;
	private String filePath;
	private long fileSize;
	private String fileUser;
	private Timestamp uploadTime;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileUser() {
		return fileUser;
	}
	public void setFileUser(String fileUser) {
		this.fileUser = fileUser;
	}
	public Timestamp getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Override
	public String toString() {
		return "MarketPic [fileName=" + fileName + ", filePath=" + filePath + ", fileSize="
				+ fileSize + ", fileUser=" + fileUser + ", uploadTime=" + uploadTime + "]";
	}
	
	
}