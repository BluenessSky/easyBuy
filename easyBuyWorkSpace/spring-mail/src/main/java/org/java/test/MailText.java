package org.java.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MailText {
	public static void main(String[] args) throws Exception {
		ApplicationContext app = new 
				ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");
		//获取到SendMialTest实例
		SendMialTest test=(SendMialTest) app.getBean("mailTest");
		/*test.sendMail();*/
		test.sendMail1();
	}
}
