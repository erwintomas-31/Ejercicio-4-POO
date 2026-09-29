public class Motocicleta extends Vehiculo {
    private static final int CilindrajeLimite=250;
    private static final double CargoAltoCilindraje=75.00;
    private final int cilindraje;

    public Motocicleta(String placa, String marca, String modelo, double tarifaDiaria, int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria);
        this.cilindraje = exigirNumeroPositivo(cilindraje, "Cilindraje");
    }

    @Override 
    public double calcularCostoAlquiler(int numeroDias){
        validarDias(numeroDias);
        double cargo=cilindraje>CilindrajeLimite ? CargoAltoCilindraje:0.00;
        return (tarifaDiaria*numeroDias)+cargo;
    }

    @Override 
    public String getCategoria(){
        return "Motocicleta";
    }

    @Override 
    public String toString(){
        return super.toString()+ ", cilindraje= " +cilindraje;
    }
}
