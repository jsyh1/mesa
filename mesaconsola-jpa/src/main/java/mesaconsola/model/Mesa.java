package mesaconsola.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false, unique = true)
    private int numero;

    @Column(name = "estado", nullable = false)
    private String estado = "LIBRE";

    @Column(name = "pedido_id")
    private Long pedidoId;

    public Mesa() {
    }

    public Mesa(int numero, String estado, Long pedidoId) {
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

    @Override
    public String toString() {
        return String.format("ID: %d | Mesa: %d | Estado: %s | Pedido ID: %s",
                id, numero, estado, pedidoId == null ? "Sin pedido" : pedidoId);
    }
}