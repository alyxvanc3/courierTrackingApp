package com.kilica.courier.tracking.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogCourierRequest {
    private LocalDateTime time;
    private Long id;
    private Double latitude;
    private Double longitude;
}
