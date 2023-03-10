package org.hsbc.Throttler;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThrottlerImplTest {
    private static final int TIME = 1000;
    private static final int MAX_NUM_OF_REQUESTS = 2;

    @Test
    public void testThrottling() throws InterruptedException {
        // number of executions
        final AtomicInteger executionCount = new AtomicInteger(0);

        ThrottlerImpl throttler = new ThrottlerImpl(TIME, MAX_NUM_OF_REQUESTS);
        for (int i = 0; i < MAX_NUM_OF_REQUESTS; i++) {
            throttler.notifyWhenCanProceed(x -> executionCount.incrementAndGet());
        }

        while (executionCount.get() < MAX_NUM_OF_REQUESTS)
            Thread.sleep(100);

        assertEquals(executionCount.get(), MAX_NUM_OF_REQUESTS);
    }

    @Test
    void shouldProceed() throws InterruptedException {
        Throttler throttler = new ThrottlerImpl(1000, 5);
        // call throttler MAX_NUM_OF_REQUESTS
        for (int i = 0; i < MAX_NUM_OF_REQUESTS; i++) {
            assertEquals(Throttler.ThrottleResult.PROCEED, throttler.shouldProceed());
        }
        // Schedule a thread to run 4 secs later, with requests more than the max_requests, then it should not proceed
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            assertEquals(Throttler.ThrottleResult.DO_NOT_PROCEED, throttler.shouldProceed());
        }, MAX_NUM_OF_REQUESTS+2, TimeUnit.SECONDS);
        scheduler.awaitTermination(5, TimeUnit.SECONDS);
    }
}
