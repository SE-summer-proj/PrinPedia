package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.MessageBox;
import com.prinpedia.backend.service.MessageBoxService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/message", produces = "text/plain;charset=UTF-8")
public class MessageBoxController {
    @Autowired
    private MessageBoxService messageBoxService;

    @CrossOrigin
    @ResponseBody
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public String createMessage(@RequestBody JSONObject request,
                                Principal principal) {
        String username = principal.getName();
        String content = request.getString("content");
        messageBoxService.createMessage(username, content);
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Submit success");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') || principal.username.equals(#username)")
    public String getMessageByUser(@RequestParam("username") String username) {
        List<MessageBox> messageBoxList =
                messageBoxService.getMessageByUser(username);
        JSONArray extraData = new JSONArray();
        for(MessageBox messageBox : messageBoxList) {
            JSONObject message = new JSONObject();
            message.put("id", messageBox.getId().toString());
            message.put("content", messageBox.getContent());
            message.put("time", messageBox.getTime());
            message.put("isReplied", messageBox.getReplied());
            if(messageBox.getReplied())
                message.put("reply", messageBox.getReply());
            extraData.add(message);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", extraData);
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllMessage(@RequestParam("isReplied") Boolean isReplied) {
        List<MessageBox> messageBoxList =
                messageBoxService.getAllMessage(isReplied);
        JSONArray extraData = new JSONArray();
        for(MessageBox messageBox : messageBoxList) {
            JSONObject message = new JSONObject();
            message.put("id", messageBox.getId().toString());
            message.put("username", messageBox.getUsername());
            message.put("time", messageBox.getTime());
            message.put("content", messageBox.getContent());
            message.put("isReplied", messageBox.getReplied());
            if(isReplied)
                message.put("reply", messageBox.getReply());
            extraData.add(message);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", extraData);
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/reply")
    @PreAuthorize("hasRole('ADMIN')")
    public String reply(@RequestBody JSONObject request) {
        String reply = request.getString("reply");
        String objectIdString = request.getString("id");
        ObjectId id = new ObjectId(objectIdString);
        JSONObject response = new JSONObject();
        if(messageBoxService.reply(id, reply)) {
            response.put("status", 0);
            response.put("message", "Reply success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Something wrong happened");
        }
        return response.toJSONString();
    }
}
