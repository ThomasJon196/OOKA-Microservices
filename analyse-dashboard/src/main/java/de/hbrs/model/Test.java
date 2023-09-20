package de.hbrs.model;

public enum Test {

    OIL(1, "Oil System", "Ölpumpe mit variabler Geschwindigkeit", "Zentrifugalölreiniger", "Ölstandssensor und -überwachung", "Wärmetauscher für Ölkühlung"),
    FUEL(2, "Fuel System", "Dieselkraftstofftank", "LNG-Tank", "Brennstoffzellenantrieb", "Bunkertank für Schweröl"),
    GEARBOX(3, "Gearbox", "Automatisches Getriebe", "Hydrodynamisches Getriebe", "Planetengetriebe", "Zweiwellengetriebe"),
    ENGINE(4, "Engine", "Dieselmotor", "Gasmotor", "Elektromotor", "Hybridmotor"),
    STARTING_SYSTEM(5, "Starting System", "Elektrischer Anlasser", "Druckluftstarter", "Handkurbelstarter", "Hydraulischer Anlasser"),
    TEST_MONITORING(6, "Monitoring: Control System", "Motorleistung und Temperatur", "Kraftstoffverbrauch und Tankstand", "Bordnetzspannung und Strom", "Brand- und Rauchdetektoren"),
    TEST_GEARBOX(7, "Monitoring: Gearbox", "Temperatur", "Geschwindigkeit/Drehzahl", "Ölstand", "Kupplungen und Schaltungen");

    private int id;
    private String description;
    private String[] configurations;

    Test(int id, String description, String... configurationOptions) {
        this.id = id;
        this.description = description;
        this.configurations = configurationOptions;
    }

    public int getTestID() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getConfigurations() {
        return configurations;
    }

    public static Test getByName(String name) {
        for (Test test : Test.values()) {
            if (test.name().equalsIgnoreCase(name)) {
                return test;
            }
        }
        return null;
    }

}
