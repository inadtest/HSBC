package org.hsbc.EventBus;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSubscriber implements Consumer<Object> {
    private int eventCount = 0;
    private Object latestEvent = null;

    @Override
    public void accept(Object event) {
        assertTrue(event instanceof TestEvent);
        eventCount++;
        latestEvent = event;
    }

    public int getEventCount() {
        return eventCount;
    }

    public Object getLatestEvent() {
        return latestEvent;
    }
}
