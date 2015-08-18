package com.pier.util;

public class MailBean {

	private String toEmail = ConfigUtil.getString("email.receiver.name", null);
	private String subject;
	private String content;
	
	public MailBean(String subject, String content){
		this.subject = subject;
		this.content = content;
	}

	public String getToEmail() {
		return toEmail;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}
	
	public String toString(){
		StringBuilder buffer = new StringBuilder();
		buffer.append("MailBean[")
			.append("toEmail:").append(toEmail)
			.append(", subject:").append(subject)
			.append(", content:").append(content)
			.append("]");
		return buffer.toString();
	}
}
