package com.example.blockchainms.Contorller;

import com.example.blockchainms.Entity.Block;
import com.example.blockchainms.Repository.BlockChainRepository;
import com.example.blockchainms.Service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;
  @Autowired
  private BlockChainRepository blockChainRepository;
    @GetMapping("/blocks")
    public Object getBlockchain() {
        return blockchainService.getBlockchain();
    }

    @PostMapping("/add-block")
    public String addBlock(@RequestBody Block newBlock) {
        blockchainService.addBlock(newBlock);
        return "Block added successfully!";
    }


    @GetMapping("/isValid")
    public String isChainValid() {
        return blockchainService.isChainValid() ? "Valid" : "Invalid";
    }

    @GetMapping("/last-block") // Ajoutez cet endpoint pour gérer la requête Feign
    public Block getLastBlock() {
        return blockchainService.getLastBlock();  // Logique pour retourner le dernier bloc
    }
    @GetMapping("/all")
    public List<Block> getall(){
        return blockChainRepository.findAll();
    }

}
