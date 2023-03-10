package org.hsbc.EventBus;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFilteredSubscriber implements Consumer<Object> {
    private int eventCount = 0;

    @Override
    public void accept(Object event) {
        assertTrue(event instanceof TestEvent);
        eventCount++;
    }

    public int getEventCount() {
        return eventCount;
    }
}
