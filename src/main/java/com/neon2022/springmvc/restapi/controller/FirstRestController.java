package com.neon2022.springmvc.restapi.controller;

import com.neon2022.springmvc.restapi.valueobject.ConversationVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstRestController {

    @GetMapping("/first-rest-api/is-alive")
    public String isAlive() {
        return "everything is ok.";
    }

    @PostMapping("/first-rest-api/complex-param-and-response-using-pojo")
    public String talkAbout(@RequestBody ConversationVO conversationVO) {
        System.out.printf("conversationVO=%s\n", conversationVO);
        return "everything is ok.";
    }

}