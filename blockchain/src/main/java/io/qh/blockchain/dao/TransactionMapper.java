package io.qh.blockchain.dao;

import com.github.pagehelper.Page;
import feign.Param;
import io.qh.blockchain.po.Transaction;

import java.util.List;

public interface TransactionMapper {
    int deleteByPrimaryKey(Integer transactionId);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(Integer transactionId);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

    List<Transaction> selectByBlockId(@Param("blockId") Integer blockId);
    Page<Transaction> selectByBlockIdWithPage(@Param("blockId") Integer blockId);
    Transaction selectByTxid(@Param("txid") String txid);
}