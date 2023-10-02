package Java.Database;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BolsadeEmpleo {
    private Dao<Aspirantes, String> aspirantesDao;

    public BolsadeEmpleo() throws SQLException {
        String databaseUrl = "jdbc:h2:file:./BolsaEmpleo";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        aspirantesDao = DaoManager.createDao(connectionSource, Aspirantes.class);

        // Crear la tabla si no existe
        TableUtils.createTableIfNotExists(connectionSource, Aspirantes.class);
    }

    public void agregarAspirante(Aspirantes aspirante) throws SQLException {
        aspirantesDao.create(aspirante);
    }

    public List<String> mostrarCedulasAspirantes() throws SQLException {
        List<Aspirantes> aspirantes = aspirantesDao.queryForAll();
        return aspirantes.stream()
                .map(Aspirantes::getCedula)
                .collect(Collectors.toList());
    }

    public Aspirantes obtenerInformacionAspirante(String cedula) throws SQLException {
        return aspirantesDao.queryForId(cedula);
    }

    public List<Aspirantes> buscarPorNombre(String nombre) throws SQLException {
        return aspirantesDao.queryForEq("nombreCompleto", nombre);
    }

    public void ordenarAspirantesPorExperiencia() throws SQLException {
        List<Aspirantes> aspirantesOrdenados = aspirantesDao.queryBuilder()
                .orderBy("experienciaAnios", true)
                .query();
        mostrarListaAspirantes(aspirantesOrdenados);
    }

    public void ordenarAspirantesPorEdad() throws SQLException {
        List<Aspirantes> aspirantesOrdenados = aspirantesDao.queryBuilder()
                .orderBy("edad", true)
                .query();
        mostrarListaAspirantes(aspirantesOrdenados);
    }

    public void ordenarAspirantesPorProfesion() throws SQLException {
        List<Aspirantes> aspirantesOrdenados = aspirantesDao.queryBuilder()
                .orderBy("profesion", true)
                .query();
        mostrarListaAspirantes(aspirantesOrdenados);
    }

    private void mostrarListaAspirantes(List<Aspirantes> lista) {
        if (!lista.isEmpty()) {
            System.out.println("Lista de aspirantes:");
            for (Aspirantes aspirante : lista) {
                mostrarInformacionAspirante(aspirante);
            }
        } else {
            System.out.println("La lista de aspirantes está vacía.");
        }
    }

    public Aspirantes consultarAspiranteConMayorExperiencia() throws SQLException {
        return aspirantesDao.queryBuilder()
                .orderBy("experienciaAnios", false)
                .queryForFirst();
    }

    public Aspirantes consultarAspiranteMasJoven() throws SQLException {
        return aspirantesDao.queryBuilder()
                .orderBy("edad", true)
                .queryForFirst();
    }

    public void contratarAspirante(String cedula) throws SQLException {

            aspirantesDao.deleteById(cedula);
            System.out.println("El aspirante con cédula " + cedula + " ha sido contratado y eliminado de la lista.");


    }



    public void eliminarPorExperiencia(int experienciaMinima) throws SQLException {
        List<Aspirantes> aspirantesAEliminar = aspirantesDao.queryBuilder()
                .where().lt("experienciaAnios", experienciaMinima)
                .query();
        aspirantesDao.delete(aspirantesAEliminar);
        System.out.println("Los aspirantes con experiencia menor a " + experienciaMinima + " años han sido eliminados.");
    }

    public double calcularPromedioEdad() throws SQLException {
        List<Aspirantes> aspirantes = aspirantesDao.queryForAll();
        if (aspirantes.isEmpty()) {
            return 0.0;
        }

        int sumaEdades = aspirantes.stream()
                .mapToInt(Aspirantes::getEdad)
                .sum();
        return (double) sumaEdades / aspirantes.size();
    }

    private void mostrarInformacionAspirante(Aspirantes aspirante) {
        System.out.println("Cédula: " + aspirante.getCedula());
        System.out.println("Nombre Completo: " + aspirante.getNombreCompleto());
        System.out.println("Edad: " + aspirante.getEdad());
        System.out.println("Experiencia en Años: " + aspirante.getExperienciaAnios());
        System.out.println("Profesión: " + aspirante.getProfesion());
        System.out.println("Teléfono: " + aspirante.getTelefono());
        System.out.println();
    }
}
