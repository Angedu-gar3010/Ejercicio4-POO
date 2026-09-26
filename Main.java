import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaRentaMovil sistema = new SistemaRentaMovil();
        boolean continuar = true;

        while (continuar) {
            System.out.println("====== SISTEMA RENTAMOVIL ======");
            System.out.println("1. Registrar nuevo vehículo");
            System.out.println("2. Consultar flota completa");
            System.out.println("3. Cotizar alquiler");
            System.out.println("4. Confirmar / Realizar alquiler");
            System.out.println("5. Registrar devolución");
            System.out.println("6. Obtener reporte general");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = -1;
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido de opción.\n");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarVehiculoUI(sistema, scanner);
                    break;

                case 2:
                    System.out.println("\n--- FLOTA DE VEHÍCULOS ---");
                    if (sistema.getFlota().isEmpty()) {
                        System.out.println("No hay vehículos registrados.");
                    } else {
                        for (Vehiculo v : sistema.getFlota()) {
                            System.out.println(v);
                        }
                    }
                    System.out.println();
                    break;

                case 3:
                    System.out.print("\nIngrese la placa del vehículo a cotizar: ");
                    String placaCotizar = scanner.nextLine().trim();
                    Vehiculo vehCotizar = sistema.buscarPorPlaca(placaCotizar);

                    if (vehCotizar == null) {
                        System.out.println("Error: El vehículo con placa " + placaCotizar + " no existe.\n");
                    } else {
                        System.out.print("Ingrese la cantidad de días solicitados: ");
                        try {
                            int dias = Integer.parseInt(scanner.nextLine().trim());
                            if (dias <= 0) {
                                System.out.println("Error: Los días deben ser mayores a cero.\n");
                            } else {
                                double costo = vehCotizar.calcularCostoTotal(dias);
                                System.out.println("\n--- COTIZACIÓN ---");
                                System.out.println(vehCotizar);
                                System.out.println("Días solicitados: " + dias);
                                System.out.printf("Costo Total Estimado: Q%.2f\n", costo);
                                if (!vehCotizar.isDisponible()) {
                                    System.out.println("NOTA: El vehículo está ocupado actualmente, no se puede alquilar hasta ser devuelto.");
                                }
                                System.out.println();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Debe ingresar un número entero de días.\n");
                        }
                    }
                    break;

                case 4:
                    System.out.print("\nIngrese la placa del vehículo a alquilar: ");
                    String placaAlquilar = scanner.nextLine().trim();
                    Vehiculo vehAlquilar = sistema.buscarPorPlaca(placaAlquilar);

                    if (vehAlquilar == null) {
                        System.out.println("Error: El vehículo especificado no existe.\n");
                    } else if (!vehAlquilar.isDisponible()) {
                        System.out.println("Error: El vehículo se encuentra alquilado actualmente.\n");
                    } else {
                        System.out.print("Ingrese la cantidad de días de alquiler: ");
                        try {
                            int dias = Integer.parseInt(scanner.nextLine().trim());
                            if (dias <= 0) {
                                System.out.println("Error: La cantidad de días debe ser mayor a cero.\n");
                            } else {
                                double total = vehAlquilar.calcularCostoTotal(dias);
                                System.out.println("\n--- RESUMEN DE ALQUILER ---");
                                System.out.println(vehAlquilar);
                                System.out.printf("Monto a cobrar: Q%.2f por %d día(s)\n", total, dias);
                                System.out.print("¿Desea confirmar el alquiler y efectuar el pago? (S/N): ");
                                String confirmacion = scanner.nextLine().trim();

                                if (confirmacion.equalsIgnoreCase("S")) {
                                    vehAlquilar.setDisponible(false);
                                    sistema.registrarIngresoAlquiler(total);
                                    System.out.println("¡Alquiler confirmado con éxito! Vehículo registrado como ocupado.\n");
                                } else {
                                    System.out.println("Alquiler cancelado. No se realizaron cargos.\n");
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Ingrese un valor entero válido para los días.\n");
                        }
                    }
                    break;

                case 5:
                    System.out.print("\nIngrese la placa del vehículo a devolver: ");
                    String placaDevolver = scanner.nextLine().trim();
                    Vehiculo vehDevolver = sistema.buscarPorPlaca(placaDevolver);

                    if (vehDevolver == null) {
                        System.out.println("Error: El vehículo especificado no existe.\n");
                    } else if (vehDevolver.isDisponible()) {
                        System.out.println("Error: El vehículo ya se encuentra disponible en la flota.\n");
                    } else {
                        vehDevolver.setDisponible(true);
                        System.out.println("Devolución registrada correctamente. El vehículo ahora está disponible.\n");
                    }
                    break;

                case 6:
                    sistema.mostrarReporteGeneral();
                    break;

                case 7:
                    continuar = false;
                    System.out.println("Saliendo del sistema RentaMovil... ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, seleccione entre 1 y 7.\n");
                    break;
            }
        }

        scanner.close();
    }

    private static void registrarVehiculoUI(SistemaRentaMovil sistema, Scanner scanner) {
        System.out.println("\n--- REGISTRO DE NUEVO VEHÍCULO ---");
        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine().trim();

        if (placa.isEmpty()) {
            System.out.println("Error: La placa no puede estar vacía.\n");
            return;
        }

        if (sistema.buscarPorPlaca(placa) != null) {
            System.out.println("Error: Ya existe un vehículo registrado con esa placa.\n");
            return;
        }

        System.out.print("Ingrese la marca: ");
        String marca = scanner.nextLine().trim();

        System.out.print("Ingrese el modelo: ");
        String modelo = scanner.nextLine().trim();

        double tarifaDiaria;
        try {
            System.out.print("Ingrese la tarifa diaria (Q): ");
            tarifaDiaria = Double.parseDouble(scanner.nextLine().trim());
            if (tarifaDiaria <= 0) {
                System.out.println("Error: La tarifa diaria debe ser mayor a cero.\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Formato numérico de tarifa inválido.\n");
            return;
        }

        System.out.println("Seleccione tipo de vehículo:");
        System.out.println("1. Automóvil");
        System.out.println("2. Motocicleta");
        System.out.println("3. Camioneta de Carga");
        System.out.print("Opción: ");

        int tipo = -1;
        try {
            tipo = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Selección de tipo no válida.\n");
            return;
        }

        switch (tipo) {
            case 1:
                try {
                    System.out.print("Ingrese la capacidad de pasajeros: ");
                    int pasajeros = Integer.parseInt(scanner.nextLine().trim());
                    if (pasajeros <= 0) {
                        System.out.println("Error: La capacidad debe ser mayor a cero.\n");
                        return;
                    }

                    System.out.print("Ingrese tipo de transmisión (1. Automatica / 2. Manual): ");
                    String transOpt = scanner.nextLine().trim();
                    String transmision = transOpt.equals("1") || transOpt.equalsIgnoreCase("Automatica") ? "Automatica" : "Manual";

                    Auto nuevoAuto = new Auto(placa, marca, modelo, tarifaDiaria, pasajeros, transmision);
                    sistema.agregarVehiculo(nuevoAuto);
                    System.out.println("Automóvil registrado exitosamente.\n");
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un valor numérico para los pasajeros.\n");
                }
                break;

            case 2:
                try {
                    System.out.print("Ingrese el cilindraje (cc): ");
                    int cc = Integer.parseInt(scanner.nextLine().trim());
                    if (cc <= 0) {
                        System.out.println("Error: El cilindraje debe ser mayor a cero.\n");
                        return;
                    }

                    Moto nuevaMoto = new Moto(placa, marca, modelo, tarifaDiaria, cc);
                    sistema.agregarVehiculo(nuevaMoto);
                    System.out.println("Motocicleta registrada exitosamente.\n");
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un cilindraje válido.\n");
                }
                break;

            case 3:
                try {
                    System.out.print("Ingrese la capacidad en toneladas (ej. 1.5): ");
                    double cap = Double.parseDouble(scanner.nextLine().trim());
                    if (cap <= 0) {
                        System.out.println("Error: La capacidad debe ser mayor a cero.\n");
                        return;
                    }

                    Camioneta nuevaCamioneta = new Camioneta(placa, marca, modelo, tarifaDiaria, cap);
                    sistema.agregarVehiculo(nuevaCamioneta);
                    System.out.println("Camioneta registrada exitosamente.\n");
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido para la capacidad.\n");
                }
                break;

            default:
                System.out.println("Error: Tipo de vehículo inválido.\n");
                break;
        }
    }
}