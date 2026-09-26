import java.util.ArrayList;
import java.util.List;

public class SistemaRentaMovil {
    private List<Vehiculo> flota;
    private double ingresosAcumulados;

    public SistemaRentaMovil() {
        this.flota = new ArrayList<>();
        this.ingresosAcumulados = 0.0;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        flota.add(new Auto("P123ABC", "Toyota", "Corolla", 200.0, 5, "Automatica"));
        flota.add(new Auto("P456DEF", "Honda", "Civic", 180.0, 5, "Manual"));

        flota.add(new Moto("M100AAA", "Yamaha", "MT-03", 100.0, 321));
        flota.add(new Moto("M200BBB", "Suzuki", "GN125", 80.0, 125));

        flota.add(new Camioneta("C789GHI", "Isuzu", "NPR", 250.0, 3.5));
        flota.add(new Camioneta("C321JKL", "Hino", "300", 200.0, 1.5));
    }

    public Vehiculo buscarPorPlaca(String placa) {
        for (Vehiculo v : flota) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    public boolean agregarVehiculo(Vehiculo v) {
        if (buscarPorPlaca(v.getPlaca()) != null) {
            return false;
        }
        flota.add(v);
        return true;
    }

    public void registrarIngresoAlquiler(double monto) {
        this.ingresosAcumulados += monto;
    }

    public double getIngresosAcumulados() {
        return ingresosAcumulados;
    }

    public List<Vehiculo> getFlota() {
        return flota;
    }

    public void mostrarReporteGeneral() {
        int totalAutos = 0, autosDisp = 0, autosAlq = 0;
        int totalMotos = 0, motosDisp = 0, motosAlq = 0;
        int totalCamionetas = 0, camDisp = 0, camAlq = 0;

        for (Vehiculo v : flota) {
            if (v instanceof Auto) {
                totalAutos++;
                if (v.isDisponible()) autosDisp++; else autosAlq++;
            } else if (v instanceof Moto) {
                totalMotos++;
                if (v.isDisponible()) motosDisp++; else motosAlq++;
            } else if (v instanceof Camioneta) {
                totalCamionetas++;
                if (v.isDisponible()) camDisp++; else camAlq++;
            }
        }

        System.out.println("\n===== REPORTE GENERAL DE LA FLOTA =====");
        System.out.println("Total de vehículos registrados: " + flota.size());
        System.out.printf("Automóviles  -> Total: %d | Disponibles: %d | Alquilados: %d\n", totalAutos, autosDisp, autosAlq);
        System.out.printf("Motocicletas -> Total: %d | Disponibles: %d | Alquilados: %d\n", totalMotos, motosDisp, motosAlq);
        System.out.printf("Camionetas   -> Total: %d | Disponibles: %d | Alquilados: %d\n", totalCamionetas, camDisp, camAlq);
        System.out.println("---------------------------------------");
        System.out.printf("Dinero Acumulado por Alquileres: Q%.2f\n", ingresosAcumulados);
        System.out.println("=======================================\n");
    }
}