package com.mycompany.clinica.ui;

import com.mycompany.clinica.services.AgendaService;
import com.mycompany.clinica.services.BuscadorCitas;
import com.mycompany.clinica.services.BuscadorDeCitas;
import java.util.Scanner;

public class Menu {
    private final AgendaService agenda;
    private final Scanner sc = new Scanner(System.in);

    public Menu(AgendaService agenda) {
        this.agenda = agenda;
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- SISTEMA DE CITAS ---");
            System.out.println("1. Soy paciente");
            System.out.println("2. Soy administrador");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            
            switch (sc.nextLine()) {
                case "1" -> new MenuPaciente(agenda, sc).mostrar();
                case "2" -> loginAdmin();
                case "0" -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void loginAdmin() {
        System.out.print("Usuario: ");
        String user = sc.nextLine();
        System.out.print("Clave: ");
        String pass = sc.nextLine();

        if (user.equals("admin") && pass.equals("admin123")) {
            // Crear el buscador con las citas actuales
            BuscadorCitas buscador = new BuscadorDeCitas(agenda.listar());
            new MenuAdmin(agenda, sc, buscador).mostrar();
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }
}