package io.qh.blockchain.service;

import com.github.pagehelper.Page;
import io.qh.blockchain.po.Block;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface BlockService {

    String syncBlock(String blockhash);
    void syncBlocks(String fromBlockhash);

    List<Block> getRecent();

    Page<Block> getWithPage(Integer page);

    Block getByBlockhash(String blockhash);
}
