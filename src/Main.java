import java.util.Scanner;

public class Main {
    private static final Scanner ENTRADA = new Scanner(System.in);
    private static final Renta CONTROLADOR = new Renta();
 
    public static void main(String[] args) {
        CONTROLADOR.cargarDatosIniciales();
        System.out.println("RentaMovil - Sistema de alquiler de vehículos");
 
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String linea = leerLinea("Seleccione una opción: ");
            int opcion;
            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                opcion = -1;
            }
 
            switch (opcion) {
                case 1:
                    solicitarDatosRegistro();
                    break;
                case 2:
                    mostrarFlota();
                    break;
                case 3:
                    procesarCotizacion();
                    break;
                case 4:
                    procesarAlquiler();
                    break;
                case 5:
                    procesarDevolucion();
                    break;
                case 6:
                    mostrarReporte();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Gracias por usar el sistema");
                    break;
                default:
                    System.out.println("Opción inválida. Elija un número del menú");
            }
        }
        ENTRADA.close();
    }
 
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("MENÚ");
        System.out.println("1. Registrar vehículo");
        System.out.println("2. Consultar flota");
        System.out.println("3. Cotizar alquiler");
        System.out.println("4. Confirmar alquiler");
        System.out.println("5. Registrar devolución");
        System.out.println("6. Reporte general");
        System.out.println("0. Salir");
    }
 
    private static void solicitarDatosRegistro() {
        System.out.println("Registro de vehículo");
        System.out.println("Categoría: 1) Automóvil  2) Motocicleta  3) Camioneta de carga");
        int categoria = leerEntero("Seleccione la categoría: ");
        if (categoria < 1 || categoria > 3) {
            System.out.println("Error: categoría inválida. No se registró ningún vehículo");
            return;
        }
 
        String placa = leerLinea("Placa: ");
        String marca = leerLinea("Marca: ");
        String modelo = leerLinea("Modelo: ");
        double tarifa = leerDecimal("Tarifa diaria (Q): ");
        try {
            boolean registrado;
            if (categoria == 1) {
                int pasajeros = leerEntero("Cantidad de pasajeros: ");
                boolean automatico = leerTransmision();
                registrado = CONTROLADOR.registrarAutomovil(placa, marca, modelo, tarifa, pasajeros, automatico);
            } else if (categoria == 2) {
                int cilindraje = leerEntero("Cilindraje (cc): ");
                registrado = CONTROLADOR.registrarMotocicleta(placa, marca, modelo, tarifa, cilindraje);
            } else {
                double capacidad = leerDecimal("Capacidad máxima de carga (toneladas): ");
                registrado = CONTROLADOR.registrarCamioneta(placa, marca, modelo, tarifa, capacidad);
            }
            System.out.println(registrado
                    ? "Vehículo registrado correctamente y disponible para alquiler."
                    : "No se pudo registrar el vehículo.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage() + " No se registró el vehículo.");
        }
    }
 
    private static void mostrarFlota() {
        System.out.println();
        System.out.println(CONTROLADOR.listarFlota());
    }
 
    private static void procesarCotizacion() {
        System.out.println("Cotización");
        String placa = leerLinea("Placa del vehículo: ");
        int dias = leerEntero("Cantidad de días: ");
        try {
            System.out.println();
            System.out.println(CONTROLADOR.cotizar(placa, dias));
            System.out.println("Aún no se realizó ningún alquiler");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
 
    private static void procesarAlquiler() {
        System.out.println("Confirmar alquiler");
        String placa = leerLinea("Placa del vehículo: ");
        int dias = leerEntero("Cantidad de días: ");
        try {
            CONTROLADOR.verificarAlquiler(placa, dias); 
            System.out.println();
            System.out.println(CONTROLADOR.cotizar(placa, dias));
 
            String respuesta = leerLinea("¿Confirmar el alquiler y cobrar el total?");
            if (respuesta.equalsIgnoreCase("S")) {
                if (CONTROLADOR.confirmarAlquiler(placa, dias)) {
                    System.out.println("Alquiler confirmado. El vehículo quedó ocupado");
                    System.out.println("Ingresos acumulados: " + CONTROLADOR.ingresosFormateados());
                }
            } else {
                System.out.println("Alquiler cancelado. No se realizaron cambios");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage() + " No se realizaron cambios");
        }
    }
 
    private static void procesarDevolucion() {
        System.out.println("Registrar devolución");
        String placa = leerLinea("Placa del vehículo: ");
        try {
            CONTROLADOR.registrarDevolucion(placa);
            System.out.println("Devolución registrada. El vehículo está disponible nuevamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage() + " No se realizaron cambios");
        }
    }
 
    private static void mostrarReporte() {
        System.out.println();
        System.out.println(CONTROLADOR.generarReporte());
    }
 
    // ---------- Lectura segura de datos ----------
 
    private static String leerLinea(String mensaje) {
        System.out.print(mensaje);
        if (!ENTRADA.hasNextLine()) {
            System.out.println("Entrada finalizada");
            System.exit(0);
        }
        return ENTRADA.nextLine().trim();
    }
 
    private static int leerEntero(String mensaje) {
        while (true) {
            String texto = leerLinea(mensaje);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: ingrese un número entero");
            }
        }
    }
 
    private static double leerDecimal(String mensaje) {
        while (true) {
            String texto = leerLinea(mensaje).replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                    throw new NumberFormatException();
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: ingrese un número (puede tener decimales)");
            }
        }
    }
 
    private static boolean leerTransmision() {
        while (true) {
            String texto = leerLinea("Transmisión (A = automática, M = manual): ");
            if (texto.equalsIgnoreCase("A")) {
                return true;
            }
            if (texto.equalsIgnoreCase("M")) {
                return false;
            }
            System.out.println("Entrada inválida: escriba A o M");
        }
    }  
}
