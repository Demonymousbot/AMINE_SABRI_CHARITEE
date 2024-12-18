package com.example.donms.feign;

import com.example.donms.dto.BlockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "BlockchainMs" , url = "http://localhost:8089/api/blockchain")
public interface BlockchainFeignClient {
    // Récupérer la liste de tous les blocs
    @GetMapping("/all")
    List<BlockDTO> getAllBlocks();

    // Récupérer un bloc par son ID
    @GetMapping("/{id}")
    BlockDTO getBlockById(@PathVariable("id") Long blockId);

    // Méthode pour ajouter un nouveau bloc à la blockchain
    // @PostMapping("/add-block")
   // void addBlock(@RequestBody BlockDTO blockDTO);
    @PostMapping("/add-block")
    public String addBlock(@RequestBody BlockDTO blockDTO);

    // Méthode pour récupérer le dernier bloc de la blockchain
    @GetMapping("/last-block")
    BlockDTO getLastBlock();
}
