package br.com.estoque.load;

import br.com.estoque.db.DatabaseConnection;
import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

public class ProductLoader {

    private static final int BATCH_SIZE = 5000;

    public static void loadProducts(int totalProducts) {
        Faker faker = new Faker(new Locale("en-US"));
        String sql = "INSERT INTO products (name, description, unit_price) VALUES (?, ?, ?)";

        long start = System.currentTimeMillis();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (int i = 1; i <= totalProducts; i++) {
                String name = faker.commerce().productName();
                String description = faker.lorem().sentence();
                double price = Double.parseDouble(faker.commerce().price().replace(",", "."));

                pstmt.setString(1, name);
                pstmt.setString(2, description);
                pstmt.setDouble(3, price);
                pstmt.addBatch();

                if (i % BATCH_SIZE == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    pstmt.clearBatch();
                    System.out.println("Inseridos " + i + " produtos...");
                }
            }

            pstmt.executeBatch();
            conn.commit();

            long end = System.currentTimeMillis();
            System.out.println("Inserção de " + totalProducts + " produtos concluída em " + (end - start) / 1000.0 + "s");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

