package org.java.test;

import java.io.File;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendMialTest {
	@Autowired
	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	//简单
	public void sendMail(){
		//创建一封邮件对象
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		simpleMailMessage.setFrom("515844706@qq.com");
		simpleMailMessage.setTo("515844706@qq.com");
		simpleMailMessage.setSubject("13411111");
		simpleMailMessage.setText("测试内容");
		mailSender.send(simpleMailMessage);
	}
	
	//简单
	public void sendMail1() throws Exception{
		//创建一封附件邮件对象
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true,"utf-8");
		messageHelper.setFrom("515844706@qq.com");
		messageHelper.setTo("515844706@qq.com");
		messageHelper.setSubject("带附件的邮件");
		messageHelper.setText("这里内容");
		FileDataSource fileDataSource=new FileDataSource(new File("D:/a.jpg"));
		messageHelper.addAttachment("在邮件文件名a.jpg",fileDataSource);
		messageHelper.addAttachment("b.jpg",new File("D:\\b.jpg"));
		mailSender.send(mimeMessage);
	}
}
