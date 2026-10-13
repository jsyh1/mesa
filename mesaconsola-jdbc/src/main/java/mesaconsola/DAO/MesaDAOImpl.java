package mesaconsola.DAO;

import mesaconsola.model.Mesa;
import mesaconsola.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa el CRUD de Mesa usando SQL y JDBC, sin JPA/Hibernate.
 *
 * @author Jsyh
 * @version 1.0
 */
public class MesaDAOImpl implements MesaDAO {

    private static final String SQL_INSERT =
        "INSERT INTO mesa (numero, estado, pedido_id) VALUES (?, ?, ?)";
    private static final String SQL_LISTAR =
        "SELECT id, numero, estado, pedido_id FROM mesa ORDER BY id";
    private static final String SQL_BUSCAR =
        "SELECT id, numero, estado, pedido_id FROM mesa WHERE id = ?";
    private static final String SQL_ACTUALIZAR =
        "UPDATE mesa SET numero = ?, estado = ?, pedido_id = ? WHERE id = ?";
    private static final String SQL_ELIMINAR =
        "DELETE FROM mesa WHERE id = ?";

    @Override
    public Mesa crear(Mesa mesa) {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(
                 SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setInt(1, mesa.getNumero());
            sentencia.setString(2, mesa.getEstado());
            establecerPedidoId(sentencia, 3, mesa.getPedidoId());

            int filas = sentencia.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se insertó la mesa.");
            }

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    mesa.setId(claves.getLong(1));
                }
            }
            return mesa;
        } catch (SQLException e) {
            throw new IllegalStateException("Error al crear la mesa: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Mesa> listar() {
        List<Mesa> mesas = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR);
             ResultSet resultados = sentencia.executeQuery()) {

            while (resultados.next()) {
                mesas.add(mapearMesa(resultados));
            }
            return mesas;
        } catch (SQLException e) {
            throw new IllegalStateException("Error al listar mesas: " + e.getMessage(), e);
        }
    }

    @Override
    public Mesa buscarPorId(Long id) {
        if (id == null) {
            return null;
        }

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR)) {

            sentencia.setLong(1, id);
            try (ResultSet resultados = sentencia.executeQuery()) {
                return resultados.next() ? mapearMesa(resultados) : null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al buscar la mesa: " + e.getMessage(), e);
        }
    }

    @Override
    public Mesa actualizar(Mesa mesa) {
        if (mesa.getId() == null) {
            throw new IllegalArgumentException("La mesa debe tener un ID para actualizarse.");
        }

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            sentencia.setInt(1, mesa.getNumero());
            sentencia.setString(2, mesa.getEstado());
            establecerPedidoId(sentencia, 3, mesa.getPedidoId());
            sentencia.setLong(4, mesa.getId());

            if (sentencia.executeUpdate() == 0) {
                return null;
            }
            return mesa;
        } catch (SQLException e) {
            throw new IllegalStateException("Error al actualizar la mesa: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {

            sentencia.setLong(1, id);
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Error al eliminar la mesa: " + e.getMessage(), e);
        }
    }

    private Mesa mapearMesa(ResultSet resultado) throws SQLException {
        long pedido = resultado.getLong("pedido_id");
        Long pedidoId = resultado.wasNull() ? null : pedido;

        return new Mesa(
            resultado.getLong("id"),
            resultado.getInt("numero"),
            resultado.getString("estado"),
            pedidoId
        );
    }

    private void establecerPedidoId(PreparedStatement sentencia, int indice, Long pedidoId)
            throws SQLException {
        if (pedidoId == null) {
            sentencia.setNull(indice, java.sql.Types.BIGINT);
        } else {
            sentencia.setLong(indice, pedidoId);
        }
    }
}
