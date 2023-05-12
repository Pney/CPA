package com.biopark.cpa.services.grupos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.biopark.cpa.controllers.grupos.dto.GenericDTO;
import com.biopark.cpa.entities.grupos.Turma;
import com.biopark.cpa.repository.grupo.TurmaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepository;

    // Filtrar Turma por ID
    public Turma buscarPorCodigo(String codigo) {
        var optionalTurma = turmaRepository.findByCodigoTurma(codigo);

        if (optionalTurma.isPresent()) {
            return optionalTurma.get();
        } else {
            throw new RuntimeException("Turma não encontrada!");
        }
    }

    // Editar Turma por ID
    public GenericDTO editarTurma(Turma turmaRequest) {
        try {
            Turma turma = buscarPorCodigo(turmaRequest.getCodigoTurma());
            // esse set vai ser para quando tiver a coluna Nome Curso no banco.
            // turma.setNomeTurma(turmaRequest.getNomeTurma());

            turmaRepository.save(turma);
            return GenericDTO.builder().status(HttpStatus.OK)
                    .mensagem("Turma " + turmaRequest.getCodigoTurma() + " editado com sucesso")
                    .build();
        } catch (Exception e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem(e.getMessage()).build();
        }
    }

    // Excluir Turma
    public GenericDTO excluirTurma(Long id) {
        try {
            var turmaDB = turmaRepository.findById(id);
            if (!turmaDB.isPresent()) {
                return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem("turma não encontrada").build();
            }
            Turma turma = turmaDB.get();
            turmaRepository.delete(turma);
            return GenericDTO.builder().status(HttpStatus.OK)
                    // Está sendo passando o get pelo codigo turma, pois, não tem a coluna nome
                    // ainda no banco.
                    .mensagem("Turma " + turma.getCodigoTurma() + " excluída com sucesso")
                    .build();
        } catch (EmptyResultDataAccessException e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND)
                    .mensagem("Turma " + id + " não encontrada")
                    .build();
        }
    }
}