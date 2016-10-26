package com.pubsub.temperature_simulation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App 
{
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    public static void main( String[] args )
    {
	Runtime.getRuntime().addShutdownHook(new Thread("CloseActiveMQConnection") {
	    
	    @Override
	    public void run()
	    {
		ActiveMQConnector.getInstance().closeConnection();
	    }
	});
        createPublishers("sensor-11", "BANGALORE_TEMPERATURE_1");
        createPublishers("sensor-21", "UDAIPUR_TEMPERATURE_2");
        createPublishers("sensor-31", "MUMBAI_TEMPERATURE_1");
        createPublishers("sensor-41", "GURGAON_TEMPERATURE_1");
        
        createSubscribers("subscriber-11", "BANGALORE_TEMPERATURE_1", "bang-temp_1");
        createSubscribers("subscriber-21", "BANGALORE_TEMPERATURE_1", "bang-temp_1");
        createSubscribers("subscriber-31", "UDAIPUR_TEMPERATURE_2", "udr-temp_2");
        createSubscribers("subscriber-41", "BANGALORE_TEMPERATURE_1", "bang-temp_1");
        createSubscribers("subscriber-51", "UDAIPUR_TEMPERATURE_2", "udr-temp_2");
        createSubscribers("subscriber-61", "MUMBAI_TEMPERATURE_1", "bom-temp_1");
        createSubscribers("subscriber-71", "GURGAON_TEMPERATURE_1", "gur-temp_1");
        createSubscribers("subscriber-81", "MUMBAI_TEMPERATURE_1", "bom-temp_1");
        createSubscribers("subscriber-91", "UDAIPUR_TEMPERATURE_2", "udr-temp_2");
        createSubscribers("subscriber-101", "GURGAON_TEMPERATURE_1", "gur-temp_1");
        
    }
    
    private static void createPublishers(String clientId, String topicName)
    {
	executor.submit(new TemperatureSensor(clientId, topicName));
    }
    
    private static void createSubscribers(String clientId, String topicName, String subscriptionName)
    {
	executor.submit(new Subscriber(clientId, topicName, subscriptionName));
    }
}
