package mesaconsola.DAO;

import java.util.List;
import mesaconsola.model.Mesa;

/**
 * Define las operaciones disponibles para administrar mesas.
 *
 * @author Jsyh
 * @version 1.0
 */
public interface MesaDAO {
    Mesa crear(Mesa mesa);
    List<Mesa> listar();
    Mesa buscarPorId(Long id);
    Mesa actualizar(Mesa mesa);
    boolean eliminar(Long id);
}
