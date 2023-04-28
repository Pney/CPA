/* package com.biopark.cpa.services.grupos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biopark.cpa.entities.grupos.Desafio;
import com.biopark.cpa.repository.grupo.DesafioRepository;

@Service
public class DesafioService2 {

    @Autowired
    private DesafioRepository desafioRepository;

    public Desafio buscarPorId(Long id) {
        Optional<Desafio> optionalDesafio = desafioRepository.findById(id);

        if (optionalDesafio.isPresent()) {
            return optionalDesafio.get();
        } else {
            throw new RuntimeException("Desafio não encontrado");
        }
    }

    public void atualizarDesafio(Long id, String novoNome) {
        Desafio desafio = buscarPorId(id);
        desafio.setNomeDesafio(novoNome);
        desafioRepository.save(desafio); // Salva as mudanças no banco de dados
    }
}
*/