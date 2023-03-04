package org.hsbc.EventBus;

import java.util.function.Consumer;

public interface EventBus {
    void publishEvent(Object o);

    void addSubscriber(Class<?> event, Consumer<Object> subscribe);
    // Would you allow clients to filter the events they receive? How would the interface look like?
    //void addSubscriberForFilteredEvents(????);
}
