package org.hsbc.EventBus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleThreadedEventBusImplTest {

    @Test
    public void testAddSubscriberForFilteredEvents() {
        SingleThreadedEventBusImpl eventBus = new SingleThreadedEventBusImpl();
        TestFilteredSubscriber filteredSubscriber = new TestFilteredSubscriber();
        eventBus.addSubscriberForFilteredEvents(TestEvent.class, filteredSubscriber);
        eventBus.publishEvent(new TestEvent());
        assertEquals(1, filteredSubscriber.getEventCount());
    }

    @Test
    public void testPublishEvent() {
        SingleThreadedEventBusImpl eventBus = new SingleThreadedEventBusImpl();
        TestSubscriber subscriber1 = new TestSubscriber();
        TestSubscriber subscriber2 = new TestSubscriber();
        eventBus.addSubscriber(TestEvent.class, subscriber1);
        eventBus.addSubscriber(TestEvent.class, subscriber2);
        eventBus.publishEvent(new TestEvent());
        assertEquals(1, subscriber1.getEventCount());
        assertEquals(1, subscriber2.getEventCount());
    }
}
