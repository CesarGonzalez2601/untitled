package org.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User authenticate(String usuario, String contraseña) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("usuario"), rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
