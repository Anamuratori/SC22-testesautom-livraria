package tech.ada.testesfinal.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "livro")
@AllArgsConstructor
@NoArgsConstructor
public class LivroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String resumo;
    private String sumario;
    @Column(nullable = false)
    private Double preco;
    @Column(nullable = false)
    private Integer paginas;
    @Column(nullable = false)
    private String isbn;
    private LocalDate dataPublicacao;
}