package mesaconsola.DAO;

import mesaconsola.model.Mesa;

import java.util.List;

public interface MesaDAO {
    Mesa crear(Mesa mesa);
    List<Mesa> listar();
    Mesa buscarPorId(Long id);
    Mesa actualizar(Mesa mesa);
    boolean eliminar(Long id);
}