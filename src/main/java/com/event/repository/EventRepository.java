package com.event.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import com.event.model.Event;

public class EventRepository {

    private static final String URL = "jdbc:mysql://maglev.proxy.rlwy.net:44750/railway?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "jsSmrEtynLwjZVxdILaGEiNiCLnpslkZ";

    public void testConnection() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ SUCCESS: Connected to Railway DB!");
        } catch (Exception e) {
            System.out.println("❌ FAILED: Cannot connect to DB");
            e.printStackTrace();
        }
    }

    public void saveEvents(List<Event> events) {

        String query = "INSERT INTO events (id, title, description, date, location, source, type, tags, entry_price, score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            System.out.println("✅ Connected to Railway DB!");

            for (Event e : events) {

                stmt.setString(1, e.getId());
                stmt.setString(2, e.getTitle());
                stmt.setString(3, e.getDescription());
                stmt.setDate(4, java.sql.Date.valueOf(e.getDate()));
                stmt.setString(5, e.getLocation());
                stmt.setString(6, e.getSource());
                stmt.setString(7, e.getType());
                stmt.setString(8, String.join(",", e.getTags()));
                stmt.setDouble(9, e.getEntryPrice());
                stmt.setDouble(10, e.getScore());

                stmt.executeUpdate();
            }

            System.out.println("Events inserted successfully!");

        } catch (Exception e) {
            System.out.println(" DB Error:");
            e.printStackTrace();
        }
    }
}