public class Auto extends Vehiculo {

    private int capacidad;
    private String transmision; 

    public Auto(String placa, String marca, String modelo, double tarifaDiaria, int capacidad, String transmision) {
        super(placa, marca, modelo, tarifaDiaria);
        this.capacidad = capacidad;
        this.transmision = transmision;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    @Override
    public double calcularCostoTotal(int duracion) {
        double recargoPorDia = 0;
        if (transmision != null && transmision.equalsIgnoreCase("Automatica")) {
            recargoPorDia = 50.0;
        }
        return (getTarifaDiaria() + recargoPorDia) * duracion;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Pasajeros: %d | Transmisión: %s", capacidad, transmision);
    }
}