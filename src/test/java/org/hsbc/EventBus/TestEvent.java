package org.hsbc.EventBus;

public class TestEvent {
    private final String name;

    public TestEvent() {
        name = null;
    }
    public TestEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
