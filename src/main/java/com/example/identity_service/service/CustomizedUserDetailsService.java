package com.example.identity_service.service;

import com.example.identity_service.entity.RoleEntity;
import com.example.identity_service.entity.UserEntity;
import com.example.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomizedUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("No user with this user name");
        }else{
            System.out.println(user.getUsername());
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (RoleEntity role : user.getRoles()) {
//            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//        }

//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
