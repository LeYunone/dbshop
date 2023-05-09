package com.leyunone.dbshop.controller;

import org.springframework.stereotype.Controller;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-09
 */
@Controller("/dbshop")
public class ViewController {

    public String view(){
        return "/";
    }
}
