package mesaconsola.model;

import mesaconsola.DAO.MesaDAO;
import mesaconsola.DAO.MesaDAOImpl;

import java.util.List;
import java.util.Scanner;

/**
 * Punto de entrada y menú de consola del CRUD de mesas.
 *
 * @author Jsyh
 * @version 1.0
 */
public class Main {

    private static final MesaDAO mesaDAO = new MesaDAOImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");

            try {
                switch (opcion) {
                    case 1 -> crearMesa();
                    case 2 -> listarMesas();
                    case 3 -> buscarMesa();
                    case 4 -> actualizarMesa();
                    case 5 -> eliminarMesa();
                    case 6 -> ocuparMesa();
                    case 7 -> liberarMesa();
                    case 0 -> System.out.println("Programa terminado.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (RuntimeException e) {
                System.out.println("No se pudo completar la operación: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- CRUD Mesa (JDBC + SQL) ---");
        System.out.println("1. Crear mesa");
        System.out.println("2. Listar mesas");
        System.out.println("3. Buscar mesa por ID");
        System.out.println("4. Actualizar mesa");
        System.out.println("5. Eliminar mesa");
        System.out.println("6. Ocupar mesa");
        System.out.println("7. Liberar mesa");
        System.out.println("0. Salir");
    }

    private static void crearMesa() {
        int numero = leerEntero("Número de mesa: ");
        System.out.print("Estado (LIBRE/OCUPADA): ");
        String estado = scanner.nextLine().trim().toUpperCase();

        Mesa mesa = new Mesa(numero, estado.isBlank() ? "LIBRE" : estado);
        mesaDAO.crear(mesa);
        System.out.println("Mesa creada con ID: " + mesa.getId());
    }

    private static void listarMesas() {
        List<Mesa> mesas = mesaDAO.listar();
        if (mesas.isEmpty()) {
            System.out.println("No hay mesas registradas.");
            return;
        }
        mesas.forEach(System.out::println);
    }

    private static void buscarMesa() {
        Long id = leerLong("ID de la mesa: ");
        Mesa mesa = mesaDAO.buscarPorId(id);
        System.out.println(mesa == null ? "No existe una mesa con ese ID." : mesa);
    }

    private static void actualizarMesa() {
        Long id = leerLong("ID de la mesa a actualizar: ");
        Mesa mesa = mesaDAO.buscarPorId(id);
        if (mesa == null) {
            System.out.println("No existe una mesa con ese ID.");
            return;
        }

        mesa.setNumero(leerEntero("Nuevo número (" + mesa.getNumero() + "): "));
        System.out.print("Nuevo estado (" + mesa.getEstado() + "): ");
        String estado = scanner.nextLine().trim();
        if (!estado.isBlank()) {
            mesa.setEstado(estado.toUpperCase());
        }

        System.out.print("Nuevo ID de pedido (Enter para dejarlo sin pedido): ");
        String pedido = scanner.nextLine().trim();
        if (pedido.isBlank()) {
            mesa.setPedidoId(null);
        } else {
            mesa.setPedidoId(Long.parseLong(pedido));
        }

        Mesa actualizada = mesaDAO.actualizar(mesa);
        System.out.println(actualizada == null ? "No se encontró la mesa." : "Mesa actualizada.");
    }

    private static void eliminarMesa() {
        Long id = leerLong("ID de la mesa a eliminar: ");
        System.out.println(mesaDAO.eliminar(id) ? "Mesa eliminada." : "No existe una mesa con ese ID.");
    }

    private static void ocuparMesa() {
        cambiarEstadoMesa("OCUPADA");
    }

    private static void liberarMesa() {
        Long id = leerLong("ID de la mesa: ");
        Mesa mesa = mesaDAO.buscarPorId(id);
        if (mesa == null) {
            System.out.println("No existe una mesa con ese ID.");
            return;
        }
        mesa.liberar();
        Mesa actualizada = mesaDAO.actualizar(mesa);
        System.out.println(actualizada == null ? "No se encontró la mesa." : "Mesa liberada.");
    }

    private static void cambiarEstadoMesa(String estado) {
        Long id = leerLong("ID de la mesa: ");
        Mesa mesa = mesaDAO.buscarPorId(id);
        if (mesa == null) {
            System.out.println("No existe una mesa con ese ID.");
            return;
        }
        mesa.setEstado(estado);
        Mesa actualizada = mesaDAO.actualizar(mesa);
        System.out.println(actualizada == null ? "No se encontró la mesa." : "Mesa ocupada.");
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número entero válido.");
            }
        }
    }

    private static Long leerLong(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un ID numérico válido.");
            }
        }
    }
}
