package io.qh.blockchain.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.qh.blockchain.client.BitcoinRest;
import io.qh.blockchain.dao.BlockMapper;
import io.qh.blockchain.po.Block;
import io.qh.blockchain.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private BitcoinRest bitcoinRest;

    @Override
    public String syncBlock(String blockhash) {
        JSONObject blockJson = bitcoinRest.getBlockNoTxDetails(blockhash);
        Block block = new Block();
        block.setBlockhash(blockJson.getString("hash"));
        block.setConfirmations(blockJson.getInteger("confirmations"));
        block.setHeight(blockJson.getInteger("height"));
        block.setTime(blockJson.getLong("time"));
        block.setDifficulty(blockJson.getDouble("difficulty"));
        block.setSizeondisk(blockJson.getInteger("size"));
        block.setMerkleRoot(blockJson.getString("merkleroot"));
        block.setTxsize(blockJson.getInteger("nTx"));
        block.setVersion(blockJson.getString("versionHex"));
        block.setNonce(blockJson.getInteger("nonce").toString());
        block.setWeight(blockJson.getInteger("weight"));
        //todo get block reward
        //todo change bits to string
        //todo calculate tx volume, fee

        blockMapper.insert(block);

        String nextblockhash = blockJson.getString("nextblockhash");
        return nextblockhash;
    }

    @Override
    public void syncBlocks(String fromBlockhash) {
        String tempBlockhash = fromBlockhash;
        while (tempBlockhash != null){
            tempBlockhash = syncBlock(tempBlockhash);
        }
    }
}
