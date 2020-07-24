package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/register")
    public String register(@RequestBody @NotNull JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("mailAddr");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);
        if(userService.register(username, password, email)) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("status", 0);
            jsonObject1.put("message", "Registration succeed.");
            return jsonObject1.toJSONString();
        }
        else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("status", -1);
            jsonObject1.put("message", "Registration fails");
            return jsonObject1.toJSONString();
        }
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/detail")
    @PreAuthorize("principal.username.equals(#username)")
    public String getUserDetail(@RequestParam("username") String username) {
        User user = userService.findUserByName(username);
        JSONObject response = new JSONObject();
        if(user != null) {
            response.put("status", 0);
            response.put("message", "Get user detail success");
            JSONObject extraData = new JSONObject();
            extraData.put("username", user.getUsername());
            extraData.put("email", user.getEmail());
            extraData.put("avatarBase64", user.getAvatarBase64());
            response.put("extraData", extraData);
        }
        else {
            response.put("status", -1);
            response.put("message", "Get user detail failure");
        }
        return response.toJSONString();
    }
}
