package com.example.blockchainms.Service;

import com.example.blockchainms.Entity.Block;
import com.example.blockchainms.Repository.BlockChainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BlockchainService {

    private List<Block> blockchain = new ArrayList<>();
    @Autowired
    private BlockChainRepository blockChainRepository;
    private int difficulty = 4;

    public BlockchainService() {
        //  le bloc générique (bloc Lowl li kaykon dima fixe)
        blockchain.add(createGenesisBlock());
    }
    private Block createGenesisBlock() {
        return new Block(0L, UUID.randomUUID(), "0", BigDecimal.ZERO, UUID.randomUUID(), UUID.randomUUID());
    }

    public void addBlock(Block newBlock) {
        try {
            newBlock.mineBlock(difficulty);
            blockchain.add(newBlock);
            blockChainRepository.save(newBlock);
        } catch (Exception e) {
            throw new RuntimeException("Error while adding block: " + e.getMessage(), e);
        }

    }
    // Vérification de l'intégrité de la chaîne
    public boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;  // Hash calculé ne correspond pas
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;  // Le lien entre les blocs est rompu
            }
        }
        return true;
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }
    public Block getLastBlock() {
        return blockchain.isEmpty() ? null : blockchain.get(blockchain.size() - 1);
    }


}
