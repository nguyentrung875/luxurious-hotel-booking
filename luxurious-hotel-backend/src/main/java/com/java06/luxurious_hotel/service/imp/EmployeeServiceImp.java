package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.EmployeeDTO;
import com.java06.luxurious_hotel.dto.RoleDTO;
import com.java06.luxurious_hotel.entity.RoleEntity;
import com.java06.luxurious_hotel.entity.UserEntity;
import com.java06.luxurious_hotel.repository.EmployeeReposiory;
import com.java06.luxurious_hotel.repository.RoleRepository;
import com.java06.luxurious_hotel.request.AddEmployeeRequest;
import com.java06.luxurious_hotel.request.UpdateEmployeeRequest;
import com.java06.luxurious_hotel.service.EmployeeService;
import com.java06.luxurious_hotel.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
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
        MultipartFile file = addEmployeeRequest.image();
        filesStorageService.save(file);
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
                filesStorageService.save(updateEmployeeRequest.image());
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

    @Override
    public Map<RoleDTO, List<EmployeeDTO>> getAllEmployee() {
        List<UserEntity> employee= employeeReposiory.findAll();
        Map<RoleDTO, List<EmployeeDTO>> employeeDTOMap = new HashMap<>();
        for (UserEntity userEntity : employee) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setLastname(userEntity.getLastName());
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName(userEntity.getRole().getName());
            employeeDTO.setRole(roleDTO);
            employeeDTOMap.computeIfAbsent(roleDTO, k -> new ArrayList<>()).add(employeeDTO);
        }
        return employeeDTOMap;
    }


}
