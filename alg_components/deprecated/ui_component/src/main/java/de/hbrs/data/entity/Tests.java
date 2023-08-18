package de.hbrs.data.entity;

public enum Tests {

    TEST_OIL(1, "Oil System"),
    TEST_FUEL(2, "Fuel System"),
    TEST_MONITORING(3, "Monitoring/Control System"),
    TEST_GEARBOX(4, "Gearbox Options");

    private int id;
    private String description;
    private State state = State.NOT_STARTED;

    Tests(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getTestID() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

}
