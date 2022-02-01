package io.streamnative.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String userid;
    private Long eventTime;
    private String eventType;
    private String productId;
    private String categoryId;
    private String categoryCode;
    private String brand;
    private Double price;
    private String userSession;
}
