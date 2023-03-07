package org.hsbc.Throttler;

import java.util.concurrent.Callable;

public class ThrottlerSubscriber implements Subscriber {
    private Callable<Void> action;

    public ThrottlerSubscriber(Callable<Void> action) {
        this.action = action;
    }

    @Override
    public void execute() {
        try {
            action.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
