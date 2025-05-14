// MenuPaciente.java
package com.mycompany.clinica.ui;

import com.mycompany.clinica.models.Cita;
import com.mycompany.clinica.models.Medico;
import com.mycompany.clinica.models.Paciente;
import com.mycompany.clinica.services.AgendaService;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MenuPaciente {
    private final AgendaService agenda;
    private final Scanner sc;

    public MenuPaciente(AgendaService agenda, Scanner sc) {
        this.agenda = agenda;
        this.sc = sc;
    }

    public void mostrar() {
        Paciente paciente = obtenerDatosPaciente();
        
        System.out.println("1. Agendar cita\n2. Cancelar cita");
        switch (sc.nextLine()) {
            case "1"-> agendarCita(paciente);
            case "2"-> cancelarCita(paciente);
            default-> System.out.println("Opción inválida");
        }
    }

    private Paciente obtenerDatosPaciente() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Edad: ");
        int edad = Integer.parseInt(sc.nextLine());
        return new Paciente(id, nombre, edad);
    }

    private void agendarCita(Paciente paciente) {
        System.out.print("ID médico: ");
        int idMedico = Integer.parseInt(sc.nextLine());
        System.out.print("Nombre médico: ");
        String nombreMedico = sc.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = sc.nextLine();
        System.out.print("Fecha y hora (yyyy-MM-ddTHH:mm): ");
        LocalDateTime fecha = LocalDateTime.parse(sc.nextLine());

        Medico medico = new Medico(idMedico, nombreMedico, especialidad);
        agenda.agendar(new Cita(medico, paciente, fecha));
    }

    private void cancelarCita(Paciente paciente) {
        System.out.print("Fecha y hora a cancelar (yyyy-MM-ddTHH:mm): ");
        LocalDateTime fecha = LocalDateTime.parse(sc.nextLine());
        agenda.cancelarCita(paciente.getId(), fecha);
    }
}