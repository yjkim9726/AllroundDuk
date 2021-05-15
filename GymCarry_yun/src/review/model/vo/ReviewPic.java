package review.model.vo;

import java.sql.Timestamp;

public class ReviewPic {
	
	private String fileName;
	private int rvNo;
	private String filePath;
	private long fileSize;
	private String fileUser;
	private Timestamp uploadtime; 
		
	public ReviewPic() {}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getRvNo() {
		return rvNo;
	}

	public void setRvNo(int rvNo) {
		this.rvNo = rvNo;
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

	public String getFileUser() {
		return fileUser;
	}

	public void setFileUser(String fileUser) {
		this.fileUser = fileUser;
	}

	public Timestamp getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Timestamp uploadtime) {
		this.uploadtime = uploadtime;
	}

	@Override
	public String toString() {
		return "ReviewPic [fileName=" + fileName + ", rvNo=" + rvNo + ", filePath=" + filePath + ", fileSize="
				+ fileSize + ", fileUser=" + fileUser + ", uploadtime=" + uploadtime + "]";
	}
	
	

}
