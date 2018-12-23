package org.java.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TopicTest {
	@Test
	public void testTopiProducer() throws Exception{
		//1.创建ConnectionFactory对象
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");
		//2.创建Connection对象
		Connection connection = connectionFactory.createConnection();
		//3.开启连接
		connection.start();
		//4.创建session对象(transacted是否开启事务，acknowledgeMode应答机制)
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//5.创建Destination对象
		//Queue queue = session.createQueue("test-queue");
		Topic topic = session.createTopic("test-topci");
		//6.通过session对象创建Producer对象
		MessageProducer producer = session.createProducer(topic);
		//7.创建message对象
		TextMessage message=session.createTextMessage("hello mq topci");
		//8.通过producer对象发送消息
		producer.send(message);
		//9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	@Test
	public void testTopiConusmar() throws Exception{
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.131:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topci");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage=(TextMessage) message;
				try {
					String text = textMessage.getText();
					System.out.println("topci"+text);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("topci的消费。。。。");
		//等待输入
		System.in.read();
	}
	/**
	 * String整合
	 * @throws Exception
	 */
	@Test
	public void testTopicProducerSpring() throws Exception{
		//1.读取spring的配置文件
		ApplicationContext context = new
				ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
		Topic topic = (Topic) context.getBean("test-topic");
		jmsTemplate.send(topic, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage("topic-spring-test");
				return textMessage;
			}
		});
	}
	@Test
	public void testTopicConusmarSpring() throws Exception{
		//1.读取spring的配置文件
		ApplicationContext context = new
				ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		System.out.println("topic listener2");
		System.in.read();
	}
}
