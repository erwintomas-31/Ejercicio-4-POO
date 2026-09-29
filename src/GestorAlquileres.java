import java.util.ArrayList;
import java.util.List;

public class GestorAlquileres {
    private final List<Vehiculo> vehiculos= new ArrayList<>();
    private double ingresosAcumulados;

    public boolean registrarVehiculo(Vehiculo v){
        if(v==null){
            throw new IllegalArgumentException("El vehículo no puede ser nulo");
        }
        if(buscarVehiculo(v.getPlaca())!=null){
            throw new IllegalArgumentException("Ya existe un vehiculo con la misma placa");
        }
        return vehiculos.add(v);
    }

    public Vehiculo buscarVehiculo(String placa) {
        String buscada = Vehiculo.ajustarPlaca(placa);
        if (buscada.isEmpty()) {
            return null;
        }
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equals(buscada)) {
                return v;
            }
        }
        return null;
    }

    public String cotizarAlquiler(String placa, int numeroDias) {
        Vehiculo v = obtenerExistente(placa);
        double total = v.calcularCostoAlquiler(numeroDias); 
        StringBuilder sb = new StringBuilder();
        sb.append(v.toString()).append('\n');
        sb.append("Días solicitados: ").append(numeroDias).append('\n');
        sb.append("Total: ").append(total);
        if (!v.estaDisponible()) {
            sb.append("\n El vehículo está ocupado y no podrá alquilarse hasta que lo devuelvan");
        }
        return sb.toString();
    }

    public void verificarAlquiler(String placa, int numeroDias) {
        Vehiculo v = obtenerExistente(placa);
        v.calcularCostoAlquiler(numeroDias); 
        if (!v.estaDisponible()) {
            throw new IllegalStateException(
                    "El vehículo " + v.getPlaca() + " ya está alquilado y no puede alquilarse de nuevo");
        }
    }

   public boolean confirmarAlquiler(String placa, int numeroDias) {
        verificarAlquiler(placa, numeroDias);
        Vehiculo v = buscarVehiculo(placa);
        double total = v.calcularCostoAlquiler(numeroDias);
        v.setDisponibilidad(false);
        ingresosAcumulados += total;
        return true;
    } 

    public void registrarDevolucion(String placa) {
        Vehiculo v = obtenerExistente(placa);
        if (v.estaDisponible()) {
            throw new IllegalStateException(
                    "El vehículo " + v.getPlaca() + " ya está disponible");
        }
        v.setDisponibilidad(true);
    }

    public String generarReporte() {
        String[] categorias = new String[vehiculos.size()];
        int[] totales = new int[vehiculos.size()];
        int[] disponiblesPorCategoria = new int[vehiculos.size()];
        int numCategorias = 0;
        int total = 0;
        int disponibles = 0;
 
        for (int i = 0; i < vehiculos.size(); i++) {
            Vehiculo v = vehiculos.get(i);
 
            int indice = -1;
            for (int j = 0; j < numCategorias; j++) {
                if (categorias[j].equals(v.getCategoria())) {
                    indice = j;
                    break;
                }
            }
            if (indice == -1) {
                indice = numCategorias;
                categorias[indice] = v.getCategoria();
                numCategorias++;
            }
 
            totales[indice]++;
            total++;
            if (v.estaDisponible()) {
                disponiblesPorCategoria[indice]++;
                disponibles++;
            }
        }
 
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE GENERAL \n");
        sb.append("Vehículos registrados: ").append(total).append('\n');
        sb.append("Disponibles: ").append(disponibles).append('\n');
        sb.append("Alquilados: ").append(total - disponibles).append("\n");
        sb.append("Por categoría:");
        for (int j = 0; j < numCategorias; j++) {
            sb.append("  - ").append(categorias[j])
            .append(": registrados=").append(totales[j])
            .append(", disponibles=").append(disponiblesPorCategoria[j])
            .append(", alquilados=").append(totales[j] - disponiblesPorCategoria[j]).append('\n');
        }
        sb.append("Ingresos acumulados: ").append(ingresosAcumulados);
        return sb.toString();
    }

    public String listarVehiculos(){
        if (vehiculos.isEmpty()) {
            return "No hay vehículos registrados.";
        }
        StringBuilder sb = new StringBuilder("FLOTA");
        for (int i = 0; i < vehiculos.size(); i++) {
            sb.append('\n').append(vehiculos.get(i).toString());
        }
        return sb.toString();
        }

    public double getIngresosAcumulados() {
        return ingresosAcumulados;
    }

    public boolean registrarAutomovil(String placa, String marca, String modelo, double tarifa,
                                  int pasajeros, boolean esAutomatico) {
        return registrarVehiculo(new Automovil(placa, marca, modelo, tarifa, pasajeros, esAutomatico));
    }

    public boolean registrarMotocicleta(String placa, String marca, String modelo, double tarifa, int cilindraje) {
        return registrarVehiculo(new Motocicleta(placa, marca, modelo, tarifa, cilindraje));
    }

    public boolean registrarCamioneta(String placa, String marca, String modelo, double tarifa, double capacidad) {
        return registrarVehiculo(new CamionetaCarga(placa, marca, modelo, tarifa, capacidad));
    }

    public void cargarDatosIniciales() {
        registrarAutomovil("P101ABC", "Toyota", "Yaris", 300.0, 4, true);   
        registrarAutomovil("P102ABC", "Honda", "Civic", 280.0, 5, false);  
        registrarMotocicleta("M201ABC", "Honda", "XR150", 120.0, 150);      
        registrarMotocicleta("M202ABC", "Kawasaki", "Ninja 400", 180.0, 400); 
        registrarCamioneta("C301ABC", "Ford", "Ranger", 200.0, 1.5);
        registrarCamioneta("C302ABC", "Isuzu", "NPR", 350.0, 3.0);
    }

    public String getIngresosFormateados() {
        return String.format("Q%.2f", ingresosAcumulados);
    }

        private Vehiculo obtenerExistente(String placa) {
            Vehiculo v = buscarVehiculo(placa);
            if (v == null) {
                String mostrada = Vehiculo.ajustarPlaca(placa);
                throw new IllegalArgumentException(mostrada.isEmpty()
                        ? "La placa no puede estar vacía."
                        : "No existe ningún vehículo con la placa " + mostrada + ".");
            }
            return v;
        }
    }
