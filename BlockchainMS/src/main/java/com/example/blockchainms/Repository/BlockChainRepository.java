package com.example.blockchainms.Repository;

import com.example.blockchainms.Entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockChainRepository extends JpaRepository<Block , Long> {
}
