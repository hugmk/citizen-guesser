package org.citizenGuesser.model;

public class Hello {
    private String name;
    private long moment;

    public Hello(String name, long moment) {
        this.name = name;
        this.moment = moment;
    }

    public String getName() {
        return name;
    }

    public long getMoment() {
        return moment;
    }
}
