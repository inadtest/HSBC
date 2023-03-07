package org.hsbc.Throttler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ThrottlerImpl implements Throttler {
    private Timer timer;
    private AtomicInteger actionsCounter;
    private final int maxRequests;
    private List<Consumer<Void>> subscribers;

    public ThrottlerImpl(int period, int maxRequests) {
        this.maxRequests = maxRequests;
        this.timer = new Timer();
        this.actionsCounter = new AtomicInteger(0);
        this.subscribers = new ArrayList<>();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (this) {
                    actionsCounter.set(0);
                    for (Consumer<Void> subscriber : subscribers) {
                        if (shouldProceed() == ThrottleResult.PROCEED) {
                            subscriber.accept(null);
                            increaseActionsCount();
                        }
                    }
                }
            }
        }, 0, period);
    }

    @Override
    public ThrottleResult shouldProceed() {
        return actionsCounter.get() < maxRequests ? ThrottleResult.PROCEED : ThrottleResult.DO_NOT_PROCEED;
    }

    @Override
    public void notifyWhenCanProceed(Consumer<Void> subscriber) {
        subscribers.add(subscriber);
    }


    public void increaseActionsCount() {
        actionsCounter.incrementAndGet();
    }
}
