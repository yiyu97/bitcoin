package io.qh.blockchain.controller;

import com.alibaba.fastjson.JSONObject;
import io.qh.blockchain.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping("/getInfoByAddress")
    public JSONObject getInfoByAddress(@RequestParam String address){

        Integer txTotal = transactionDetailService.getTotalByAddress(address);
        Double receiveAmount = transactionDetailService.getReceiveByAddres(address);
        Double sendAmount = transactionDetailService.getSendByAddress(address);
        Double balance = receiveAmount + sendAmount;


        JSONObject addressInfoJson = new JSONObject();
        addressInfoJson.put("address", address);
        addressInfoJson.put("txSize", txTotal);
        addressInfoJson.put("totalReceived", receiveAmount);
        addressInfoJson.put("totalSent", Math.abs(sendAmount));
        addressInfoJson.put("balance", balance);

        return addressInfoJson;
    }
}
