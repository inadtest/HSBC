package org.hsbc.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SingleThreadEventBusImpl implements EventBus {
    // event - subscriber map. Each event can have multiple consumers
    private Map<Class<?> , List<Consumer<Object>>> consumerList;

    public SingleThreadEventBusImpl() {
        consumerList = new HashMap<>();
    }

    @Override
    public void addSubscriber(Class<?> event, Consumer<Object> subscriber) {
        consumerList.putIfAbsent(event, new ArrayList<>());
        consumerList.get(event).add(subscriber);
    }

    @Override
    public void publishEvent(Object o) {
        List<Consumer<Object>> consumers = consumerList.getOrDefault(o.getClass(), new ArrayList<>());
        for(Consumer<Object> consumer : consumers) {
            consumer.accept(o);
        }
    }
}
