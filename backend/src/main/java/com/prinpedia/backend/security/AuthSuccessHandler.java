package com.prinpedia.backend.security;

import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.findUserByName(userDetails.getUsername());
        JSONObject jsonObject = new JSONObject();
        JSONObject extraData = new JSONObject();
        extraData.put("username", user.getUsername());
        extraData.put("type", user.getAuthority());
        jsonObject.put("extraData", extraData);
        jsonObject.put("message", "Login success");
        jsonObject.put("status", 0);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }
}
