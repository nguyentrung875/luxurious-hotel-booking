package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.entity.RoleEntity;
import com.java06.luxurious_hotel.entity.UserEntity;
import com.java06.luxurious_hotel.repository.EmployeeReposiory;
import com.java06.luxurious_hotel.repository.RoleRepository;
import com.java06.luxurious_hotel.request.AddEmployeeRequest;
import com.java06.luxurious_hotel.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmployeeServiceImp implements EmployeeService {


    @Autowired
    private EmployeeReposiory employeeReposiory;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void addEmployee(AddEmployeeRequest addEmployeeRequest) {
        UserEntity Employee = new UserEntity();
        Employee.setFirstName(addEmployeeRequest.firstName());
        Employee.setLastName(addEmployeeRequest.lastName());
        Employee.setEmail(addEmployeeRequest.email());
        Employee.setDob(LocalDate.parse(addEmployeeRequest.dob()));
        Employee.setAddress(addEmployeeRequest.address());
        Employee.setSummary(addEmployeeRequest.summary());
        Employee.setPhone(addEmployeeRequest.phone());
        Employee.setUsername(addEmployeeRequest.username());
        Employee.setPassword(addEmployeeRequest.password());
        RoleEntity Role = roleRepository.findRoleEntitiesById(addEmployeeRequest.Idrole());
        Employee.setRole(Role);
        Employee.setImage(addEmployeeRequest.image().getOriginalFilename());


        employeeReposiory.save(Employee);
    }
}
