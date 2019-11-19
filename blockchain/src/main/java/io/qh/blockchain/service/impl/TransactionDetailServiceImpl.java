package io.qh.blockchain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.qh.blockchain.client.BitcoinJsonRpcImpl;
import io.qh.blockchain.client.BitcoinRest;
import io.qh.blockchain.dao.TransactionDetailMapper;
import io.qh.blockchain.enumeration.TxDetailType;
import io.qh.blockchain.po.TransactionDetail;
import io.qh.blockchain.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {
    @Autowired
    private BitcoinRest bitcoinRest;

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @Autowired
    private BitcoinJsonRpcImpl bitcoinJsonRpc;

    @Override
    public void syncTxDetailVout(JSONObject vout, Integer transactionId) {
        TransactionDetail transactionDetail = new TransactionDetail();
        JSONObject scriptPubKey = vout.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses != null){
            String address = (String) addresses.get(0);
            transactionDetail.setAddress(address);
            transactionDetail.setAmount(vout.getDouble("value"));
            transactionDetail.setType((byte) TxDetailType.Receive.ordinal());
            transactionDetail.setTransactionId(transactionId);

            transactionDetailMapper.insert(transactionDetail);
        }
    }

    @Override
    public void syncTxDetailVin(JSONObject vin, Integer transactionId) {
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setTransactionId(transactionId);
        transactionDetail.setType((byte) TxDetailType.Send.ordinal());
        String txidVin = vin.getString("txid");
        Integer n = vin.getInteger("vout");
        if (txidVin != null && n != null){
/*            JSONObject utxoJson = bitcoinRest.getUTXO(txidVin, n);
            List<JSONObject> utxos = utxoJson.getJSONArray("utxos").toJavaList(JSONObject.class);
            JSONObject utxo = utxos.get(0);
            Double amount = utxo.getDouble("value");
            transactionDetail.setAmount(-amount);
            JSONObject scriptPubKey = utxo.getJSONObject("scriptPubKey");
            JSONArray addresses = scriptPubKey.getJSONArray("addresses");
            if (addresses != null){
                String address = (String) addresses.get(0);
                transactionDetail.setAddress(address);
                transactionDetailMapper.insert(transactionDetail);
           }*/

            try {
                JSONObject transactionJson = bitcoinJsonRpc.getRawTransaction(txidVin);
                JSONArray vouts = transactionJson.getJSONArray("vout");
                JSONObject vout = vouts.getJSONObject(n);
                Double amount = vout.getDouble("value");
                transactionDetail.setAmount(-amount);
                JSONObject scriptPubKey = vout.getJSONObject("scriptPubKey");
                JSONArray addresses = scriptPubKey.getJSONArray("addresses");
                if (addresses != null){
                    String address = addresses.getString(0);
                    transactionDetail.setAddress(address);
                    transactionDetailMapper.insert(transactionDetail);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }
    }

    @Override
    public List<TransactionDetail> getByTransactionId(Integer transactionId) {
        List<TransactionDetail> transactionDetails = transactionDetailMapper.selectByTransactionId(transactionId);
        return transactionDetails;
    }

    @Override
    public Integer getTotalByAddress(String address) {
        Integer total = transactionDetailMapper.selectTotalByAddress(address);
        return total;
    }

    @Override
    public Double getReceiveByAddres(String address) {
        Double receiveAmount = transactionDetailMapper.selectReceiveByAddress(address);
        return receiveAmount;
    }

    @Override
    public Double getSendByAddress(String address) {
        Double sendAmount = transactionDetailMapper.selectSendByAddress(address);
        return sendAmount;
    }
}
