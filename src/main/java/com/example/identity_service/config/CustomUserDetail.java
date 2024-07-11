package com.example.identity_service.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Set;

@Component
public class CustomUserDetail implements UserDetails {
    private  String username;
    private  String password;
    public Set<GrantedAuthority> authorities;
//    @Autowired
//    private UserRepository userRepository;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        UserEntity user = userRepository.findByUsername(username);
//        authorities = new HashSet<>();
//        for (RoleEntity role : user.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//            for (PermissionEntity permission : role.getPermissions()) {
//                authorities.add(new SimpleGrantedAuthority(permission.getName()));
//            }
//        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
