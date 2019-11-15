package io.qh.blockchain.dao;

import feign.Param;
import io.qh.blockchain.po.TransactionDetail;

import java.util.List;

public interface TransactionDetailMapper {
    int deleteByPrimaryKey(Long txDetailId);

    int insert(TransactionDetail record);

    int insertSelective(TransactionDetail record);

    TransactionDetail selectByPrimaryKey(Long txDetailId);

    int updateByPrimaryKeySelective(TransactionDetail record);
    List<TransactionDetail> selectByTransactionId(@Param("transactionId") Integer transactionId);
    int updateByPrimaryKey(TransactionDetail record);
}