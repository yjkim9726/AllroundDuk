package message.model.vo;

import java.sql.Date;

public class Message {
	    // 쪽지번호 
	    private int messageNo;

	    // 수신회원 고유번호 
	    private int receiverId;

	    // 발신회원 고유번호 
	    private int senderId;
	    
	    private String nickName;
	    // 내용 
	    private String messageContent;

	    // 날짜 
	    private String messageDate;

	    // 읽음여부 
	    private int readCount;
	    

	    public Message() {}
	    
	    

		public int getMessageNo() {
			return messageNo;
		}



		public void setMessageNo(int messageNo) {
			this.messageNo = messageNo;
		}



		public int getReceiverId() {
			return receiverId;
		}



		public void setReceiverId(int receiverId) {
			this.receiverId = receiverId;
		}



		public int getSenderId() {
			return senderId;
		}



		public void setSenderId(int senderId) {
			this.senderId = senderId;
		}



		public String getMessageContent() {
			return messageContent;
		}



		public void setMessageContent(String messageContent) {
			this.messageContent = messageContent;
		}



		public String getMessageDate() {
			return messageDate;
		}



		public void setMessageDate(String messageDate) {
			this.messageDate = messageDate;
		}



		public int getReadCount() {
			return readCount;
		}



		public void setReadCount(int readCount) {
			this.readCount = readCount;
		}
		


		public String getNickName() {
			return nickName;
		}



		public void setNickName(String nickName) {
			this.nickName = nickName;
		}



		@Override
		public String toString() {
			return "Message [messageNo=" + messageNo + ", receiverId=" + receiverId + ", senderId=" + senderId
					+ ", nickName=" + nickName + ", messageContent=" + messageContent + ", messageDate=" + messageDate
					+ ", readCount=" + readCount + "]";
		}



		
	    
	    

}
