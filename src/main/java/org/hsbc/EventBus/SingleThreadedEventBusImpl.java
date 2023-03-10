package org.hsbc.EventBus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class SingleThreadedEventBusImpl implements EventBus {
    private Map<Class<?>, Set<Consumer<Object>>> subscribers;
    private Map<Class<?>, Set<Consumer<Object>>> filteredSubscribers;

    public SingleThreadedEventBusImpl() {
        this.subscribers = new HashMap<>();
        this.filteredSubscribers = new HashMap<>();
    }

    @Override
    public void publishEvent(Object event) {
        Class<?> eventType = event.getClass();
        Set<Consumer<Object>> eventSubscribers = subscribers.computeIfAbsent(event.getClass(), k -> new HashSet<>());//subscribers.get(eventType);
        if (eventSubscribers != null) {
            for (Consumer<Object> subscriber : eventSubscribers) {
                subscriber.accept(event);
            }
        }
        Set<Consumer<Object>> filteredSubscriberSet = filteredSubscribers.computeIfAbsent(event.getClass(), k -> new HashSet<>());
        for (Consumer<Object> filtered : filteredSubscriberSet) {
            filtered.accept(event);
        }
    }

    @Override
    public void addSubscriber(Class<?> eventType, Consumer<Object> subscriber) {
        subscribers.computeIfAbsent(eventType, key -> new HashSet<>()).add(subscriber);

    }

    @Override
    public void addSubscriberForFilteredEvents(Class<?> eventType, Consumer<Object> subscriber) {
        filteredSubscribers.computeIfAbsent(eventType, key -> new HashSet<>()).add(subscriber);
    }
}
