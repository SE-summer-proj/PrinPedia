package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.AdminService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin", produces = "text/plain;charset=UTF-8")
public class AdminController {
    @Autowired
    private AdminService adminService;

    private String superPassword = null;

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
    @PreAuthorize("hasRole('SUPER')")
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

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/super/password")
    public String generateSuperPassword(@RequestBody JSONObject request) {
        logger.info("Receive POST request on '/admin/super/password'");
        logger.debug("POST request on '/admin/super/password' with request body: " +
                request.toJSONString());
        String password = request.getString("password");
        logger.debug("POST request on '/admin/grant' with params: " +
                "'password'=" + password);
        JSONObject response = new JSONObject();
        if(password.equals("#super_user_password#")) {
            response.put("status", 0);
            response.put("message", "Created new password");
            superPassword = RandomStringUtils.randomAlphanumeric(20);
            logger.info("Super password is: " + superPassword);
        }
        else {
            response.put("status", -1);
            response.put("message", "Request rejected");
            superPassword = RandomStringUtils.random(20);
        }
        logger.debug("Response to POST request on '/admin/super/password' is: "
                + response.toJSONString());
        logger.info("Response to POST request on '/admin/super/password' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/super/create")
    public String createSuperAdmin(@RequestBody JSONObject request) {
        logger.info("Receive POST request on '/admin/super/create'");
        String username = request.getString("username");
        String password = request.getString("password");
        String key = request.getString("key");
        logger.debug("POST request on '/admin/super/create' with params: " +
                "'username'=" + username);
        JSONObject response = new JSONObject();
        if(key.equals(superPassword)) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            password = passwordEncoder.encode(password);
            if(adminService.createSuperUser(username, password)) {
                response.put("status", 0);
                response.put("message", "Create super admin success");
                superPassword = RandomStringUtils.random(20);
                logger.info("Super admin created successfully, super password " +
                        "has been reset");
            }
            else {
                response.put("status", -1);
                response.put("message", "Create failed, duplicate username");
            }
        }
        else {
            response.put("status", -1);
            response.put("message", "Request rejected");
        }
        logger.debug("Response to POST request on '/admin/super/create' is: "
                + response.toJSONString());
        logger.info("Response to POST request on '/admin/super/create' finished");
        return response.toJSONString();
    }
}
