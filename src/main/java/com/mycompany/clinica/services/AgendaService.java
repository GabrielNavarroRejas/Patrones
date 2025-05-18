package com.mycompany.clinica.services;

import com.mycompany.clinica.models.Cita;
import com.mycompany.clinica.storage.Repositorio;
import com.mycompany.clinica.validadores.ValidadorBase;
import com.mycompany.clinica.validadores.ValidadorDeCitas;

import java.time.LocalDateTime;
import java.util.List;

public class AgendaService {
    private final Repositorio repositorio;
    private ValidadorDeCitas validadorChain;

    public AgendaService(Repositorio repositorio) {
        this.repositorio = repositorio;
        this.validadorChain = new ValidadorBase(); // Inicia con validación base
    }

    // Método para agregar decoradores externamente
    public void agregarValidador(ValidadorDeCitas decorador) {
        this.validadorChain = decorador;
    }

    public void agendar(Cita nuevaCita) {
        List<Cita> citasExistentes = repositorio.cargar();
        if (validadorChain.esValida(nuevaCita, citasExistentes)) {
            repositorio.guardar(nuevaCita);
            System.out.println("Cita agendada correctamente para " + nuevaCita.getPaciente().getNombre());
        } else {
            System.out.println("Este horario se cruza con otra cita");
        }
    }

    public boolean cancelarCita(int idPaciente, LocalDateTime fechaHora) {
        boolean cancelada = repositorio.eliminar(idPaciente, fechaHora);
        if (cancelada) {
            System.out.println("Cita cancelada.");
        } else {
            System.out.println("No se encontró la cita para cancelar.");
        }
        return cancelada;
    }

    public List<Cita> listar() {
        return repositorio.cargar();
    }
}


