package org.hsbc.EventBus;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiThreadedEventBusImplTest {

    @Test
    public void testAddSubscriberForEvents() throws InterruptedException {
        MultiThreadedEventBusImpl eventBus = new MultiThreadedEventBusImpl(1);
        TestSubscriber subscriber = new TestSubscriber();
        eventBus.addSubscriber(TestEvent.class, subscriber);
        eventBus.publishEvent(new TestEvent());
        Thread.sleep(100); // wait for event to be processed
        assertEquals(1, subscriber.getEventCount());
        eventBus.shutdown();
    }
    @Test
    public void testAddSubscriberForFilteredEvents() throws InterruptedException {
        MultiThreadedEventBusImpl eventBus = new MultiThreadedEventBusImpl(1);
        TestFilteredSubscriber filteredSubscriber = new TestFilteredSubscriber();
        eventBus.addSubscriberForFilteredEvents(TestEvent.class, filteredSubscriber);
        eventBus.publishEvent(new TestEvent());
        Thread.sleep(100); // wait for event to be processed
        assertEquals(1, filteredSubscriber.getEventCount());
        eventBus.shutdown();
    }

    @Test
    public void testPublishEvent() throws InterruptedException {
        MultiThreadedEventBusImpl eventBus = new MultiThreadedEventBusImpl(2);
        TestSubscriber subscriber1 = new TestSubscriber();
        TestSubscriber subscriber2 = new TestSubscriber();
        eventBus.addSubscriber(TestEvent.class, subscriber1);
        eventBus.addSubscriber(TestEvent.class, subscriber2);
        eventBus.publishEvent(new TestEvent());
        Thread.sleep(500); // wait for event to be processed
        assertEquals(1, subscriber1.getEventCount());
        assertEquals(1, subscriber2.getEventCount());
        eventBus.shutdown();
    }

    @Test
    public void testPublishLatestEvents() throws InterruptedException {
        MultiThreadedEventBusImpl eventBus = new MultiThreadedEventBusImpl(1);

        TestEvent event1 = new TestEvent("event1");
        TestEvent event2 = new TestEvent("event2");

        // Test subscriber that should only receive the latest event
        TestSubscriber subscriber = new TestSubscriber();
        eventBus.addSubscriberForLatestValueEvents(TestEvent.class, subscriber);

        // Publish two events and wait for them to be processed
        eventBus.publishLatestEvents(TestEvent.class, event1);
        eventBus.publishLatestEvents(TestEvent.class, event2);
        TimeUnit.MILLISECONDS.sleep(100);

        // Verify that the subscriber only received the latest event
        assertEquals(event2, subscriber.getLatestEvent());
    }
}
