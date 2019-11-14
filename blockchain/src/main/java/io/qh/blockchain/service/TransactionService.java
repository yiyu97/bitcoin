package io.qh.blockchain.service;

public interface TransactionService {
    void syncTransaction(String txid, Integer blockId, Long time);
}
