package br.com.estoque.load;

import br.com.estoque.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class TransactionLoader {

    private static final int BATCH_SIZE = 5000;

    public static void loadTransactions(int totalTransactions, int totalProducts) {
        String sql = "INSERT INTO transactions (product_id, transaction_type, quantity) VALUES (?, ?, ?)";
        Random random = new Random();

        long start = System.currentTimeMillis();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (int i = 1; i <= totalTransactions; i++) {
                int productId = random.nextInt(totalProducts) + 1;
                String type = random.nextBoolean() ? "IN" : "OUT";
                int quantity = random.nextInt(50) + 1; // até 50 itens por transação

                pstmt.setInt(1, productId);
                pstmt.setString(2, type);
                pstmt.setInt(3, quantity);
                pstmt.addBatch();

                if (i % BATCH_SIZE == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    pstmt.clearBatch();
                    System.out.println("Inseridas " + i + " transações...");
                }
            }

            pstmt.executeBatch();
            conn.commit();

            long end = System.currentTimeMillis();
            System.out.println("Inserção de " + totalTransactions + " transações concluída em " + (end - start) / 1000.0 + "s");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

