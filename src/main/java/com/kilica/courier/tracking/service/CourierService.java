package com.kilica.courier.tracking.service;

import java.time.LocalDateTime;

public interface CourierService {

    Void logIfCourierinZone(LocalDateTime time,
                            Long id,
                            Double latitude,
                            Double longitude);

    Double getTotalTravelDistance(Long id);

}
