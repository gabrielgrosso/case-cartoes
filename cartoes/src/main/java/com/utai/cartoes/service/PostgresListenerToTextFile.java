package com.utai.cartoes.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.postgresql.PGConnection;

public class PostgresListenerToTextFile {

    // Detalhes de conexão com o banco
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        String fileName = "C:\\Users\\GAL1L\\Desktop\\case-cartoes\\cartoes\\src\\main\\resources\\" +
                "export-txt\\cartoes.txt";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {

            PGConnection pgConnection = (PGConnection) connection;

            stmt.execute("LISTEN new_cartao");

            System.out.println("Escutando por novos cartões...");

            while (true) {
                pgConnection.getNotifications();

                generateTextFile(fileName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateTextFile(String fileName) {
        String query = "SELECT * FROM cartoes";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

                while (rs.next()) {
                    String id = rs.getString("id");
                    int bandeira = rs.getInt("bandeira");
                    int nivelCartao = rs.getInt("nivel_cartao");
                    String nome = rs.getString("nome");

                    String line = String.format("ID: %s, Bandeira: %d, Nível: %d, Nome: %s", id, bandeira, nivelCartao, nome);

                    writer.write(line);
                    writer.newLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
