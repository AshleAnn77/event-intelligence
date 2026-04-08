package com.event.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import com.event.model.Event;

public class EventRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/eventdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Liyastanly@25.";

    public void saveEvents(List<Event> events) {

        String query = "INSERT INTO events (id, title, description, date, location, source, type, tags, entry_price, score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            for (Event e : events) {

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, e.getId());
                stmt.setString(2, e.getTitle());
                stmt.setString(3, e.getDescription());
                stmt.setDate(4, java.sql.Date.valueOf(e.getDate()));
                stmt.setString(5, e.getLocation());
                stmt.setString(6, e.getSource());
                stmt.setString(7, e.getType());

                // Convert List<String> → CSV
                stmt.setString(8, String.join(",", e.getTags()));

                stmt.setDouble(9, e.getEntryPrice());
                stmt.setDouble(10, e.getScore());

                stmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}