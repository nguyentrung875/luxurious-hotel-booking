package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.EmployeeDTO;
import com.java06.luxurious_hotel.dto.RoleDTO;
import com.java06.luxurious_hotel.entity.RoleEntity;
import com.java06.luxurious_hotel.entity.UserEntity;
import com.java06.luxurious_hotel.exception.Employee.EmployeeNotExitsException;
import com.java06.luxurious_hotel.repository.EmployeeReposiory;
import com.java06.luxurious_hotel.repository.RoleRepository;
import com.java06.luxurious_hotel.request.AddEmployeeRequest;
import com.java06.luxurious_hotel.request.UpdateEmployeeRequest;
import com.java06.luxurious_hotel.service.EmployeeService;
import com.java06.luxurious_hotel.service.FilesStorageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeServiceImp implements EmployeeService {


    @Autowired
    private FilesStorageService filesStorageService;

    @Autowired
    private EmployeeReposiory employeeReposiory;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
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
        Employee.setPassword(passwordEncoder.encode(addEmployeeRequest.password()));
        RoleEntity Role = roleRepository.findRoleEntitiesById(addEmployeeRequest.Idrole());
        Employee.setRole(Role);
        Employee.setImage(addEmployeeRequest.image().getOriginalFilename());
        MultipartFile file = addEmployeeRequest.image();
        filesStorageService.save(file);
        employeeReposiory.save(Employee);
    }
    @Transactional
    @Override
    public Boolean updateEmployee(UpdateEmployeeRequest updateEmployeeRequest) {
        Optional<UserEntity> usercheck = employeeReposiory.findById(updateEmployeeRequest.id());
        boolean isSuccess = false;
        if (usercheck.isPresent()) {
            UserEntity Employee = usercheck.get();
                Employee.setId(updateEmployeeRequest.id());
                Employee.setFirstName(updateEmployeeRequest.firstname());


                Employee.setLastName(updateEmployeeRequest.lastname());


                Employee.setAddress(updateEmployeeRequest.address());


                Employee.setEmail(updateEmployeeRequest.email());


                Employee.setDob(LocalDate.parse(updateEmployeeRequest.dob()));


                Employee.setImage(updateEmployeeRequest.image().getOriginalFilename());
                filesStorageService.save(updateEmployeeRequest.image());


                Employee.setSummary(updateEmployeeRequest.summary());


                Employee.setPhone(updateEmployeeRequest.phone());

            RoleEntity role = roleRepository.findRoleEntitiesById(updateEmployeeRequest.IdRole());
            Employee.setRole(role);
            employeeReposiory.save(Employee);
            isSuccess= true;
        }else{
            throw  new EmployeeNotExitsException("Employee does not exist");
        }
        return isSuccess;
    }
    @Transactional
    @Override
    public Boolean deleteEmployee(int employeeId) {
        Boolean isSuccess = false;
        if (employeeReposiory.existsById(employeeId)) {
            employeeReposiory.deleteById(employeeId);
            isSuccess = true;
        }else{
            throw  new EmployeeNotExitsException("Employee does not exist");
        }
        return isSuccess;
    }

    @Override
    public EmployeeDTO getEmployee(int employeeId) {
        Optional<UserEntity> employeecheck = employeeReposiory.findById(employeeId);
        return employeecheck.stream().map(userEntity -> {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(userEntity.getId());
            employeeDTO.setFirstname(userEntity.getFirstName());
            employeeDTO.setLastname(userEntity.getLastName());
            employeeDTO.setEmail(userEntity.getEmail());
            employeeDTO.setAddress(userEntity.getAddress());
            employeeDTO.setDob(userEntity.getDob());
            employeeDTO.setImage(userEntity.getImage());
            employeeDTO.setSumary(userEntity.getSummary());
            employeeDTO.setPhone(userEntity.getPhone());
            RoleDTO roleDTO= new RoleDTO();
            roleDTO.setName(userEntity.getRole().getName());
            roleDTO.setDescription(userEntity.getRole().getDescription());
            employeeDTO.setRole(roleDTO);
            return employeeDTO;
        }).findFirst().orElseThrow(() -> new RuntimeException("Employee not found"));
    }

}
