package tech.ada.testesfinal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.ada.testesfinal.model.entity.LivroEntity;

import java.util.List;

@Repository
public interface LivroRepository extends CrudRepository<LivroEntity, Long> {

    List<LivroEntity> findAll();
}
