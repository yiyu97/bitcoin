package io.qh.blockchain.service;

import com.alibaba.fastjson.JSONObject;
import io.qh.blockchain.po.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {

    void syncTxDetailVout(JSONObject vout, Integer transactionId);

    void syncTxDetailVin(JSONObject vin, Integer transactionId);

    List<TransactionDetail> getByTransactionId(Integer transactionId);
}
