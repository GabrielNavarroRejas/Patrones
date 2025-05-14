package com.mycompany.clinica.storage;

import com.mycompany.clinica.models.Cita;

import java.util.List;

public interface Repositorio {
    void guardar(List<Cita> citas);
    List<Cita> cargar();
}
