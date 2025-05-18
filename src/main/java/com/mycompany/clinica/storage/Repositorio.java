package com.mycompany.clinica.storage;

import com.mycompany.clinica.models.Cita;

import java.time.LocalDateTime;
import java.util.List;

public interface Repositorio {
    void guardar(Cita cita);       
    List<Cita> cargar();
    boolean eliminar(int idPaciente, LocalDateTime fechaHora);
}
