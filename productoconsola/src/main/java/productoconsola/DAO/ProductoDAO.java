package productoconsola.DAO;

import productoconsola.model.Producto;
import java.util.List;

public interface ProductoDAO {
    Producto crear(Producto producto);
    List<Producto> listar();
    Producto buscarPorId(Long id);
    Producto actualizar(Producto producto);
    boolean eliminar(Long id);
}