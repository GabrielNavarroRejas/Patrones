/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.clinica;

/**
 *
 * @author LAB-USR-ICA
 */

import com.mycompany.clinica.services.AgendaService;
import com.mycompany.clinica.storage.RepositorioArchivo;
import com.mycompany.clinica.ui.Menu;
import com.mycompany.clinica.validadores.DisponibilidadPorHorarioMedicoDecorator;
import com.mycompany.clinica.validadores.ValidadorBase;
import com.mycompany.clinica.validadores.ValidadorDeCitas;

/*
 * El principio Open/Closed dice que una clase debe estar abierta para extensión pero cerrada para 
 * modificación. Aplicamos OCP en:

 * En todo el sistema:
 * Repositorio es una interfaz: Se puede agregar más implementaciones (RepositorioMemoria, 
 * RepositorioMySQL, etc.) sin modificar AgendaService.

 * AgendaService no conoce los detalles del guardado. Solo usa Repositorio (depende de la abstracción).

 * Si más adelante deseamos validaciones adicionales (días festivos, doctores de vacaciones, etc.), 
 * podremos extender con una clase ValidadorDeCitas sin tocar AgendaService.
 * 
 * Open/Closed Principle:

 * Puedes crear nuevos tipos de buscadores implementando la interfaz BuscadorCitas

 * Ejemplo: BuscadorAvanzado con filtros combinados sin modificar el MenuAdmin

 * Inversión de dependencias:

 * El MenuAdmin depende de una abstracción (BuscadorCitas) no de una implementación concreta

 * Open/Closed Principle:

 * AgendaService está cerrado para modificación (no cambia su código al añadir validadores).

 * Abierto para extensión: Puedes añadir nuevos decoradores desde fuera.

 * Implementación correcta del Decorator:

 * La cadena se construye externamente: ValidadorBase -> DisponibilidadDecorator.

 * Cada decorador envuelve al validador anterior.

 * Flexibilidad:

 * se puede añadir validadores desde cualquier parte del código:
 */

 /* Este es un testeo*/
public class Clinica {

    public static void main(String[] args) {
        /*AgendaService agenda = new AgendaService(new RepositorioArchivo("citas.txt"));


        Medico draAnaPerez = new Medico(1, "Dra. Ana Pérez", "Pediatría");
        Paciente pacienteCarlosLopez = new Paciente(1, "Carlos López", 12);
        LocalDateTime fechaCarlosLopez = LocalDateTime.of(2025, 5, 1, 10, 30);

        Cita citaCarlosLopez = new Cita(draAnaPerez, pacienteCarlosLopez, fechaCarlosLopez);
        agenda.agendar(citaCarlosLopez);

        Paciente pacienteAbdielDiaz = new Paciente(2, "Abdiel Diaz", 18);
        LocalDateTime fechaAbdielDiaz = LocalDateTime.of(2025, 5, 1, 10, 30);

        Cita citaAbdielDiaz = new Cita(draAnaPerez, pacienteAbdielDiaz, fechaAbdielDiaz);
        agenda.agendar(citaAbdielDiaz);

        System.out.println("Citas agendadas:");
        for (Cita c : agenda.listar()) {
            System.out.println(c);
        }*/

        AgendaService servicio = new AgendaService(new RepositorioArchivo("citas.txt"));
        
        // Construir cadena de validadores
        ValidadorDeCitas validador = new ValidadorBase();
        validador = new DisponibilidadPorHorarioMedicoDecorator(validador);
        
        servicio.agregarValidador(validador);
        
        Menu menu = new Menu(servicio);
        menu.iniciar();
    }
}
