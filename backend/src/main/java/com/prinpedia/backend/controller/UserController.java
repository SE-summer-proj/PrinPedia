package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.UserService;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user", produces = "text/plain;charset=UTF-8")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/register")
    public String register(@RequestBody @NotNull JSONObject jsonObject) {
        logger.info("Receive POST request on '/user/register'");
        logger.debug("POST request on '/user/register' with request body: " +
                jsonObject.toJSONString());
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("mailAddr");
        logger.debug("POST request on '/user/register' with params: " +
                "'username'=" + username + ", 'mailAddr'=" + email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);
        JSONObject response = new JSONObject();
        if(userService.register(username, password, email)) {
            response.put("status", 0);
            response.put("message", "Registration succeed.");
        }
        else {
            response.put("status", -1);
            response.put("message", "Registration fails");
        }
        logger.debug("Response to POST request on '/user/register' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/user/register' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/detail")
    @PreAuthorize("principal.username.equals(#username)")
    public String getUserDetail(@RequestParam("username") String username) {
        logger.info("Receive GET request on '/user/detail'");
        logger.debug("GET request on '/user/detail' with params: " +
                "'username'=" + username);
        User user = userService.findUserByName(username);
        JSONObject response = new JSONObject();
        if(user != null) {
            response.put("status", 0);
            response.put("message", "Get user detail success");
            JSONObject extraData = new JSONObject();
            extraData.put("username", user.getUsername());
            extraData.put("email", user.getEmail());
            extraData.put("birthday", user.getBirthday());
            extraData.put("avatarBase64", user.getAvatarBase64());
            response.put("extraData", extraData);
        }
        else {
            response.put("status", -1);
            response.put("message", "Get user detail failure");
        }
        logger.debug("Response to GET request on '/user/detail' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/user/detail' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/edit")
    @PreAuthorize("principal.username.equals(#user.getUsername())")
    public String editUserDetail(@RequestBody User user) {
        logger.info("Receive POST request on '/user/edit'");
        logger.debug("POST request on '/user/edit' with request body: " +
                user.toString());
        JSONObject response = new JSONObject();
        if(userService.editUserDetail(user)) {
            response.put("status", 0);
            response.put("message", "Update user detail successfully");
        }
        else {
            response.put("status", -1);
            response.put("message", "Failure");
        }
        logger.debug("Response to POST request on '/user/edit' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/user/edit' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/password")
    @PreAuthorize("hasRole('USER')")
    public String alterPassword(@RequestBody JSONObject request,
                                Principal principal) {
        logger.info("Receive POST request on '/user/password'");
        logger.debug("POST request on '/user/password' with request body: " +
                request.toJSONString());
        String oldPassword = request.getString("oldPassword");
        String newPassword = request.getString("newPassword");
        String username = principal.getName();
        User user = userService.findUserByName(username);
        String password = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMatched = passwordEncoder.matches(oldPassword, password);
        JSONObject response = new JSONObject();
        if(isMatched) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.editUserDetail(user);
            response.put("status", 0);
            response.put("message", "Alter password success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Wrong old password");
        }
        logger.debug("Response to POST request on '/user/password' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/user/password' finished");
        return response.toJSONString();
    }
}
