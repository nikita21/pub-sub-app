package com.pubsub.temperature_simulation;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class Subscriber implements Runnable
{
    private static final Logger logger = Logger.getLogger(Subscriber.class);
    private String clientId;
    private String topicName;
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;
    
    public Subscriber(String clientId, String topicName)
    {
	this.clientId = clientId;
	this.topicName = topicName;
	createSubscriber();
    }
    
    private void createSubscriber()
    {
	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
	try
	{
	    connection = connectionFactory.createConnection();
	    connection.setClientID(clientId);
	    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    Topic topic = session.createTopic(topicName);
	    messageConsumer = session.createConsumer(topic);
	    connection.start();
	}
	catch (JMSException e)
	{
	    logger.error("Exception while creating consumer :" + e);
	}
    }
    
    private void closeConnection()
    {
	try
	{
	    connection.close();
	}
	catch (JMSException e)
	{
	    logger.error("Exception while closing connection :" + e);
	}
    }

    public void run()
    {
	try
	{
	    Message message = messageConsumer.receive(1000);
	    if(null != message)
	    {
		TextMessage textMessage = (TextMessage) message;
		String text = textMessage.getText();
		logger.info(clientId + " received temperature update with value : " + text);
	    }
	    else
	    {
		logger.info(clientId + " : no message received");
	    }
	}
	catch (JMSException e)
	{
	    logger.error("Exception while receiving message :", e);
	}
	finally
	{
	    closeConnection();
	}
	
    }
    
    
}

