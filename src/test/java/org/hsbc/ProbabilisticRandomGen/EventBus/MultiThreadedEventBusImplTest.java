package org.hsbc.ProbabilisticRandomGen.EventBus;

import io.vavr.collection.List;
import org.hsbc.EventBus.MultiThreadedEventBusImpl;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiThreadedEventBusImplTest {
    @Test
    void testSingleSubscriberReceivesEvent() throws InterruptedException {
        MultiThreadedEventBusImpl eventBus = new MultiThreadedEventBusImpl(1);
        AtomicInteger counter = new AtomicInteger(0);
        Consumer<String> consumer = s -> {counter.incrementAndGet();
                                        assertEquals("test", s);};
        eventBus.addSubscriber(String.class, consumer);
        eventBus.publishEvent("test");
        System.out.println(counter.get());
        Thread.sleep(100);
        assertEquals(1, counter.get());
    }

    @Test
    void testMultipleSubscribersReceiveEvents() throws InterruptedException {
        MultiThreadedEventBusImpl eventBus = new MultiThreadedEventBusImpl(2);
        AtomicInteger counter1 = new AtomicInteger(0);
        AtomicInteger counter2 = new AtomicInteger(0);

        eventBus.addSubscriber(String.class, (Consumer<String>) s -> {
            counter1.incrementAndGet();
            assertEquals("test", s);
        });

        eventBus.addSubscriber(String.class, (Consumer<String>) s -> {
            counter2.incrementAndGet();
            assertEquals("test", s);
        });

        eventBus.publishEvent("test");
        Thread.sleep(1000);
        assertEquals(1, counter1.get());
        assertEquals(1, counter2.get());
    }

    @Test
    void testSingleSubscriberReceivesEvent1() throws InterruptedException {
        MultiThreadedEventBusImpl eventBus = new MultiThreadedEventBusImpl(1);
        AtomicInteger counter = new AtomicInteger(0);
        Semaphore semaphore = new Semaphore(0);
        Consumer<String> consumer = s -> {
            counter.incrementAndGet();
            assertEquals("test", s);
            semaphore.release();
        };
        eventBus.addSubscriber(String.class, consumer);
        eventBus.publishEvent("test");
        semaphore.acquire();
        assertEquals(1, counter.get());
    }



}
