package mesaconsola.controller;

import jakarta.persistence.PersistenceException;
import mesaconsola.DAO.MesaDAO;
import mesaconsola.DAO.MesaDAOImpl;
import mesaconsola.model.Mesa;
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

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {
    private final MesaDAO mesaDAO = new MesaDAOImpl();

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Mesa mesa) {
        try {
            mesa.setId(null);
            Mesa creada = mesaDAO.crear(mesa);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo crear la mesa");
        }
    }

    @GetMapping
    public ResponseEntity<List<Mesa>> listar() {
        return ResponseEntity.ok(mesaDAO.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> buscarPorId(@PathVariable Long id) {
        Mesa mesa = mesaDAO.buscarPorId(id);
        return mesa == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(mesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Mesa datos) {
        Mesa existente = mesaDAO.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNumero(datos.getNumero());
        existente.setEstado(datos.getEstado());
        existente.setPedidoId(datos.getPedidoId());
        try {
            return ResponseEntity.ok(mesaDAO.actualizar(existente));
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo actualizar la mesa");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return mesaDAO.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}