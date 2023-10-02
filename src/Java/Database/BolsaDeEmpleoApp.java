package Java.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BolsaDeEmpleoApp {

    static Dao<Aspirantes, Integer> tablaAspirantes;

    public static void main(String[] args) throws SQLException {
        LoggerFactory.setLogBackendFactory(LogBackendType.NULL);
        String url = "jdbc:h2:file:./BolsaEmpleo";
        // Conectarnos con la base de datos
        ConnectionSource con = new JdbcConnectionSource(url);
        // Configurar la tabla a traves de un DAO (Data Access Object)
        tablaAspirantes = DaoManager.createDao(con, Aspirantes.class);

        BolsadeEmpleo bolsaDeEmpleo = new BolsadeEmpleo();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menú Principal:");
            System.out.println("1. Agregar nuevo aspirante");
            System.out.println("2. Mostrar cédulas de aspirantes");
            System.out.println("3. Mostrar información detallada de aspirante");
            System.out.println("4. Buscar aspirantes por nombre");
            System.out.println("5. Ordenar aspirantes");
            System.out.println("6. Consultar aspirante con mayor experiencia");
            System.out.println("7. Consultar aspirante más joven");
            System.out.println("8. Contratar aspirante");
            System.out.println("9. Eliminar aspirantes por experiencia");
            System.out.println("10. Calcular promedio de edad de aspirantes");
            System.out.println("11. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    // Agregar nuevo aspirante
                    Aspirantes nuevoAspirante = agregarAspiranteDesdeConsola(scanner);
                    bolsaDeEmpleo.agregarAspirante(nuevoAspirante);
                    break;
                case 2:
                    // Mostrar cédulas de aspirantes
                    List<String> cedulas = bolsaDeEmpleo.mostrarCedulasAspirantes();
                    System.out.println("Cédulas de aspirantes:");
                    for (String cedula : cedulas) {
                        System.out.println(cedula);
                    }
                    break;
                case 3:
                    // Mostrar información detallada de aspirante
                    System.out.print("Ingrese la cédula del aspirante: ");
                    String cedulaBuscada = scanner.nextLine();
                    Aspirantes aspiranteEncontrado = bolsaDeEmpleo.obtenerInformacionAspirante(cedulaBuscada);
                    if (aspiranteEncontrado != null) {
                        mostrarInformacionAspirante(aspiranteEncontrado);
                    } else {
                        System.out.println("No se encontró al aspirante con esa cédula.");
                    }
                    break;
                case 4:
                    // Buscar aspirantes por nombre
                    System.out.print("Ingrese el nombre o parte del nombre: ");
                    String nombreBuscado = scanner.nextLine();
                    List<Aspirantes> aspirantesEncontrados = bolsaDeEmpleo.buscarPorNombre(nombreBuscado);
                    if (!aspirantesEncontrados.isEmpty()) {
                        mostrarListaAspirantes(aspirantesEncontrados);
                    } else {
                        System.out.println("No se encontraron aspirantes con ese nombre.");
                    }
                    break;
                case 5:
                    // Ordenar aspirantes
                    ordenarAspirantes(bolsaDeEmpleo, scanner);
                    break;
                case 6:
                    // Consultar aspirante con mayor experiencia
                    Aspirantes mayorExperiencia = bolsaDeEmpleo.consultarAspiranteConMayorExperiencia();
                    if (mayorExperiencia != null) {
                        mostrarInformacionAspirante(mayorExperiencia);
                    } else {
                        System.out.println("No hay aspirantes registrados.");
                    }
                    break;
                case 7:
                    // Consultar aspirante más joven
                    Aspirantes masJoven = bolsaDeEmpleo.consultarAspiranteMasJoven();
                    if (masJoven != null) {
                        mostrarInformacionAspirante(masJoven);
                    } else {
                        System.out.println("No hay aspirantes registrados.");
                    }
                    break;
                case 8:
                    // Contratar aspirante
                    System.out.print("Ingrese la cédula del aspirante a contratar: ");
                    String cedulaContrato = scanner.nextLine();
                    Aspirantes aspiranteAContratar = bolsaDeEmpleo.obtenerInformacionAspirante(cedulaContrato);
                    if (aspiranteAContratar != null) {
                        bolsaDeEmpleo.contratarAspirante(cedulaContrato);
                        System.out.println("El aspirante ha sido contratado y eliminado de la lista.");
                    } else {
                        System.out.println("No se encontró al aspirante con esa cédula.");
                    }
                    break;
                case 9:
                    // Eliminar aspirantes por experiencia
                    System.out.print("Ingrese la experiencia mínima requerida: ");
                    int experienciaMinima = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea
                    bolsaDeEmpleo.eliminarPorExperiencia(experienciaMinima);
                    System.out.println("Los aspirantes con experiencia menor a " + experienciaMinima + " años han sido eliminados.");
                    break;
                case 10:
                    // Calcular promedio de edad de aspirantes
                    double promedioEdad = bolsaDeEmpleo.calcularPromedioEdad();
                    System.out.println("El promedio de edad de los aspirantes es: " + promedioEdad);
                    break;
                case 11:
                    System.out.println("Saliendo del programa.");
                    H2.cerrarConexion();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static Aspirantes agregarAspiranteDesdeConsola(Scanner scanner) {
        System.out.print("Ingrese la cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Ingrese el nombre completo: ");
        String nombreCompleto = scanner.nextLine();
        System.out.print("Ingrese la edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        System.out.print("Ingrese la experiencia en años: ");
        int experienciaAnios = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        System.out.print("Ingrese la profesión: ");
        String profesion = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();

        return new Aspirantes(cedula, nombreCompleto, edad, experienciaAnios, profesion, telefono);
    }

    private static void mostrarInformacionAspirante(Aspirantes aspirante) {
        System.out.println("Información del aspirante:");
        System.out.println("Cédula: " + aspirante.getCedula());
        System.out.println("Nombre Completo: " + aspirante.getNombreCompleto());
        System.out.println("Edad: " + aspirante.getEdad());
        System.out.println("Experiencia en Años: " + aspirante.getExperienciaAnios());
        System.out.println("Profesión: " + aspirante.getProfesion());
        System.out.println("Teléfono: " + aspirante.getTelefono());
    }

    private static void mostrarListaAspirantes(List<Aspirantes> aspirantes) {
        System.out.println("Aspirantes encontrados:");
        for (Aspirantes aspirante : aspirantes) {
            System.out.println("Cédula: " + aspirante.getCedula() + ", Nombre: " + aspirante.getNombreCompleto());
        }
    }

    private static void ordenarAspirantes(BolsadeEmpleo bolsaDeEmpleo, Scanner scanner) throws SQLException {
        System.out.println("Seleccione el criterio de ordenamiento:");
        System.out.println("1. Por años de experiencia");
        System.out.println("2. Por edad");
        System.out.println("3. Por profesión");
        int opcionOrdenamiento = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        switch (opcionOrdenamiento) {
            case 1:
                bolsaDeEmpleo.ordenarAspirantesPorExperiencia();
                break;
            case 2:
                bolsaDeEmpleo.ordenarAspirantesPorEdad();
                break;
            case 3:
                bolsaDeEmpleo.ordenarAspirantesPorProfesion();
                break;
            default:
                System.out.println("Opción de ordenamiento no válida.");
        }

        System.out.println("La lista de aspirantes ha sido ordenada.");
    }
}