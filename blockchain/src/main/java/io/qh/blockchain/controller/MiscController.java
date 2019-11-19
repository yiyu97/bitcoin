package io.qh.blockchain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/misc")
public class MiscController {

    @GetMapping("/search")
    public String search(@RequestParam String keyword){

        if (keyword.matches("-?\\d+")){
            return "block detail url";
        }

        if (keyword.length() < 64){
            return "address info url";
        }

        if (keyword.startsWith("00000")){
            return "block detail url";
        }else {
            return "tx detail url";
        }

    }
}
