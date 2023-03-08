package org.hsbc.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class MultipleThreadedEventBusImpl implements EventBus {
    private Map<Class<?> , List<Consumer<Object>>> consumerList;
    private Map<Class<?>, BlockingQueue<Object>> eventQueues;
    private ExecutorService executor;

    public MultipleThreadedEventBusImpl() {
        consumerList = new HashMap<>();
        eventQueues = new HashMap<>();
        executor = Executors.newFixedThreadPool(5);
    }

    @Override
    public void publishEvent(Object o) {
        BlockingQueue queue = eventQueues.getOrDefault(o.getClass(), new LinkedBlockingQueue<>());
        queue.offer(o);
    }

    @Override
    public void addSubscriber(Class<?> event, Consumer<Object> subscribe) {
        List<Consumer<Object>> consumers = consumerList.putIfAbsent(event, new ArrayList<>());
        consumers.add(subscribe);
        BlockingQueue<Object> queue = eventQueues.putIfAbsent(event, new LinkedBlockingQueue<>());
        executor.execute(() -> {
            while(!queue.isEmpty()) {
                try {
                    Object o = queue.take();
                    for (Consumer<Object> consumer : consumers) {
                        consumer.accept(o);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
    }
}
