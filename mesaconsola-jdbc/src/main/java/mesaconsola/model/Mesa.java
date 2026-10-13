package mesaconsola.model;

/**
 * Representa una mesa del restaurante.
 *
 * @author Jsyh
 * @version 1.0
 */
public class Mesa {

    private Long id;
    private int numero;
    private String estado;
    private Long pedidoId;

    public Mesa() {
    }

    public Mesa(int numero, String estado) {
        this.numero = numero;
        this.estado = estado;
    }

    public Mesa(int numero, String estado, Long pedidoId) {
        this.numero = numero;
        this.estado = estado;
        this.pedidoId = pedidoId;
    }

    public Mesa(Long id, int numero, String estado, Long pedidoId) {
        this.id = id;
        this.numero = numero;
        this.estado = estado;
        this.pedidoId = pedidoId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public void ocupar() {
        estado = "OCUPADA";
    }

    public void liberar() {
        estado = "LIBRE";
        pedidoId = null;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %s | Mesa: %d | Estado: %s | Pedido ID: %s",
            id, numero, estado == null ? "Sin estado" : estado,
            pedidoId == null ? "Sin pedido" : pedidoId
        );
    }
}
