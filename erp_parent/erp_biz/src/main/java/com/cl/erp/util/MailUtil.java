package com.cl.erp.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtil {

	private JavaMailSender sender;//Spring邮件发送者
	private String from;//发件人
	
	public void sendMail(String to,String subject,String text) throws MessagingException{
		//创建邮件
		MimeMessage mimeMessage = sender.createMimeMessage();
		//邮件包装工具
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setFrom(from);
		//设置主题
		helper.setSubject(subject);
		//邮件正文
		helper.setText(text);
		//设置收件人
		helper.setTo(to);
		//发送
		sender.send(mimeMessage);
		
	}
	
	public void setFrom(String from){
		this.from = from;
	}
	
	public void setSender(JavaMailSender sender){
		this.sender = sender;
	}
}
