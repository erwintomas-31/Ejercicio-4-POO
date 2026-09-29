public class Automovil extends Vehiculo {
    public static final double RecargoAutomatico= 50.00;
    private final int numPasajeros;
    private final boolean esAutomatico;

    public Automovil(String placa, String marca, String modelo, double tarifaDiaria, int numPasajeros, boolean esAtomatico) {
        super(placa, marca, modelo, tarifaDiaria);
        this.numPasajeros = exigirNumeroPositivo(numPasajeros, "Número de pasajeros");
        this.esAutomatico = esAtomatico;
    }

    @Override 
    public double calcularCostoAlquiler(int numeroDias){
        validarDias(numeroDias);
        double tarifa=tarifaDiaria+(esAutomatico ? RecargoAutomatico:0.00);
        return tarifa*numeroDias;
    }

    @Override 
    public String getCategoria(){
       return "Automóvil"; 
    }
    
    @Override
    public String toString() {
        return super.toString() + ", número de pasajeros = " + numPasajeros + ", es automático = " + esAutomatico;
    }
}
