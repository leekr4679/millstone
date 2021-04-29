package com.base.app.security.service;

import com.base.app.domain.user.SessionUser;
import com.base.app.domain.user.User;
import com.base.app.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class CusUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public SessionUser loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userService.findByUserId(userId);

        if (user == null) {
            throw new UsernameNotFoundException("UserId Not Found");
        }

        List<String> userRoles = user.getUserRoles()
                                    .stream()
                                    .map(userRole -> userRole.getRoleCd())
                                    .collect(Collectors.toList());

        SessionUser sessionUser = new ModelMapper().map(user, SessionUser.class);
        sessionUser.setUserRoles(userRoles);

        return sessionUser;
    }
}
