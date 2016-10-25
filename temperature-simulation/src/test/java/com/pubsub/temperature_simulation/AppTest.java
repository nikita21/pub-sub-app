package com.pubsub.temperature_simulation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.BeforeClass;
import org.junit.Test;

public class AppTest {

    private static TemperatureSensor publisherPublishSubscribe,
            publisherMultipleConsumers, publisherNonDurableSubscriber;
    private static Subscriber subscriberPublishSubscribe,
            subscriber1MultipleConsumers, subscriber2MultipleConsumers,
            subscriber1NonDurableSubscriber, subscriber2NonDurableSubscriber;
    
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        publisherPublishSubscribe = new TemperatureSensor("publisher-publishsubscribe",
                "publishsubscribe.t");

        publisherMultipleConsumers = new TemperatureSensor("publisher-multipleconsumers",
                "multipleconsumers.t");

        publisherNonDurableSubscriber = new TemperatureSensor("publisher-nondurablesubscriber",
                "nondurablesubscriber.t");

        subscriberPublishSubscribe = new Subscriber("subscriber-publishsubscribe",
                "publishsubscribe.t", "ps.t");

        subscriber1MultipleConsumers = new Subscriber("subscriber1-multipleconsumers",
                "multipleconsumers.t", "mc.t");

        subscriber2MultipleConsumers = new Subscriber("subscriber2-multipleconsumers",
                "multipleconsumers.t", "mc.t");

        subscriber1NonDurableSubscriber = new Subscriber(
                "subscriber1-nondurablesubscriber", "nondurablesubscriber.t", "nd.t");

        subscriber2NonDurableSubscriber = new Subscriber(
                "subscriber2-nondurablesubscriber", "nondurablesubscriber.t", "nd.t");
    }

    @Test
    public void testMultipleConsumers() {
        executor.submit(publisherMultipleConsumers);

	executor.submit(subscriber1MultipleConsumers);

	executor.submit(subscriber1MultipleConsumers);
    }

}