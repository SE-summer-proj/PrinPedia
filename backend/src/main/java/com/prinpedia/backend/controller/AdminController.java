package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.AdminService;
import com.prinpedia.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/allUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllUsers() {
        List<User> userList = adminService.findAllUsers();
        JSONArray extraData = new JSONArray();
        for(User user: userList) {
            JSONObject userInfo = new JSONObject();
            userInfo.put("username", user.getUsername());
            userInfo.put("email", user.getEmail());
            extraData.add(userInfo);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "success");
        response.put("extraData", extraData);
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/grant")
    public String grantAdmin(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        JSONObject response = new JSONObject();
        if(adminService.grantAdmin(username)) {
            response.put("status", 0);
            response.put("message", "Success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Failure");
        }
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/ability")
    @PreAuthorize("hasRole('ADMIN')")
    public String changeUserAbility(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        Boolean enabled = jsonObject.getBoolean("enabled");
        JSONObject response = new JSONObject();
        if(adminService.changeUserAbility(username, enabled)) {
            response.put("status", !enabled);
            response.put("message", "Success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Failure, cannot disable this user");
        }
        return response.toJSONString();
    }
}
