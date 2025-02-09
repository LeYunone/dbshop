package com.leyunone.dbshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-07-02
 */
@Controller
@RequestMapping("/dbshop")
public class ViewController {

    @GetMapping("/home")
    public String homeView(){
        return "dbshop";
    }
}
