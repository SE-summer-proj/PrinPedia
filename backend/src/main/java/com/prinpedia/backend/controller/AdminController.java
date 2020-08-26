package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.AdminService;
import com.prinpedia.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin", produces = "text/plain;charset=UTF-8")
public class AdminController {
    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/allUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllUsers() {
        logger.info("Receive GET request on '/admin/allUser'");
        List<User> userList = adminService.findAllUsers();
        JSONArray extraData = new JSONArray();
        for(User user: userList) {
            JSONObject userInfo = new JSONObject();
            userInfo.put("username", user.getUsername());
            userInfo.put("email", user.getEmail());
            userInfo.put("enabled", user.getEnabled());
            extraData.add(userInfo);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "success");
        response.put("extraData", extraData);
        logger.debug("Response to GET request on '/admin/allUser' is: "
                + response.toJSONString());
        logger.info("Response to GET request on '/admin/allUser' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/grant")
    public String grantAdmin(@RequestBody JSONObject jsonObject) {
        logger.info("Receive POST request on '/admin/grant'");
        logger.debug("POST request on '/admin/grant' with request body: " +
                jsonObject.toJSONString());
        String username = jsonObject.getString("username");
        logger.debug("POST request on '/admin/grant' with params: " +
                "'username'=" + username);
        JSONObject response = new JSONObject();
        if(adminService.grantAdmin(username)) {
            response.put("status", 0);
            response.put("message", "Success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Failure");
        }
        logger.debug("Response to POST request on '/admin/grant' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/admin/grant' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/ability")
    @PreAuthorize("hasRole('ADMIN')")
    public String changeUserAbility(@RequestBody JSONObject jsonObject) {
        logger.info("Receive POST request on '/admin/ability'");
        logger.debug("POST request on '/admin/ability' with request body: " +
                jsonObject.toJSONString());
        String username = jsonObject.getString("username");
        Boolean enabled = jsonObject.getBoolean("enabled");
        logger.debug("POST request on '/admin/ability' with params: " +
                "'username'=" + username + ", 'enabled'=" + enabled.toString());
        JSONObject response = new JSONObject();
        if(adminService.changeUserAbility(username, enabled)) {
            response.put("status", enabled);
            response.put("message", "Success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Failure, cannot disable this user");
        }
        logger.debug("Response to POST request on '/admin/ability' is: "
                + response.toJSONString());
        logger.info("Response to POST request on '/admin/ability' finished");
        return response.toJSONString();
    }
}
