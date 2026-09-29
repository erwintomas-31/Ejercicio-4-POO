public class CamionetaCarga extends Vehiculo {
    public static final double RecargoPorTonelada=100.00;
    private final double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo, double tarifaDiaria, double capacidadToneladas) {
        super(placa, marca, modelo, tarifaDiaria);
        this.capacidadToneladas = exigirNumeroPositivo(capacidadToneladas, "Capacidad en toneladas");
    }

    @Override 
    public double calcularCostoAlquiler(int numeroDias){
        validarDias(numeroDias);
        double recargo=capacidadToneladas*RecargoPorTonelada;
        return (tarifaDiaria+recargo)*numeroDias;
    }

    @Override 
    public String getCategoria(){
        return "Camioneta de carga";
    }
    
    @Override
    public String toString(){
        return super.toString() + ", capacidad en toneladas= " + capacidadToneladas;
    }
}
