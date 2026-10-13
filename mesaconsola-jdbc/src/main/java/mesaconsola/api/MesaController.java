package mesaconsola.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mesaconsola.DAO.MesaDAO;
import mesaconsola.DAO.MesaDAOImpl;
import mesaconsola.model.Mesa;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {
    private final MesaDAO mesaDAO = new MesaDAOImpl();
    private static final Set<String> ESTADOS = Set.of("LIBRE", "OCUPADA");

    @GetMapping
    public List<Mesa> listar() {
        return mesaDAO.listar();
    }

    @GetMapping("/{id}")
    public Mesa buscar(@PathVariable @Min(1) Long id) {
        Mesa mesa = mesaDAO.buscarPorId(id);
        if (mesa == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la mesa " + id);
        return mesa;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mesa crear(@Valid @RequestBody MesaRequest request) {
        validarEstado(request.estado());
        Mesa mesa = new Mesa(request.numero(), normalizarEstado(request.estado()), request.pedidoId());
        return mesaDAO.crear(mesa);
    }

    @PutMapping("/{id}")
    public Mesa actualizar(@PathVariable @Min(1) Long id, @Valid @RequestBody MesaRequest request) {
        validarEstado(request.estado());
        if (mesaDAO.buscarPorId(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la mesa " + id);
        }
        Mesa mesa = new Mesa(id, request.numero(), normalizarEstado(request.estado()), request.pedidoId());
        return mesaDAO.actualizar(mesa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable @Min(1) Long id) {
        if (!mesaDAO.eliminar(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la mesa " + id);
        }
    }

    @PatchMapping("/{id}/ocupar")
    public Mesa ocupar(@PathVariable @Min(1) Long id) {
        Mesa mesa = buscar(id);
        mesa.setEstado("OCUPADA");
        return mesaDAO.actualizar(mesa);
    }

    @PatchMapping("/{id}/liberar")
    public Mesa liberar(@PathVariable @Min(1) Long id) {
        Mesa mesa = buscar(id);
        mesa.liberar();
        return mesaDAO.actualizar(mesa);
    }

    private void validarEstado(String estado) {
        if (estado != null && !ESTADOS.contains(estado.trim().toUpperCase(Locale.ROOT))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "estado debe ser LIBRE u OCUPADA");
        }
    }

    private String normalizarEstado(String estado) {
        return estado == null || estado.isBlank() ? "LIBRE" : estado.trim().toUpperCase(Locale.ROOT);
    }

    public record MesaRequest(
        @NotNull @Min(1) Integer numero,
        String estado,
        Long pedidoId
    ) {}
}
