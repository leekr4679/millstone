package com.base.app.controllers.admin;

import com.base.app.domain.system.role.RoleService;
import com.base.app.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public String admin() {
        return "/admin/admin";
    }
}
