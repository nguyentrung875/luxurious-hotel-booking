package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.EmployeeDTO;
import com.java06.luxurious_hotel.entity.RoleEntity;
import com.java06.luxurious_hotel.entity.UserEntity;
import com.java06.luxurious_hotel.repository.EmployeeReposiory;
import com.java06.luxurious_hotel.repository.RoleRepository;
import com.java06.luxurious_hotel.request.AddEmployeeRequest;
import com.java06.luxurious_hotel.request.UpdateEmployeeRequest;
import com.java06.luxurious_hotel.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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

    @Override
    public Boolean updateEmployee(UpdateEmployeeRequest updateEmployeeRequest) {
        Optional<UserEntity> usercheck = employeeReposiory.findById(updateEmployeeRequest.id());
        boolean isSuccess = false;
        if (usercheck.isPresent()) {
            UserEntity Employee = usercheck.get();
                Employee.setId(updateEmployeeRequest.id());
            if (updateEmployeeRequest.firstname() != null) {
                Employee.setFirstName(updateEmployeeRequest.firstname());
            }
            if (updateEmployeeRequest.lastname() != null) {
                Employee.setLastName(updateEmployeeRequest.lastname());
            }
            if (updateEmployeeRequest.address() != null) {
                Employee.setAddress(updateEmployeeRequest.address());
            }
            if (updateEmployeeRequest.email() != null) {
                Employee.setEmail(updateEmployeeRequest.email());
            }
            if (updateEmployeeRequest.dob() != null) {
                Employee.setDob(LocalDate.parse(updateEmployeeRequest.dob()));
            }
            if (updateEmployeeRequest.image() != null && updateEmployeeRequest.image().getOriginalFilename() != null) {
                Employee.setImage(updateEmployeeRequest.image().getOriginalFilename());
            }
            if (updateEmployeeRequest.summary() != null) {
                Employee.setSummary(updateEmployeeRequest.summary());
            }
            if (updateEmployeeRequest.phone() != null) {
                Employee.setPhone(updateEmployeeRequest.phone());
            }
            RoleEntity role = roleRepository.findRoleEntitiesById(updateEmployeeRequest.IdRole());
            Employee.setRole(role);
            employeeReposiory.save(Employee);
            isSuccess= true;
        }
        return isSuccess;
    }

    @Override
    public Boolean deleteEmployee(int employeeId) {
        Boolean isSuccess = false;
        Optional<UserEntity> usercheck = employeeReposiory.findById(employeeId);
        if (usercheck.isPresent()) {
            employeeReposiory.deleteById(employeeId);
            isSuccess = true;
        }
        return isSuccess;
    }


}
