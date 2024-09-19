package com.vid.envio_correos.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@//serorapru12:1539/pruvid12.congregacion.local";
        String user = "PRAPPVID";
        String password = "PRAppVID$*";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos!");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
