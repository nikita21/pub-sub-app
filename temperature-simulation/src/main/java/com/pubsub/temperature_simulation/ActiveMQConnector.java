package com.pubsub.temperature_simulation;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class ActiveMQConnector
{
    private static final Logger logger = Logger.getLogger(ActiveMQConnector.class);
    private static ActiveMQConnector _connectionInstance = new ActiveMQConnector();
    private Connection connection;
    
    private ActiveMQConnector()
    {
	getActiveMQConnection();
    }
    
    public static ActiveMQConnector getInstance()
    {
	return _connectionInstance;
    }
    
    public Connection getActiveMQConnection()
    {
	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
	try
	{
	    connection = connectionFactory.createConnection();
	}
	catch (JMSException e)
	{
	    logger.error("Exception while connecting to ActiveMQ :", e);
	}
	return connection;
    }
    
    public void closeConnection()
    {
	try
	{
	    logger.debug("Closing activemq connection....");
	    connection.close();
	}
	catch (JMSException e)
	{
	    logger.error("Exception while closing connection ", e);
	}
    }
}
