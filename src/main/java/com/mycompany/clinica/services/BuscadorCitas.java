package com.mycompany.clinica.services;

import com.mycompany.clinica.models.Cita;
import java.time.LocalDate;
import java.util.List;

public interface BuscadorCitas {
    List<Cita> buscarPorMedico(int idMedico);
    List<Cita> buscarPorPaciente(int idPaciente);
    List<Cita> buscarPorFecha(LocalDate fecha);
}