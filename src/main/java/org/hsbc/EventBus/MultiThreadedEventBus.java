package org.hsbc.EventBus;

import java.util.function.Consumer;

public interface MultiThreadedEventBus extends EventBus {
    void publishEvent(Object event);
    void publishLatestEvents(Class<?> eventType, Object event);
    void addSubscriber(Class<?> eventType, Consumer<Object> subscriber);
    void addSubscriberForFilteredEvents(Class<?> eventType, Consumer<Object> subscriber);
    void addSubscriberForLatestValueEvents(Class<?> eventType, Consumer<Object> subscriber);
}
