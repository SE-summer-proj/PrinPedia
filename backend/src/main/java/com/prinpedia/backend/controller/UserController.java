package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/register")
    public String register(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("mailAddr");
        userService.register(username, password, email);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("status", 0);
        jsonObject1.put("message", "Registration succeed.");
        return jsonObject1.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/login")
    public String login(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject jsonObject1 = new JSONObject();
        JSONObject response = new JSONObject();
        if(userService.validate(username, password)) {
            User user = userService.findUserByName(username);
            jsonObject1.put("username", username);
            jsonObject1.put("userType", user.getAuthority());
            jsonObject1.put("avatar", user.getAvatarBase64());
            response.put("status", 0);
            response.put("message", "Login succeed");
            response.put("extraData", jsonObject1);
        }
        else {
            jsonObject1.put("username", username);
            jsonObject1.put("userType", 0);
            response.put("status", -1);
            response.put("message", "Wrong username or password");
            response.put("extraData", jsonObject1);
        }
        return response.toJSONString();
    }
}
