package com.java06.luxurious_hotel.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<DateRangeConstraint, Object> { //Object là class mình cần validate

    private String checkInField;
    private String checkOutField;

    @Override
    public void initialize(DateRangeConstraint constraintAnnotation) {
        this.checkInField = constraintAnnotation.checkInField();
        this.checkOutField = constraintAnnotation.checkOutField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            // Sử dụng reflection để lấy giá trị của các trường checkIn và checkOut
            Field checkInField = value.getClass().getDeclaredField(this.checkInField);
            Field checkOutField = value.getClass().getDeclaredField(this.checkOutField);

            //bỏ qua giới hạn quyền truy cập, cho phép đọc và sửa đổi giá trị của các field private, protected (hoặc package-private) của một đối tượng.
            checkInField.setAccessible(true);
            checkOutField.setAccessible(true);

            LocalDate checkInDate = LocalDate.parse(checkInField.get(value).toString());

            LocalDate checkOutDate = LocalDate.parse(checkOutField.get(value).toString());

            return checkInDate.isBefore(checkOutDate); // Kiểm tra check-in trước check-out
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }
}
