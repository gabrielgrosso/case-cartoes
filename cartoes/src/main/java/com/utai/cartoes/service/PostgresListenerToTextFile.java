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
        // Nome do arquivo .txt que será gerado
        String fileName = "C:\\Users\\GAL1L\\Desktop\\case-cartoes\\cartoes\\src\\main\\resources\\" +
                "export-txt\\cartoes.txt";

        // Conectar ao banco e ouvir por notificações
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {

            PGConnection pgConnection = (PGConnection) connection;

            // Escuta no canal 'new_cartao'
            stmt.execute("LISTEN new_cartao");

            System.out.println("Escutando por novos cartões...");

            while (true) {
                // Espera por uma notificação no canal 'new_cartao'
                pgConnection.getNotifications();

                // Quando a notificação for recebida, gera o arquivo .txt
                generateTextFile(fileName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gera o arquivo .txt com os dados da tabela
    public static void generateTextFile(String fileName) {
        String query = "SELECT * FROM cartoes";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

                // Escrever os dados no arquivo .txt
                while (rs.next()) {
                    String id = rs.getString("id");
                    int bandeira = rs.getInt("bandeira");
                    int nivelCartao = rs.getInt("nivel_cartao");
                    String nome = rs.getString("nome");

                    // Formatar os dados para escrever no arquivo .txt
                    String line = String.format("ID: %s, Bandeira: %d, Nível: %d, Nome: %s", id, bandeira, nivelCartao, nome);

                    // Escrever a linha no arquivo
                    writer.write(line);
                    writer.newLine(); // Nova linha após cada entrada
                }

                System.out.println("Arquivo .txt gerado com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
