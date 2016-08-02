package test_amqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test_amqp.calculator.GoogleMapsDistanceCalculator;
import test_amqp.config.QueueConfig;
import test_amqp.model.Direction;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = QueueConfig.class)
public class DistanceCalculatorTest {

    @Autowired
    GoogleMapsDistanceCalculator distanceCalculator;

    /*
    The purpose of this test is to not test the google API, but to check that there is no exception thrown when a name
    of the town is spelt in  the way I've done it 
     */
    @Test
    public void test() {
        assertNotNull(distanceCalculator.calculateDistance(Direction.SHOREHAM_BY_SEA, Direction.HOVE));
        assertNotNull(distanceCalculator.calculateDistance(Direction.LONDON_VICTORIA, Direction.BRIGHTON));
        assertNotNull(distanceCalculator.calculateDistance(Direction.GATWICK_AIRPORT, Direction.WORTHING));
    }

}
