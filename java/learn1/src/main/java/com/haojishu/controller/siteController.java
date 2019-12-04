package com.haojishu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

    @RequestMapping(value = "/")
    public String Hello(){
        return "Helllo World";
    }


}
