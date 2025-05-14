package com.mycompany.clinica.validadores;

import com.mycompany.clinica.models.Cita;
import java.util.List;

public interface ValidadorDeCitas {
    
    public boolean esValida(Cita nuevaCita, List<Cita> citasExistentes);
}