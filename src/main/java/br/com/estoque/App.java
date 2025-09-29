package br.com.estoque;

import br.com.estoque.load.ProductLoader;
import br.com.estoque.load.InventoryLoader;
import br.com.estoque.load.TransactionLoader;

public class App {

    public static void main(String[] args) {
        int totalProducts = 100000;
        int totalTransactions = 1900000;

        System.out.println("Iniciando carga de dados...");

        ProductLoader.loadProducts(totalProducts);
        InventoryLoader.loadInventory(totalProducts);
        TransactionLoader.loadTransactions(totalTransactions, totalProducts);

        System.out.println("Carga de dados concluída!");
    }
}

