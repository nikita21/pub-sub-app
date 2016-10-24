package com.pubsub.temperature_simulation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App 
{
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    public static void main( String[] args )
    {
        createPublishers("sensor-1", "BANGALORE_TEMPERATURE");
        createPublishers("sensor-2", "UDAIPUR_TEMPERATURE");
        createPublishers("sensor-3", "MUMBAI_TEMPERATURE");
        createPublishers("sensor-4", "GURGAON_TEMPERATURE");
        
        createSubscribers("subscriber-1", "BANGALORE_TEMPERATURE");
        createSubscribers("subscriber-2", "BANGALORE_TEMPERATURE");
        createSubscribers("subscriber-3", "UDAIPUR_TEMPERATURE");
        createSubscribers("subscriber-4", "BANGALORE_TEMPERATURE");
        createSubscribers("subscriber-5", "UDAIPUR_TEMPERATURE");
        createSubscribers("subscriber-6", "MUMBAI_TEMPERATURE");
        createSubscribers("subscriber-7", "GURGAON_TEMPERATURE");
        createSubscribers("subscriber-8", "MUMBAI_TEMPERATURE");
        createSubscribers("subscriber-9", "UDAIPUR_TEMPERATURE");
        createSubscribers("subscriber-10", "GURGAON_TEMPERATURE");
        
    }
    
    private static void createPublishers(String clientId, String topicName)
    {
	executor.submit(new TemperatureSensor(clientId, topicName));
    }
    
    private static void createSubscribers(String clientId, String topicName)
    {
	executor.submit(new Subscriber(clientId, topicName));
    }
}
