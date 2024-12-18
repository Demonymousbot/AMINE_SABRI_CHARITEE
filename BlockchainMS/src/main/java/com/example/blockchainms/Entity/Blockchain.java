package com.example.blockchainms.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Blockchain {
    
        private List<Block> chain;
        private int difficulty; // Difficulté pour le minage (nombre de zéros requis)

        // Initialisation de la blockchain
        public Blockchain(int difficulty) {
            this.chain = new ArrayList<>();
            this.difficulty = difficulty;
            chain.add(createGenesisBlock());
        }

        // Création du Genesis Block
        private Block createGenesisBlock() {
            return new Block(1L, UUID.randomUUID(), "0", BigDecimal.ZERO, UUID.randomUUID(), UUID.randomUUID());
        }

        // Obtenir le dernier bloc de la chaîne
        public Block getLastBlock() {
            return chain.get(chain.size() - 1);
        }

        // Ajouter un nouveau bloc à la chaîne
        public void addBlock(UUID donId, BigDecimal amount, UUID senderUserId, UUID receiverOrganisationId) {
            Block previousBlock = getLastBlock();
            Block newBlock = new Block((long) chain.size() + 1, donId, previousBlock.getHash(), amount, senderUserId, receiverOrganisationId);

            // Miner le bloc avant de l'ajouter
            newBlock.mineBlock(difficulty);
            chain.add(newBlock);
        }

        // Vérifier la validité de la blockchain
        public boolean isValidChain() {
            for (int i = 1; i < chain.size(); i++) {
                Block currentBlock = chain.get(i);
                Block previousBlock = chain.get(i - 1);

                // Vérifier l'intégrité des données et des hash
                if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                    System.out.println("Le hash du bloc " + currentBlock.getBlockId() + " est invalide.");
                    return false;
                }

                if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                    System.out.println("Le hash précédent du bloc " + currentBlock.getBlockId() + " ne correspond pas.");
                    return false;
                }
            }
            return true;
        }

        // Afficher la blockchain
        public void printBlockchain() {
            for (Block block : chain) {
                System.out.println(block);
            }
        }

        // Obtenir la liste des blocs
        public List<Block> getChain() {
            return chain;
        }
}
