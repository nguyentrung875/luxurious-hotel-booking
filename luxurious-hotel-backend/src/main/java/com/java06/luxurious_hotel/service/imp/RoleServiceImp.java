package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.RoleDTO;
import com.java06.luxurious_hotel.entity.RoleEntity;
import com.java06.luxurious_hotel.repository.RoleRepository;
import com.java06.luxurious_hotel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDTO> getRoles() {
        List<RoleDTO> roledto = new ArrayList<>();
        List<RoleEntity> roles = roleRepository.findAll();
        for (RoleEntity data : roles) {
            RoleDTO dto = new RoleDTO();
            dto.setId(data.getId());
            dto.setName(data.getName());
            dto.setDescription(data.getDescription());
            roledto.add(dto);
        }
        return roledto;
    }
}
