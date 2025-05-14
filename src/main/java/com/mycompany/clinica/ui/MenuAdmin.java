package com.mycompany.clinica.ui;

import com.mycompany.clinica.services.AgendaService;
import com.mycompany.clinica.services.BuscadorCitas;
import java.time.LocalDate;
import java.util.Scanner;

public class MenuAdmin {
    private final AgendaService agenda;
    private final Scanner sc;
    private final BuscadorCitas buscador;

    public MenuAdmin(AgendaService agenda, Scanner sc, BuscadorCitas buscador) {
        this.agenda = agenda;
        this.sc = sc;
        this.buscador = buscador;
    }

    public void mostrar() {
        System.out.println("\n--- MENÚ ADMINISTRADOR ---");
        System.out.println("1. Buscar por médico");
        System.out.println("2. Buscar por paciente");
        System.out.println("3. Buscar por fecha");
        System.out.print("Opción: ");
        
        switch (sc.nextLine()) {
            case "1" -> buscarPorMedico();
            case "2" -> buscarPorPaciente();
            case "3" -> buscarPorFecha();
            default -> System.out.println("Opción inválida");
        }
    }

    private void buscarPorMedico() {
        System.out.print("ID médico: ");
        int id = Integer.parseInt(sc.nextLine());
        buscador.buscarPorMedico(id).forEach(System.out::println);
    }

    private void buscarPorPaciente() {
        System.out.print("ID paciente: ");
        int id = Integer.parseInt(sc.nextLine());
        buscador.buscarPorPaciente(id).forEach(System.out::println);
    }

    private void buscarPorFecha() {
        System.out.print("Fecha (yyyy-MM-dd): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine());
        buscador.buscarPorFecha(fecha).forEach(System.out::println);
    }
}