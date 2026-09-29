public abstract class Vehiculo {
   private final String placa;
   protected String marca;
   protected String modelo;
   protected double tarifaDiaria;
   protected boolean disponible;
   protected int diasAlquiler;
   
   public Vehiculo(String placa, String marca, String modelo, double tarifaDiaria){
        this.placa=ajustarPlaca(placa);
        if(this.placa.isEmpty()){
          throw new IllegalArgumentException("La placa no puede estar vaciía");
        }
        this.marca=exigirTexto(marca, "Marca");;
        this.modelo=exigirTexto(modelo, "Modelo");;
        this.tarifaDiaria=exigirNumeroPositivo(tarifaDiaria, "Tarifa diaria");
        this.disponible=true;
   }

   public String getPlaca(){
        return placa;
   }

   public void setDiasAlquiler(int diasAlquiler){
        this.diasAlquiler=diasAlquiler;
   }

   public boolean estaDisponible(){
        return disponible;
   }

   public void setDisponibilidad(boolean estado) {
        this.disponible = estado;
    }

    public static String ajustarPlaca(String placa){
          return placa == null ? "" : placa.trim().toUpperCase();
    }

    public static String exigirTexto(String valor, String campo){
          if(valor==null || valor.trim().isEmpty()){
               throw new IllegalArgumentException(campo+ " no puede estar vacío");
          }
          return valor.trim();
    }

    public static double exigirNumeroPositivo(double valor, String campo){
          if(valor<0){
               throw new IllegalArgumentException(campo+ " debe ser un número mayor a cero");
          }
          return valor;
    }

    public static int exigirNumeroPositivo(int valor, String campo){
          if(valor<=0){
               throw new IllegalArgumentException(campo+ " debe ser un número mayor a cero");
          }
          return valor;
    }

    public static void validarDias(int diasAlquiler){
     if(diasAlquiler<=0){
          throw new IllegalArgumentException("Los días de alquiler deben ser mayores a cero");
     }
    }

   @Override 
   public String toString(){
    return "Vehículo: " + "placa= " + placa + ", marca= " + marca + ", modelo= " + modelo + ", disponible= " + estaDisponible();
   }

   public abstract double calcularCostoAlquiler(int dias);
 
   public abstract String getCategoria();
}
