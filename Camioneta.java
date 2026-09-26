public class Camioneta extends Vehiculo {

    private double capacidadToneladas;

    public Camioneta(String placa, String marca, String modelo, double tarifaDiaria, double capacidadToneladas) {
        super(placa, marca, modelo, tarifaDiaria);
        this.capacidadToneladas = capacidadToneladas;
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }

    public void setCapacidadToneladas(double capacidadToneladas) {
        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public double calcularCostoTotal(int duracion) {
        return (getTarifaDiaria() + (capacidadToneladas * 100.0)) * duracion;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Capacidad: %.2f Ton", capacidadToneladas);
    }
}