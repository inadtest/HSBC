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

public class MultiThreadedEventBusImpl implements EventBus {
    private Map<Class<?> , List<Consumer<?>>> consumerList;
    private Map<Class<?>, BlockingQueue<Object>> eventQueues;
    private ExecutorService executor;

    public MultiThreadedEventBusImpl(int noOfThreads) {
        consumerList = new HashMap<>();
        eventQueues = new HashMap<>();
        executor = Executors.newFixedThreadPool(noOfThreads);
    }

    @Override
    public void publishEvent(Object o) {
        BlockingQueue<Object> queue = eventQueues.getOrDefault(o.getClass(), new LinkedBlockingQueue<>());
        queue.offer(o);
    }

    @Override
    public void addSubscriber(Class<?> event, Consumer<?> subscribe) {
        List<Consumer<?>> consumers = consumerList.computeIfAbsent(event,l -> new ArrayList<>());
        consumers.add(subscribe);
        BlockingQueue<Object> queue = eventQueues.computeIfAbsent(event, q -> new LinkedBlockingQueue<>());
        executor.execute(() -> {
            while (true) {
                try {
                    Object obj = queue.take();
                    for (Consumer<?> consumer : consumers) {
                        Consumer<Object> consumerObj = (Consumer<Object>) consumer;
                        consumerObj.accept(obj);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
}
