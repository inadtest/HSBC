package org.hsbc.EventBus;

import java.util.function.Consumer;

public interface EventBus {
    void publishEvent(Object event);
    void addSubscriber(Class<?> eventType, Consumer<Object> subscriber);
    void addSubscriberForFilteredEvents(Class<?> eventType, Consumer<Object> subscriber);
}
