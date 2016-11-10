package hyweb.gip.mail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailDataSender {
	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private final Logger log = LoggerFactory.getLogger(MailDataSender.class);
	
	private boolean sendMail;
	
	private boolean filter;
	
	private String encoding;
	
	private JavaMailSender mailSender;
	
	private String fromName;
	private String fromAddress;
	
	private String subjectTemplate;
	private String contentTemplate;
	
	private Map<String, Object> model;
	
	private String subject;
	private String content;
	private List<InternetAddress> toList;
	private List<InternetAddress> ccList;
	private List<InternetAddress> bccList;
	private List<MailFile> mailFiles;
	
	public boolean isSendMail() {
		return sendMail;
	}

	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}

	public boolean isFilter() {
		return filter;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	protected JavaMailSender getMailSender() {
		return mailSender;
	}

	public String getFromName() {
		return fromName;
	}
	
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromAddress() {
		return fromAddress;
	}
	
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	
	public String getSubjectTemplate() {
		return subjectTemplate;
	}

	public void setSubjectTemplate(String subjectTemplate) {
		this.subjectTemplate = subjectTemplate;
	}
	
	public String getContentTemplate() {
		return contentTemplate;
	}

	public void setContentTemplate(String contentTemplate) {
		this.contentTemplate = contentTemplate;
	}
	
	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	public void addAttribute(String key, Object value) {
		if (model == null) {
			model = new HashMap<String, Object>();
		}
		model.put(key, value);
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<InternetAddress> getToList() {
		return toList;
	}
	public void setToList(List<InternetAddress> toList) {
		this.toList = toList;
	}
	public void setTo(InternetAddress addr) {
		this.toList = new ArrayList();
		this.toList.add(addr);
	}
	public void setTo(String mail) throws AddressException {
		this.toList = new ArrayList();
		InternetAddress addr = new InternetAddress(mail);
		toList.add(addr);
	}
	public void setTo(String mail, String personal) throws UnsupportedEncodingException {
		this.toList = new ArrayList();
		InternetAddress addr = new InternetAddress(mail, personal);
		toList.add(addr);
	}
	public void addTo(InternetAddress addr) {
		if (toList == null) {
			toList = new ArrayList();
		}
		toList.add(addr);
	}
	public void addTo(String mail) throws AddressException {
		if (toList == null) {
			toList = new ArrayList();
		}
		InternetAddress addr = new InternetAddress(mail);
		toList.add(addr);
	}
	public void addTo(String mail, String personal) throws AddressException, UnsupportedEncodingException {
		if (toList == null) {
			toList = new ArrayList();
		}
		InternetAddress addr = new InternetAddress(mail, personal);
		toList.add(addr);
	}
	public List<InternetAddress> getCcList() {
		return ccList;
	}
	public void setCcList(List<InternetAddress> ccList) {
		this.ccList = ccList;
	}
	public void setCc(InternetAddress addr) {
		ccList = new ArrayList();
		this.ccList.add(addr);
	}
	public void setCc(String mail) throws AddressException {
		ccList = new ArrayList();
		InternetAddress addr = new InternetAddress(mail);
		ccList.add(addr);
	}
	public void setCc(String mail, String personal) throws UnsupportedEncodingException {
		ccList = new ArrayList();
		InternetAddress addr = new InternetAddress(mail, personal);
		ccList.add(addr);
	}
	public void addCc(InternetAddress addr) {
		if (ccList == null) {
			ccList = new ArrayList();
		}
		ccList.add(addr);
	}
	public void addCc(String mail) throws AddressException {
		if (ccList == null) {
			ccList = new ArrayList();
		}
		InternetAddress addr = new InternetAddress(mail);
		ccList.add(addr);
	}
	public void addCc(String mail, String personal) throws AddressException, UnsupportedEncodingException {
		if (ccList == null) {
			ccList = new ArrayList();
		}
		InternetAddress addr = new InternetAddress(mail, personal);
		ccList.add(addr);
	}
	public List<InternetAddress> getBccList() {
		return bccList;
	}
	public void setBccList(List<InternetAddress> bccList) {
		this.bccList = bccList;
	}
	public void setBCc(InternetAddress addr) {
		bccList = new ArrayList();
		this.bccList.add(addr);
	}
	public void setBCc(String mail) throws AddressException {
		bccList = new ArrayList();
		InternetAddress addr = new InternetAddress(mail);
		bccList.add(addr);
	}
	public void setBCc(String mail, String personal) throws UnsupportedEncodingException {
		bccList = new ArrayList();
		InternetAddress addr = new InternetAddress(mail, personal);
		bccList.add(addr);
	}
	public void addBcc(InternetAddress addr) {
		if (bccList == null) {
			bccList = new ArrayList();
		}
		bccList.add(addr);
	}
	public void addBCc(String mail) throws AddressException {
		if (bccList == null) {
			bccList = new ArrayList();
		}
		InternetAddress addr = new InternetAddress(mail);
		bccList.add(addr);
	}
	public void addBCc(String mail, String personal) throws AddressException, UnsupportedEncodingException {
		if (bccList == null) {
			bccList = new ArrayList();
		}
		InternetAddress addr = new InternetAddress(mail, personal);
		bccList.add(addr);
	}
	public List<MailFile> getMailFiles() {
		return mailFiles;
	}
	public void setMailFiles(List<MailFile> mailFiles) {
		this.mailFiles = mailFiles;
	}
	public void addMailFile(MailFile mailFile) {
		if (mailFiles == null) {
			mailFiles = new ArrayList();
		}
		mailFiles.add(mailFile);
	}
	public void addMailFile(String fileName, File file) {
		if (mailFiles == null) {
			mailFiles = new ArrayList();
		}
		MailFile mailFile = new MailFile();
		mailFile.setFileName(fileName);
		mailFile.setFile(file);
		mailFiles.add(mailFile);
	}
	public void addMailFile(File file) {
		if (mailFiles == null) {
			mailFiles = new ArrayList();
		}
		MailFile mailFile = new MailFile();
		mailFile.setFileName(file.getName());
		mailFile.setFile(file);
		mailFiles.add(mailFile);
	}

	public void send() throws MessagingException, IOException {
		try {
			String subject = (this.subject != null ? this.subject : replaceData(this.subjectTemplate));
			String content = (this.content != null ? this.content : replaceData(this.contentTemplate));
			
			List<InternetAddress> toAddress = getToList();
			List<InternetAddress> ccAddress = getCcList();
			List<InternetAddress> bccAddress = getBccList();
			List<MailFile> listFile = getMailFiles();
			
			log.info("[SendMail] -----------------start--------------------- ");
			MimeMessage mailMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, encoding);
	
			log.info("[SUBJECT] subject = {}", subject);
			messageHelper.setSubject(subject);
			
			log.info("[Content] content = {}", content);
	        messageHelper.setText(content, true);
			
			InternetAddress fromAddr = getFrom();
			
			log.info("[FROM] addr = {}, name = {}", fromAddr.getAddress(), fromAddr.getPersonal());
			messageHelper.setFrom(fromAddr);
			
			if (toAddress != null) {
				for (InternetAddress addr : toAddress) {
					log.info("[TO] addr = {}, name = {}", addr.getAddress(), addr.getPersonal());
					if (this.isFilter()) {
						if (addr.getAddress().matches(EMAIL_REGEX) == false) {
							log.error("[TO][Error] addr = {}, name = {}", addr.getAddress(), addr.getPersonal());
							continue;
						}
					}
					if (addr.getPersonal() != null) {
						addr.setPersonal(addr.getPersonal(), encoding);
					}
					messageHelper.addTo(addr);
				}
			}
			
			if (ccAddress != null) {
				for (InternetAddress addr : ccAddress) {
					log.info("[CC] addr = {}, name = {}", addr.getAddress(), addr.getPersonal());
					if (this.isFilter()) {
						if (addr.getAddress().matches(EMAIL_REGEX) == false) {
							log.error("[CC][Error] addr = {}, name = {}", addr.getAddress(), addr.getPersonal());
							continue;
						}
					}
					if (addr.getPersonal() != null) {
						addr.setPersonal(addr.getPersonal(), encoding);
					}
					messageHelper.addCc(addr);
				}
			}
			
			if (bccAddress != null) {
				for (InternetAddress addr : bccAddress) {
					log.info("[BCC] addr = {}, name = {}", addr.getAddress(), addr.getPersonal());
					if (this.isFilter()) {
						if (addr.getAddress().matches(EMAIL_REGEX) == false) {
							log.error("[BCC][Error] addr = {}, name = {}", addr.getAddress(), addr.getPersonal());
							continue;
						}
					}
					if (addr.getPersonal() != null) {
						addr.setPersonal(addr.getPersonal(), encoding);
					}
					messageHelper.addBcc(addr);
				}
			}
	
	        if (listFile != null) {
		        FileSystemResource fileRes = null;
		        for (MailFile file : listFile) {
		        	log.info("[FILE] filename = {}", file.getFileName());
		        	fileRes = new FileSystemResource(file.getFile());
		        	messageHelper.addAttachment(MimeUtility.encodeWord(file.getFileName(), encoding, null), fileRes); 
		        }
	        }
	        
	        if (isSendMail()) {
	        	mailSender.send(mailMessage);
	        	log.info("[SendMail] Success");
	        } else {
	        	log.info("[SendMail] No Send");
	        }
			
			log.info("[SendMail] -----------------end--------------------- ");
		} finally {
			setToList(null);
			setCcList(null);
			setBccList(null);
		}
	}
	
	protected InternetAddress getFrom() throws UnsupportedEncodingException {
		InternetAddress from = new InternetAddress(fromAddress, fromName, encoding);
		return from;
	}
	
	private String replaceData(String templateStr) throws IOException {
		CompiledTemplate template = TemplateCompiler.compileTemplate(templateStr);
		
		String context = (String) TemplateRuntime.execute(template, this.model);
		return context;
	}
}
