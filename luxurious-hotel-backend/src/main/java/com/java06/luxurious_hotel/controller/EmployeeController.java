package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.request.AddEmployeeRequest;
import com.java06.luxurious_hotel.request.UpdateEmployeeRequest;
import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> addEmployee(AddEmployeeRequest addEmployeeRequest) {
        employeeService.addEmployee(addEmployeeRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("New Employee Added");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(UpdateEmployeeRequest updateEmployeeRequest) {
        boolean isSuccess = employeeService.updateEmployee(updateEmployeeRequest);
        BaseResponse baseResponse = new BaseResponse();
        if (isSuccess) {
            baseResponse.setMessage("Employee Updated Successfully");
        }else {
            baseResponse.setMessage("Employee Updated Failed");
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        boolean isSuccess = employeeService.deleteEmployee(id);
        BaseResponse baseResponse = new BaseResponse();
        if (isSuccess) {
            baseResponse.setMessage("Employee Deleted Successfully");
        }else {
            baseResponse.setMessage("Employee Deleted Failed");
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable int id) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Employee Retrieved Successfully");
        baseResponse.setData(employeeService.getEmployee(id));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployee() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Employee Retrieved Successfully");
        baseResponse.setData(employeeService.getAllEmployee());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
