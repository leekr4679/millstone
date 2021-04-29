package com.base.app.controllers.admin;

import com.base.app.domain.system.role.Role;
import com.base.app.domain.system.role.RoleService;
import com.base.app.domain.user.User;
import com.base.app.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<User> users = userService.list();
        model.addAttribute("users", users);

        return "/admin/user/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String save(Model model) {
        return "/admin/user/regist";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(User user, Model model) {
    }

    @RequestMapping(value = "/user/regist", method = RequestMethod.POST)
    public String saveUser(User user) {
        Role role = roleService.findByRoleCd("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setUserRoles(roles);
        userService.saveUser(user);

        return "redirect:/";
    }
}
