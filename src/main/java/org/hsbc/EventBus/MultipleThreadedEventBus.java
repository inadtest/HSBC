package org.hsbc.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MultipleThreadedEventBus implements EventBus {
    Map<Class<?> , Consumer<Object>> eventMap = new HashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public void publishEvent(Object o) {

    }

    @Override
    public void addSubscriber(Class<?> event, Consumer<Object> subscribe) {

    }
}
