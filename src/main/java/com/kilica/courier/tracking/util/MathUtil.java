package com.kilica.courier.tracking.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtil {

    public final Double EARTH_RADIUS = 6371000D;

    public Double calculateDistanceByHaversineFormula(final Double storeLatitude,
                                                      final Double storeLongitude,
                                                      final Double courierLatitude,
                                                      final Double courierLongitude) {

        Double latitudeDistance = Math.toRadians(courierLatitude - storeLatitude);
        Double longitudeDistance = Math.toRadians(courierLongitude - storeLongitude);

        Double sinTheta = Math.pow(Math.sin(latitudeDistance / 2),2) + Math.pow(Math.sin(longitudeDistance / 2), 2)
                * Math.cos(Math.toRadians(storeLatitude)) * Math.cos(Math.toRadians(courierLatitude));

        Double theta = 2 * Math.asin(Math.sqrt(sinTheta));

        return EARTH_RADIUS * theta;
    }

}