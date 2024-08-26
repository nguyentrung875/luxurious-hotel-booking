package com.java06.luxurious_hotel.response;

import lombok.Data;

@Data
public class BaseResponse {
    private int statusCode = 200;
    private String message;
    private Object data;
}
