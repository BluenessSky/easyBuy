package org.java.activemq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueLinstener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		 TextMessage textMessage=(TextMessage) message;
		 try {
			String msg=textMessage.getText();
			System.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
