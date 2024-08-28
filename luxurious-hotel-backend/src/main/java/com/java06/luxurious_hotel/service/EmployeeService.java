package com.java06.luxurious_hotel.service;

import com.java06.luxurious_hotel.request.AddEmployeeRequest;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    void addEmployee(AddEmployeeRequest addEmployeeRequest);
}
