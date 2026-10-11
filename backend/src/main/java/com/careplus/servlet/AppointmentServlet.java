package com.careplus.servlet;

import com.careplus.config.Database;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@WebServlet("/api/appointments")
public class AppointmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String patientName = request.getParameter("patientName");
        String patientEmail = request.getParameter("patientEmail");
        String doctorText = request.getParameter("doctorId");
        String dateText = request.getParameter("appointmentDateTime");
        String reason = request.getParameter("reason");

        if (blank(patientName) || blank(patientEmail) || blank(doctorText) || blank(dateText)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"patientName, patientEmail, doctorId, and appointmentDateTime are required\"}");
            return;
        }

        final int doctorId;
        final LocalDateTime dateTime;
        try {
            doctorId = Integer.parseInt(doctorText);
            dateTime = LocalDateTime.parse(dateText);
        } catch (NumberFormatException | DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid doctorId or date format; use yyyy-MM-ddTHH:mm:ss\"}");
            return;
        }

        String sql = "INSERT INTO appointments (patient_name, patient_email, doctor_id, appointment_datetime, reason, status) VALUES (?, ?, ?, ?, ?, 'PENDING')";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patientName.trim());
            statement.setString(2, patientEmail.trim());
            statement.setInt(3, doctorId);
            statement.setTimestamp(4, Timestamp.valueOf(dateTime));
            statement.setString(5, reason == null ? "" : reason.trim());
            statement.executeUpdate();
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\":\"Appointment saved\",\"status\":\"PENDING\"}");
        } catch (SQLException e) {
            getServletContext().log("Could not save appointment", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Could not save appointment; check database settings and doctorId\"}");
        }
    }

    private static boolean blank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
