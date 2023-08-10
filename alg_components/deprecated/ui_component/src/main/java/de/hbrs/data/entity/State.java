package de.hbrs.data.entity;

public enum State {

    RUNNING ("Running", "primary"),
    FAILED ("Failed", "error"),
    READY ("Ready", "success"),
    NOT_STARTED("Not started", "contrast");

    private final String description;
    private final String css_style;

    State(String description, String css_style) {
        this.description = description;
        this.css_style = css_style;
    }

    public String toString() {
        return this.description;
    }

    public String getCssStyle() {
        return this.css_style;
    }

}
