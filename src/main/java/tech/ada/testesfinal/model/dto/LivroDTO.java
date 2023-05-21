package tech.ada.testesfinal.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LivroDTO {
    private Long id;
    @NotBlank(message = "Campo 'titulo' não pode estar vazio.")
    private String titulo;
    @NotBlank(message = "Campo 'resumo' não pode estar vazio.")
    @Size(max = 500, message = "Tamanho acima do permitido. Maximo 500 caracteres.")
    private String resumo;
    private String sumario;
    @NotNull(message = "Campo 'preço' não pode estar vazio.")
    @Min(value = 20)
    private Double preco;
    @NotNull(message = "Campo 'paginas' não pode estar vazio.")
    @Min(value = 100)
    private Integer paginas;
    @NotBlank(message = "Campo 'ISBN' não pode estar vazio.")
    private String isbn;
    //Data precisa ser no futuro
    @Future(message = "A data precisa ser futura em relação ao dia atual.")
    private LocalDate dataPublicacao;

}