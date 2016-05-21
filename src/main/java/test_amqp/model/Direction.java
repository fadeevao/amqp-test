package test_amqp.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum Direction {
    BRIGHTON,
    GATWICK_AIRPORT,
    LONDON_VICTORIA,
    HOVE,
    WORTHING,
    SHOREHAM_BY_SEA;

    public Map<Direction, BigDecimal> reachableDirections = new HashMap<Direction, BigDecimal>();

    public boolean visited;

    static {

        BRIGHTON.reachableDirections.put(GATWICK_AIRPORT, new BigDecimal(100));
        GATWICK_AIRPORT.reachableDirections.put(BRIGHTON, new BigDecimal(100));

        BRIGHTON.reachableDirections.put(HOVE, new BigDecimal(10));
        HOVE.reachableDirections.put(BRIGHTON, new BigDecimal(10));

        HOVE.reachableDirections.put(SHOREHAM_BY_SEA, new BigDecimal(12));
        SHOREHAM_BY_SEA.reachableDirections.put(HOVE, new BigDecimal(12));

        SHOREHAM_BY_SEA.reachableDirections.put(WORTHING, new BigDecimal(18));
        WORTHING.reachableDirections.put(SHOREHAM_BY_SEA, new BigDecimal(18));

        GATWICK_AIRPORT.reachableDirections.put(LONDON_VICTORIA, new BigDecimal(200));
        LONDON_VICTORIA.reachableDirections.put(GATWICK_AIRPORT, new BigDecimal(200));
    }
}
