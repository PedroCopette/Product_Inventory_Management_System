package br.com.estoque.load;

import br.com.estoque.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class InventoryLoader {

    private static final int BATCH_SIZE = 5000;

    public static void loadInventory(int totalProducts) {
        String sql = "INSERT INTO inventory (product_id, quantity) VALUES (?, ?)";
        Random random = new Random();

        long start = System.currentTimeMillis();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (int i = 1; i <= totalProducts; i++) {
                int quantity = random.nextInt(500) + 1; // estoque inicial de 1 a 500

                pstmt.setInt(1, i);
                pstmt.setInt(2, quantity);
                pstmt.addBatch();

                if (i % BATCH_SIZE == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    pstmt.clearBatch();
                    System.out.println("Estoque inicial inserido para " + i + " produtos...");
                }
            }

            pstmt.executeBatch();
            conn.commit();

            long end = System.currentTimeMillis();
            System.out.println("Estoque inicial carregado em " + (end - start) / 1000.0 + "s");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

