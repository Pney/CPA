/* 
package com.biopark.cpa.repository.grupo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.biopark.cpa.entities.grupos.Desafio;

@Repository
public interface EdicaoDesafioRepository extends JpaRepository<Desafio, Long> {
}

@Service
public class DesafioService {

    @Autowired
    private EdicaoDesafioRepository edicaoDesafioRepository;

    public Desafio buscarPorId(Long id) {
        Optional<Desafio> optionalDesafio = edicaoDesafioRepository.findById(id);

        
        if (optionalDesafio.isPresent()) {
            ;
            return optionalDesafio.get();
        } else {
            throw new RuntimeException("Desafio não encontrado");
        }
    }

}

Desafio desafio = desafioService.buscarPorId(1L);desafio.setNome("Novo nome");desafio.setEmail("novo@email");

//salvar
desafioRepository.save(desafio);

*/
