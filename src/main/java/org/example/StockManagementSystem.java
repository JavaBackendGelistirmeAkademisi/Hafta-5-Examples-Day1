package org.example;

import java.sql.*;
import java.util.Scanner;

public class StockManagementSystem {

    // Veritabanı bağlantı bilgileri
    private static final String URL = "jdbc:mysql://localhost:3306/stock_management";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Stok Takip Sistemine Hoş Geldiniz!");

            boolean continueProgram = true;

            while (continueProgram) {
                System.out.println("\nYapmak istediğiniz işlemi seçin:");
                System.out.println("1. Ürün Ekle");
                System.out.println("2. Ürünleri Listele");
                System.out.println("3. Ürün Güncelle");
                System.out.println("4. Ürün Sil");
                System.out.println("5. Düşük Stokları Kontrol Et");
                System.out.println("6. Çıkış");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Scanner hatasını önlemek için

                switch (choice) {
                    case 1:
                        addProduct(connection, scanner);
                        break;
                    case 2:
                        listProducts(connection);
                        break;
                    case 3:
                        updateProduct(connection, scanner);
                        break;
                    case 4:
                        deleteProduct(connection, scanner);
                        break;
                    case 5:
                        checkLowStock(connection);
                        break;
                    case 6:
                        continueProgram = false;
                        System.out.println("Sistemden çıkılıyor...");
                        break;
                    default:
                        System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Veritabanı hatası: " + e.getMessage());
        }
    }

    // Yeni ürün ekleme metodu
    private static void addProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Yeni ürün adını girin:");
        String productName = scanner.nextLine();
        System.out.println("Ürün kategorisini girin:");
        String category = scanner.nextLine();
        System.out.println("Ürün fiyatını girin:");
        double price = scanner.nextDouble();
        System.out.println("Ürün stok miktarını girin:");
        int stock = scanner.nextInt();

        String insertSQL = "INSERT INTO products (product_name, category, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, category);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, stock);
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(rowsInserted + " ürün eklendi.");
        }
    }

    // Ürünleri listeleme metodu
    private static void listProducts(Connection connection) throws SQLException {
        String selectSQL = "SELECT * FROM products";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            System.out.println("Ürün Listesi:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String productName = resultSet.getString("product_name");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                System.out.println("ID: " + id + ", Ürün Adı: " + productName + ", Kategori: " + category + ", Fiyat: " + price + ", Stok: " + stock);
            }
        }
    }

    // Ürün güncelleme metodu
    private static void updateProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Güncellemek istediğiniz ürünün ID'sini girin:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Scanner hatasını önlemek için

        System.out.println("Yeni ürün adı girin:");
        String newProductName = scanner.nextLine();
        System.out.println("Yeni kategori girin:");
        String newCategory = scanner.nextLine();
        System.out.println("Yeni fiyat girin:");
        double newPrice = scanner.nextDouble();
        System.out.println("Yeni stok miktarı girin:");
        int newStock = scanner.nextInt();

        String updateSQL = "UPDATE products SET product_name = ?, category = ?, price = ?, stock = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, newProductName);
            preparedStatement.setString(2, newCategory);
            preparedStatement.setDouble(3, newPrice);
            preparedStatement.setInt(4, newStock);
            preparedStatement.setInt(5, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println(rowsUpdated + " ürün güncellendi.");
        }
    }

    // Ürün silme metodu
    private static void deleteProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Silmek istediğiniz ürünün ID'sini girin:");
        int id = scanner.nextInt();

        String deleteSQL = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted + " ürün silindi.");
        }
    }

    // Düşük stokları kontrol etme metodu
    private static void checkLowStock(Connection connection) throws SQLException {
        String lowStockSQL = "SELECT * FROM products WHERE stock < 10";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(lowStockSQL)) {
            System.out.println("Düşük Stokta Olan Ürünler:");
            while (resultSet.next()) {
                String productName = resultSet.getString("product_name");
                int stock = resultSet.getInt("stock");
                System.out.println("Ürün Adı: " + productName + ", Stok: " + stock);
            }
        }
    }
}
