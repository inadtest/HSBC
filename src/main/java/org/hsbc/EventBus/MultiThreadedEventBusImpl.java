package org.hsbc.EventBus;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class MultiThreadedEventBusImpl implements MultiThreadedEventBus {
    private final Map<Class<?>, Set<Consumer<Object>>> subscribers;
    private final Map<Class<?>, Set<Consumer<Object>>> filteredSubscribers;
    private final Map<Class<?>, BlockingQueue<Object>> eventQueues;
    private final ExecutorService executorService;
    private final Map<Class<?>, Object> latestEventValues;
    private final Map<Class<?>, Set<Consumer<Object>>> latestValueSubscribers;

    public MultiThreadedEventBusImpl(int noOfThreads) {
        this.subscribers = new ConcurrentHashMap<>();
        this.filteredSubscribers = new ConcurrentHashMap<>();
        this.latestEventValues = new ConcurrentHashMap<>();
        this.latestValueSubscribers = new ConcurrentHashMap<>();
        this.eventQueues = new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(noOfThreads);
    }

    @Override
    public void publishEvent(Object event) {
        BlockingQueue<Object> eventQueue = eventQueues.computeIfAbsent(event.getClass(), k -> new LinkedBlockingQueue<>());
        eventQueue.offer(event);
        executorService.execute(() -> {
            while(!eventQueue.isEmpty()) {
                Object eventFromQueue = eventQueue.poll();
                if (eventFromQueue != null) {
                    Set<Consumer<Object>> eventSubscribers = subscribers.computeIfAbsent(event.getClass(), k -> new HashSet<>());
                    for (Consumer<Object> subscriber : eventSubscribers) {
                        subscriber.accept(eventFromQueue);
                    }
                    Set<Consumer<Object>> filteredSubscriber = filteredSubscribers.computeIfAbsent(event.getClass(), k -> new HashSet<>());
                    for (Consumer<Object> filtered : filteredSubscriber) {
                        filtered.accept(eventFromQueue);
                    }
                }
            }
        });
    }

    @Override
    public void addSubscriber(Class<?> eventType, Consumer<Object> subscriber) {
        subscribers.computeIfAbsent(eventType, key -> new CopyOnWriteArraySet<>()).add(subscriber);
    }

    @Override
    public void addSubscriberForFilteredEvents(Class<?> eventType, Consumer<Object> subscriber) {
        filteredSubscribers.computeIfAbsent(eventType, key -> new CopyOnWriteArraySet<>()).add(subscriber);
    }

    @Override
    public void publishLatestEvents(Class<?> eventType, Object event) {
        BlockingQueue<Object> eventQueue = eventQueues.computeIfAbsent(eventType, k -> new ArrayBlockingQueue<>(1));
        eventQueue.clear(); // remove old events
        eventQueue.offer(event); // add the new event to the queue
        latestEventValues.put(eventType, event); // update the latest
        // notify subscribers with the latest value of the event type
        executorService.execute(() -> {
            Set<Consumer<Object>> subscribers = latestValueSubscribers.computeIfAbsent(event.getClass(), k -> new HashSet<>());
            for (Consumer<Object> subscriber : subscribers) {
                subscriber.accept(event);
            }
        });
    }

    @Override
    public void addSubscriberForLatestValueEvents(Class<?> eventType, Consumer<Object> subscriber) {
        latestValueSubscribers.computeIfAbsent(eventType, key -> new CopyOnWriteArraySet<>()).add(subscriber);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
