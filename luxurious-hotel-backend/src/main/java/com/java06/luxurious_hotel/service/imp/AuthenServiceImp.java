package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.AuthorityDTO;
import com.java06.luxurious_hotel.request.AuthenRequest;
import com.java06.luxurious_hotel.service.AuthenService;
import com.java06.luxurious_hotel.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenServiceImp implements AuthenService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public String login(AuthenRequest request) {

        UsernamePasswordAuthenticationToken authenToken =
                new UsernamePasswordAuthenticationToken(request.username(), request.password());
        System.out.println(request.username() + " : " + request.password());
        Authentication authen = authenticationManager.authenticate(authenToken);

        String role =  authen.getAuthorities()
                .stream().map(grantedAuthority -> grantedAuthority.getAuthority().toString()
                ).findFirst().get();

        AuthorityDTO authorityDTO = new AuthorityDTO();
        authorityDTO.setUsername(request.username());
        authorityDTO.setRole(role);

        return jwtUtils.generateJwtToken(authorityDTO);
    }
}
