package com.mycompany.clinica.storage;

import com.mycompany.clinica.models.Cita;
import com.mycompany.clinica.models.Medico;
import com.mycompany.clinica.models.Paciente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class RepositorioArchivo implements Repositorio {

    private static final String URL = "jdbc:postgresql://localhost:5432/Clinica";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    @Override
    public void guardar(Cita cita) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Insertar médico (si no existe)
            String sqlMedico = "INSERT INTO medicos (id, nombre, especialidad) VALUES (?, ?, ?) ON CONFLICT (id) DO NOTHING";
            try (PreparedStatement ps = conn.prepareStatement(sqlMedico)) {
                ps.setInt(1, cita.getMedico().getId());
                ps.setString(2, cita.getMedico().getNombre());
                ps.setString(3, cita.getMedico().getEspecialidad());
                ps.executeUpdate();
            }

            // Insertar paciente (si no existe)
            String sqlPaciente = "INSERT INTO pacientes (id, nombre, edad) VALUES (?, ?, ?) ON CONFLICT (id) DO NOTHING";
            try (PreparedStatement ps = conn.prepareStatement(sqlPaciente)) {
                ps.setInt(1, cita.getPaciente().getId());
                ps.setString(2, cita.getPaciente().getNombre());
                ps.setInt(3, cita.getPaciente().getEdad());
                ps.executeUpdate();
            }

            // Insertar cita
            String sqlCita = "INSERT INTO citas (id_medico, id_paciente, fecha_hora) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlCita)) {
                ps.setInt(1, cita.getMedico().getId());
                ps.setInt(2, cita.getPaciente().getId());
                ps.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar en la base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Cita> cargar() {
        List<Cita> citas = new ArrayList<>();

        String sql = "SELECT c.fecha_hora, " +
                     "m.id AS id_medico, m.nombre AS nombre_medico, m.especialidad, " +
                     "p.id AS id_paciente, p.nombre AS nombre_paciente, p.edad " +
                     "FROM citas c " +
                     "JOIN medicos m ON c.id_medico = m.id " +
                     "JOIN pacientes p ON c.id_paciente = p.id";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre_medico"),
                        rs.getString("especialidad"));

                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre_paciente"),
                        rs.getInt("edad"));

                LocalDateTime fecha = rs.getTimestamp("fecha_hora").toLocalDateTime();

                citas.add(new Cita(medico, paciente, fecha));
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar desde la base de datos: " + e.getMessage());
        }

        return citas;
    }

    @Override
    public boolean eliminar(int idPaciente, LocalDateTime fechaHora) {
        String sql = "DELETE FROM citas WHERE id_paciente = ? AND fecha_hora = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            stmt.setTimestamp(2, Timestamp.valueOf(fechaHora));

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar cita en PostgreSQL: " + e.getMessage());
            return false;
        }
    }
}
