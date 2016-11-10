package hyweb.gip.mail;


import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MemberMail {
	public void sendPwd(String mail, String password, String loginType, String name) throws MessagingException, IOException {
		ClassPathXmlApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("spring_mail.xml");
			MailDataSender mailSender = (MailDataSender) context.getBean("memberMailPwd");
			
			String sendName = "";
			if("".equals(name) || name == null){
				sendName = mail;
			}else{
				sendName = name;
			}
			
			Calendar cal = Calendar.getInstance();
			
			mailSender.addTo(mail, sendName);
					
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("mail", mail);
			model.put("year", cal.get(Calendar.YEAR));
			model.put("month", cal.get(Calendar.MONTH)+1);
			model.put("day", cal.get(Calendar.DAY_OF_MONTH));
			model.put("time", this.addZero(cal.get(Calendar.HOUR_OF_DAY)) + ":" + this.addZero(cal.get(Calendar.MINUTE)));
			if("0".equals(loginType)){  //網站
				model.put("content", "<p style='text-align:left;'>系統自動重設之登入密碼： <b class='HL'>"+ password +"</b></p>");
				model.put("content2", "<p><b class='HL'>請使用此重設密碼立即登入網站，並於「會員中心 &gt;&gt; 會員註冊資訊」進行密碼修改</b>。<br/>");
			}else if("1".equals(loginType)){  //google
				model.put("content", "<p style='text-align:left;'>您使用Google帳號註冊今周刊會員，請透過Google帳號進行會員登入</p>");
				model.put("content2", "<p><b class='HL'>使用Facebook或Google帳號註冊今周刊會員後，只能使用同一種方式登入會員。請在網站點選使用Facebook或Google帳號登入即可</b>。<br/>");
			}else if("2".equals(loginType)){  //facebook
				model.put("content", "<p style='text-align:left;'>您使用Facebook帳號註冊今周刊會員，請透過Facebook帳號進行會員登入</p>");
				model.put("content2", "<p><b class='HL'>使用Facebook或Google帳號註冊今周刊會員後，只能使用同一種方式登入會員。請在網站點選使用Facebook或Google帳號登入即可</b>。<br/>");
			}else{
				model.put("content", "<p style='text-align:left;'>您可能是使用Facebook或Google帳號註冊今周刊會員，請透過Facebook或Google帳號進行會員登入。</p>");
				model.put("content2", "<p><b class='HL'>使用Facebook或Google帳號註冊今周刊會員後，只能使用同一種方式登入會員。請在網站點選使用Facebook或Google帳號登入即可</b>。<br/>");
			}
			mailSender.setModel(model);
			
			mailSender.send();
		} finally {
			if (context != null) {
				context.close();
			}
		}
		
	}
	
	public void sendCustNo(String mail, String custNo, String name) throws MessagingException, IOException {
		ClassPathXmlApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("spring_mail.xml");
			MailDataSender mailSender = (MailDataSender) context.getBean("memberMailCustNo");
			
			String sendName = "";
			if("".equals(name) || name == null){
				sendName = mail;
			}else{
				sendName = name;
			}
			
			Calendar cal = Calendar.getInstance();
			
			mailSender.addTo(mail, sendName);
					
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("mail", mail); 
			model.put("custno", custNo);
			model.put("year", cal.get(Calendar.YEAR));
			model.put("month", cal.get(Calendar.MONTH)+1);
			model.put("day", cal.get(Calendar.DAY_OF_MONTH));
			model.put("time", this.addZero(cal.get(Calendar.HOUR_OF_DAY)) + ":" + this.addZero(cal.get(Calendar.MINUTE)));
			model.put("name", name);
			
			mailSender.setModel(model);
			
			mailSender.send();
		} finally {
			if (context != null) {
				context.close();
			}
		}
	}
	
	public String addZero(int number){ //數字小於10補0  
        return number < 10 ? "0" + number : "" + number;  
    } 
}
