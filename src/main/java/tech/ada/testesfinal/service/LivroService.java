package tech.ada.testesfinal.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.testesfinal.exception.BadRequestException;
import tech.ada.testesfinal.model.mapper.LivroMapper;
import tech.ada.testesfinal.model.dto.LivroDTO;
import tech.ada.testesfinal.model.entity.LivroEntity;
import tech.ada.testesfinal.repository.LivroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    LivroRepository repository;
    @Autowired
    LivroMapper mapper;

    public LivroDTO criar (LivroDTO livroDTO) {
        LivroEntity livro = mapper.update(livroDTO);
        livro = repository.save(livro);
        return mapper.update(livro);
    }

    public List<LivroDTO> listar () {

        List<LivroEntity> livros = repository.findAll();
        return mapper.updateListDTO(livros);
    }
    public LivroDTO buscarPorId (Long id) {
        Optional<LivroEntity> livroEntityOp = repository.findById(id);
        if (livroEntityOp.isPresent()) {
            LivroEntity livroEntity = livroEntityOp.get();
            return mapper.update(livroEntity);
        }
        throw new BadRequestException("Livro nao encontrado.");
    }
}
