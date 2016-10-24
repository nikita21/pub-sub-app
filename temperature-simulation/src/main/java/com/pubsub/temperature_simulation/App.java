package com.pubsub.temperature_simulation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App 
{
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    public static void main( String[] args )
    {
        executor.submit(new TemperatureSensor("sensor-1", "bangalore-temperature"));
        
    }
}
