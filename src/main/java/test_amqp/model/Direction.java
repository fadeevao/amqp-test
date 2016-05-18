package test_amqp.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by olga on 18/05/16.
 */
public enum Direction {
    BRIGHTON,
    GATWICK_AIRPORT,
    LONDON_VICTORIA,
    BEDFORD,
    HOVE,
    WORTHING,
    SHOREHAM_BY_SEA;

    public Set<Direction> reachableDirections = new HashSet<Direction>();

    static {
        BRIGHTON.reachableDirections.addAll(Arrays.asList(GATWICK_AIRPORT, LONDON_VICTORIA, BEDFORD, HOVE,
                WORTHING, SHOREHAM_BY_SEA));
        SHOREHAM_BY_SEA.reachableDirections.addAll(Arrays.asList(WORTHING, BRIGHTON));
        WORTHING.reachableDirections.addAll(Arrays.asList(SHOREHAM_BY_SEA, BRIGHTON));
    }
}
