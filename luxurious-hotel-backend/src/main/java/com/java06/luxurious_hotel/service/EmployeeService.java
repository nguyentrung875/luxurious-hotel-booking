package com.java06.luxurious_hotel.service;

import com.java06.luxurious_hotel.dto.EmployeeDTO;
import com.java06.luxurious_hotel.request.AddEmployeeRequest;
import com.java06.luxurious_hotel.request.UpdateEmployeeRequest;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    void addEmployee(AddEmployeeRequest addEmployeeRequest);
    Boolean updateEmployee(UpdateEmployeeRequest updateEmployeeRequest);
    Boolean deleteEmployee(int employeeId);
}
