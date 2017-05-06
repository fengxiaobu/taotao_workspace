package com.test.page;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TestMQ {

	
	public void testSendActiveMQ() throws Exception {
		// 第一步创建一个连接工程ConnectionFactory对象,指定IP和port
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		// 第二步 使用ConnectionFActory对象创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		// 第三步：开启连接，调用Connection对象的start方法。
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		// 第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
		// 第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 使用Session对象创建一个Producer
		// 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
		// 参数：队列的名称。

		Queue queue = session.createQueue("test-queue");
		// 第六步：使用Session对象创建一个Producer对象。
		MessageProducer messageProducer = session.createProducer(queue);
		// 第七步：创建一个Message对象，创建一个TextMessage对象
		TextMessage textMessage = session.createTextMessage("测试消息1315665<h1>123</h2>");
		// 第八步：使用Producer对象发送消息。
		messageProducer.send(textMessage);
		// 关闭资源
		messageProducer.close();
		session.close();
		connection.close();
	}

	
	public void getMessage() throws Exception {
		// 创建一个连接工程ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		// 创建一个连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Queue queue = session.createQueue("test-queue");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
					String text = null;
					// 取消息的内容
					text = textMessage.getText();
					// 第八步：打印消息。
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}

			}
		});
		// 等待键盘输入
		System.in.read();
		// 第九步：关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	
	public void testTopicSend() throws Exception {
		// 第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		// 第二步：使用ConnectionFactory对象创建一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		// 第三步：开启连接，调用Connection对象的start方法。
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Topic对象。
		Topic topic = session.createTopic("test-topic");
		// 第六步：使用Session对象创建一个Producer对象。
		MessageProducer messageProducer = session.createProducer(topic);
		// 第七步：创建一个Message对象，创建一个TextMessage对象。
		Message message = session.createTextMessage("呵呵");
		// 第八步：使用Producer对象发送消息。
		messageProducer.send(message);
		// 第九步：关闭资源。
		messageProducer.close();
		session.close();
		connection.close();

	}

	
	public void testTopic() throws Exception {
		// 消费者：接收消息。
		// 第一步：创建一个ConnectionFactory对象。
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		// 第二步：从ConnectionFactory对象中获得一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		// 第三步：开启连接。调用Connection对象的start方法。
		connection.start();
		// 第四步：使用Connection对象创建一个Session对象。
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 第五步：使用Session对象创建一个Destination对象。和发送端保持一致topic，并且话题的名称一致。
		Topic topic = session.createTopic("test-topic");
		// 第六步：使用Session对象创建一个Consumer对象。
		MessageConsumer messageConsumer = session.createConsumer(topic);
		// 第七步：接收消息。
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;

					String text = textMessage.getText();
					// 第八步：打印消息。
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}

			}
		});
		System.in.read();

		// 第九步：关闭资源
		messageConsumer.close();
		session.close();
		connection.close();

	}
}
