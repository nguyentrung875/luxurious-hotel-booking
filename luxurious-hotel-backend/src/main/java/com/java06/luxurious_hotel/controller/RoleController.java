package com.java06.luxurious_hotel.controller;


import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getrole() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Role Retrieved Successfully");
        baseResponse.setData(roleService.getRoles());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
