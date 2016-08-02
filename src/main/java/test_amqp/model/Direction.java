package test_amqp.model;

public enum Direction {
    BRIGHTON("Brighton"),
    GATWICK_AIRPORT("London Gatwick"),
    LONDON_VICTORIA("London Victoria"),
    HOVE("Hove"),
    WORTHING("Worthing"),
    SHOREHAM_BY_SEA("Shoreham-by-Sea");

    String name;

    Direction(String name) {
        this.name = name;
    }

    public String getName() { return name; }

}
