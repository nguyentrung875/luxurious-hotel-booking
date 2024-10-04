package com.java06.luxurious_hotel.enumContraints;

public enum RabbitmqEnum {
    BOOKING_EMAIL_EXCHANGE ("booking-email-exchange")
    ;

    private String key;

    RabbitmqEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
