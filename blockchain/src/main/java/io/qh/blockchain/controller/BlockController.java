package io.qh.blockchain.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.github.pagehelper.Page;
import io.qh.blockchain.dto.PageDTO;
import io.qh.blockchain.po.Block;
import io.qh.blockchain.po.Transaction;
import io.qh.blockchain.po.TransactionDetail;
import io.qh.blockchain.service.BlockService;
import io.qh.blockchain.service.TransactionDetailService;
import io.qh.blockchain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/block")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping("/getRecent")
    public List<JSONObject> getRecent(){
        List<Block> blocks = blockService.getRecent();
        List<JSONObject> blockJsons = blocks.stream().map(block -> {
            JSONObject blockJson = new JSONObject();
            blockJson.put("height", block.getHeight());
            blockJson.put("blockhash", block.getBlockhash());
            blockJson.put("time", block.getTime());
            blockJson.put("miner", block.getMiner());
            blockJson.put("size", block.getSizeondisk());
            return blockJson;
        }).collect(Collectors.toList());
        return blockJsons;
    }

    @GetMapping("/getWithPage")
    public PageDTO<JSONObject> getWithPage(@RequestParam(required = false, defaultValue = "1") Integer page){
        Page<Block> blocks = blockService.getWithPage(page);
        List<JSONObject> blockJsons = blocks.stream().map(block -> {
            JSONObject blockJson = new JSONObject();
            blockJson.put("height", block.getHeight());
            blockJson.put("blockhash", block.getBlockhash());
            blockJson.put("time", block.getTime());
            blockJson.put("miner", block.getMiner());
            blockJson.put("size", block.getSizeondisk());
            return blockJson;
        }).collect(Collectors.toList());

        PageDTO<JSONObject> pageDTO = new PageDTO<>();
        pageDTO.setList(blockJsons);
        pageDTO.setTotal(blocks.getTotal());
        pageDTO.setPageSize(blocks.getPageSize());
        pageDTO.setCurrentPage(blocks.getPageNum());

        return pageDTO;
    }

    @GetMapping("/getInfoByHash")
    public JSONObject getInfoByHash(@RequestParam String blockhash){

        JSONObject blockInfoJson = new JSONObject();

        Block block = blockService.getByBlockhash(blockhash);
        blockInfoJson.put("blockhash",block.getBlockhash());
        blockInfoJson.put("confirmations",null);
        blockInfoJson.put("time",block.getTime());
        blockInfoJson.put("height",block.getHeight());
        blockInfoJson.put("miner",block.getMiner());
        blockInfoJson.put("txSize",block.getTxsize());
        blockInfoJson.put("difficulty",block.getDifficulty());
        blockInfoJson.put("merkleroot",block.getMerkleRoot());
        blockInfoJson.put("version",block.getVersion());
        blockInfoJson.put("bits",block.getBits());
        blockInfoJson.put("weight",block.getWeight());
        blockInfoJson.put("sizeOnDisk",block.getSizeondisk());
        blockInfoJson.put("nonce",block.getNonce());
        blockInfoJson.put("txVol",block.getTransactionVolume());
        blockInfoJson.put("blockReward",block.getBlockReward());
        blockInfoJson.put("feeReward",block.getFeeReward());

        List<Transaction> transactions = transactionService.getByBlockId(block.getBlockId());
        List<JSONObject> txJsons = transactions.stream().map(tx -> {
            JSONObject txJson = new JSONObject();
            txJson.put("txid", tx.getTxid());
            txJson.put("txhash", tx.getTxhash());
            txJson.put("time", tx.getTime());
            txJson.put("fees", tx.getFees());
            txJson.put("totalOutput", tx.getTotalOutput());

            List<TransactionDetail> txDetails = transactionDetailService.getByTransactionId(tx.getTransactionId());
            List<JSONObject> txDetailJsons = txDetails.stream().map(txDetail -> {
                JSONObject txDetailJson = new JSONObject();
                txDetailJson.put("address", txDetail.getAddress());
                txDetailJson.put("type", txDetail.getType());
                txDetailJson.put("amount", Math.abs(txDetail.getAmount()));
                return txDetailJson;
            }).collect(Collectors.toList());
            txJson.put("txDetails", txDetailJsons);
            return txJson;
        }).collect(Collectors.toList());
        blockInfoJson.put("transactions",txJsons);
        return blockInfoJson;
    }

    @GetMapping("/getInfoByHeight")
    public JSONObject getInfoByHeight(@RequestParam Integer height){
        return null;
    }
}
