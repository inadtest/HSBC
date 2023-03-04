package org.hsbc.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventBusImpl implements EventBus {
    // Keep a map of event and subscribers
    Map<Class<?> , Consumer<Object>> eventMap = new HashMap<>();

    @Override
    public void addSubscriber(Class<?> event, Consumer<Object> subscriber) {
        eventMap.put(event, subscriber);
    }

    @Override
    public void publishEvent(Object o) {
        eventMap.get(o);
        eventMap.get(o).accept(o);

    }
}
