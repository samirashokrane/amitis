package com.amitis.interview.controller.exception;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GracefulError {

    Integer statusCode;
    String statusMessage;
    String gracefulMessage;
    LocalDateTime occurrenceTime;



}
