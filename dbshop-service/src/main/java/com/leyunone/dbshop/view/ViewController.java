package com.leyunone.dbshop.view;

import org.springframework.stereotype.Controller;

/**
 * UI界面
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
