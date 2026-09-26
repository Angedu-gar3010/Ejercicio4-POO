public class Moto extends Vehiculo {

    private int cilindraje;

    public Moto(String placa, String marca, String modelo, double tarifaDiaria, int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria);
        this.cilindraje = cilindraje;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(int cilindraje) {
        this.cilindraje = cilindraje;
    }

    @Override
    public double calcularCostoTotal(int duracion) {
        double costoTotal = getTarifaDiaria() * duracion;
        if (cilindraje > 250) {
            costoTotal += 75.0;
        }
        return costoTotal;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Cilindraje: %d cc", cilindraje);
    }
}