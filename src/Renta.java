public class Renta {
    private final GestorAlquileres gestor =new GestorAlquileres();
    
    public void cargarDatosIniciales() {
        gestor.cargarDatosIniciales();
    }
 
    public boolean registrarAutomovil(String placa, String marca, String modelo, double tarifa,
                                      int pasajeros, boolean esAutomatico) {
        return gestor.registrarAutomovil(placa, marca, modelo, tarifa, pasajeros, esAutomatico);
    }
 
    public boolean registrarMotocicleta(String placa, String marca, String modelo, double tarifa,
                                        int cilindraje) {
        return gestor.registrarMotocicleta(placa, marca, modelo, tarifa, cilindraje);
    }
 
    public boolean registrarCamioneta(String placa, String marca, String modelo, double tarifa,
                                      double capacidadToneladas) {
        return gestor.registrarCamioneta(placa, marca, modelo, tarifa, capacidadToneladas);
    }
 
    public String listarFlota() {
        return gestor.listarVehiculos();
    }
 
    public String cotizar(String placa, int dias) {
        return gestor.cotizarAlquiler(placa, dias);
    }
 
    public void verificarAlquiler(String placa, int dias) {
        gestor.verificarAlquiler(placa, dias);
    }
 
    public boolean confirmarAlquiler(String placa, int dias) {
        return gestor.confirmarAlquiler(placa, dias);
    }
 
    public void registrarDevolucion(String placa) {
        gestor.registrarDevolucion(placa);
    }
 
    public String generarReporte() {
        return gestor.generarReporte();
    }
 
    public String ingresosFormateados() {
        return gestor.getIngresosFormateados();
    }
}
