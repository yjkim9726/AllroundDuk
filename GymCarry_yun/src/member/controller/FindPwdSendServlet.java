//메일 발송하는 서블렛

package member.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FindPwdServlet
 */
@WebServlet("/member/sendFindPwd")
public class FindPwdSendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FindPwdSendServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userEmail = request.getParameter("userEmail");
		String user = "official.basak@gmail.com"; // 계정
		String password = "allrounduk!"; // 비밀번호

		// SMTP 서버 정보를 저장
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.EnableSSL.enable", "true");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		int randomCode = 0;

		try {
			// 보낸시간 및 sender 생성
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));

			// 수신자 메일주소 설정
			String emails = request.getParameter("userEmail");
			InternetAddress to = new InternetAddress(emails);
			message.setRecipient(Message.RecipientType.TO, to);

			// 메일제목 설정
			message.setSubject("[GYM CARRY] 비밀번호찾기 인증번호 입니다."); // 메일 제목을 입력

			// 메일내용 설정
			randomCode = (int)Math.floor((Math.random()*(99999-10000+1)))+10000; // 5자리 랜덤 숫자코드
			String content = "비밀번호찾기 인증번호는 " + randomCode + " 입니다.";
			message.setText(content, "UTF-8");

			// send the message
			Transport.send(message); // 전송
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		request.setAttribute("checkCode", randomCode);
		request.getRequestDispatcher("/WEB-INF/views/member/findPwd.jsp").forward(request, response);
		System.out.println("message sent successfully...");

	}

	// 아이디와 패스워드 인증받는 함수
	class MyAuthentication extends Authenticator {

		PasswordAuthentication pa;

		public MyAuthentication() {
			String id = "official.basak";
			String pw = "allrounduk!";
			// ID와 비밀번호를 자동 입력함
			pa = new PasswordAuthentication(id, pw);
		}

		// 시스템에서 사용하는 인증 정보
		public PasswordAuthentication getPasswordAuthentication() {
			return pa;
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}
	
}