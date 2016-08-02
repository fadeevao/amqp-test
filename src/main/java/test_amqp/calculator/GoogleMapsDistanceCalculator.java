package test_amqp.calculator;


import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import test_amqp.model.Direction;

import java.math.BigDecimal;


@Service
@PropertySource(value = {"classpath:application.properties"})
public class GoogleMapsDistanceCalculator {

    @Value("${key}")
    private String apiKey;

    private final static String country = "United Kingdom";

    public BigDecimal calculateDistance(Direction from, Direction to)  {

        StringBuilder stringBuilder = new StringBuilder();

        GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
        String[] origins = new String[]{
                stringBuilder.append(from.getName())
                        .append(" , ")
                        .append(country)
                        .toString()
        };

        stringBuilder = new StringBuilder();
        String[] destinations = new String[]{
                stringBuilder.append(to.getName())
                        .append(" , ")
                        .append(country)
                        .toString()
        };
        DistanceMatrix matrix =
                null;
        try {
            matrix = DistanceMatrixApi.getDistanceMatrix(context, origins, destinations).await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(matrix.rows[0].elements[0].distance);
        String distanceInKm = matrix.rows[0].elements[0].distance.toString().split(" km")[0];

        return new BigDecimal(distanceInKm);
    }
}
