package com.SistemaDeReserva.mspedidos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Clase de Entrada Principal - MsPedidosApplication.java
 * 
 * Descripcion: Clase de inicio principal para arrancar la aplicación Spring Boot del microservicio mspedidos.
 * 
 * @author Sistema de Reservas de Restaurante
 * @version 1.0.0
 */
@SpringBootApplication
public class MsPedidosApplication {
    /**
     * Ejecuta un proceso dentro de la capa arquitectonica.
     */
    public static void main(String[] args){SpringApplication.run(MsPedidosApplication.class,args);}
}
