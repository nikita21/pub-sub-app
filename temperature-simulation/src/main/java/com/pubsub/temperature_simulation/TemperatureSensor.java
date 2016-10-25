package com.pubsub.temperature_simulation;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.log4j.Logger;

public class TemperatureSensor implements Runnable
{
    private static Logger logger = Logger.getLogger(TemperatureSensor.class);

    private String clientId;
    private String topicName;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;
    private float temperature;

    public TemperatureSensor(String clientId, String topicName) {
	this.clientId = clientId;
	this.topicName = topicName;
	try
	{
	    connection = ActiveMQConnector.getInstance().getActiveMQConnection();
	    connection.setClientID(clientId);
	    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    Topic topic = session.createTopic(topicName);
	    messageProducer = session.createProducer(topic);
	    messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	    temperature = getTemperature();
	    logger.info("Temperature at location: " + topicName + " is : " + temperature);
	}
	catch (JMSException e)
	{
	    e.printStackTrace();
	}
    }

    private float getTemperature()
    {
	/* return random value between -10 and 40 */
	float randomTemperature = (float) (50.0 * Math.random() - 10.0);
	return randomTemperature;
    }
    
    private void closeConnection()
    {
	try
	{
	    connection.close();
	}
	catch (JMSException e)
	{
	    logger.error("Exception while closing connection ", e);
	}
    }

    public void run()
    {
	String tempUpdate = new StringBuilder("Temperature at Location ").append(topicName).append(" is ").append(temperature)
		.toString();
	TextMessage textMessage;
	try
	{
	    textMessage = session.createTextMessage(tempUpdate);
	    // send the message to the topic destination
	    messageProducer.send(textMessage);
	    logger.debug(clientId + ": sent message with text={}" + tempUpdate);
	}
	catch (JMSException e)
	{
	    logger.error("Exception while sending temperature update to the server ", e);
	}
	
    }

}
