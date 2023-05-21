package tech.ada.testesfinal.model.mapper;

import org.springframework.stereotype.Component;
import tech.ada.testesfinal.model.dto.LivroDTO;
import tech.ada.testesfinal.model.entity.LivroEntity;

import java.util.List;
@Component
public class LivroMapper {
    public LivroDTO update(LivroEntity livro) {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(livro.getId());
        livroDTO.setTitulo(livro.getTitulo());
        livroDTO.setResumo(livro.getResumo());
        livroDTO.setSumario(livro.getSumario());
        livroDTO.setPreco(livro.getPreco());
        livroDTO.setPaginas(livro.getPaginas());
        livroDTO.setIsbn(livro.getIsbn());
        livroDTO.setDataPublicacao(livro.getDataPublicacao());
        return livroDTO;
    }
    public LivroEntity update(LivroDTO livro) {
        LivroEntity livroEntity = new LivroEntity();
        livroEntity.setId(livro.getId());
        livroEntity.setTitulo(livro.getTitulo());
        livroEntity.setResumo(livro.getResumo());
        livroEntity.setSumario(livro.getSumario());
        livroEntity.setPreco(livro.getPreco());
        livroEntity.setPaginas(livro.getPaginas());
        livroEntity.setIsbn(livro.getIsbn());
        livroEntity.setDataPublicacao(livro.getDataPublicacao());
        return livroEntity;
    }

    public List<LivroDTO> updateListDTO (List<LivroEntity> listaEntity) {
        return listaEntity.stream()
                .map(livroEntity -> this.update(livroEntity)).toList();
    }
}
