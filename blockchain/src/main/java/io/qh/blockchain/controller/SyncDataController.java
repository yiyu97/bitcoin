package io.qh.blockchain.controller;

import io.qh.blockchains.service.BlockService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/syncdata")
@EnableAutoConfiguration
public class SyncDataController {

    @Autowired
    private BlockService blockService;

    @PostMapping("/fullImport")
    public void fullImport(@RequestParam(required = false, defaultValue = "000000000933ea01ad0ee984209779baaec3ced90fa3f408719526f8d77f4943") String blockhash){
        blockService.syncBlocks(blockhash);
    }
}
