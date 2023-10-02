package Java.Database;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2 {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:file:./BolsaEmpleo";
        // Conectarnos con la base de datos
        ConnectionSource con = new JdbcConnectionSource(url);
        // Configurar la tabla a traves de un DAO (Data Access Object)
        Dao<Aspirantes, Integer> aspirantesDao =
                DaoManager.createDao(con, Aspirantes.class);
        // Creo el archivo donde se guardaran las cuentas
        TableUtils.createTable(aspirantesDao);
        System.out.println("Table creada exitosamente!");
        // Cerrar la conexion
        con.close();
    }

    private static Connection connection; // Agregamos una variable para la conexión



    public static void cerrarConexion() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

