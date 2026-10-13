package productoconsola.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.PersistenceException;
import productoconsola.DAO.ProductoDAO;
import productoconsola.DAO.ProductoDAOImpl;
import productoconsola.model.Producto;


@RestController 
@RequestMapping ("/productos")
public class ProductoController {
    private final ProductoDAO productoDAO = new ProductoDAOImpl();
    
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Producto producto) {
        try {
            producto.setId(null);
            Producto creado = productoDAO.crear(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("No se pudo crear el producto");
        }
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoDAO.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id){
        Producto producto = productoDAO.buscarPorId(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(producto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Producto datos){
        Producto existente = productoDAO.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());
        existente.setDisponibilidad(datos.isDisponibilidad());
        try { 
            return ResponseEntity.ok(productoDAO.actualizar(existente));
        } catch (PersistenceException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("No se pudo actualizar el producto");
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (!productoDAO.eliminar(id)){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    //http://localhost:8080/productos

}
