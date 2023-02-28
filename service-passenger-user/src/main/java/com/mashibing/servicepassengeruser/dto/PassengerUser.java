package com.mashibing.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PassengerUser {

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String passengerPhone;

    private String passengerName;

    private Byte passengerGender;

    private Byte state;
}
