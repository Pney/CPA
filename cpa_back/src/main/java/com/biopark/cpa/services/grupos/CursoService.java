package com.biopark.cpa.services.grupos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.biopark.cpa.controllers.grupos.dto.GenericDTO;
import com.biopark.cpa.entities.grupos.Curso;
import com.biopark.cpa.repository.grupo.CursoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    // Filtrar Curso por ID
    public Curso buscarPorCodigo(String codigo) {
        var optionalCurso = cursoRepository.findByCodigoCurso(codigo);
        if (optionalCurso.isPresent()) {
            return optionalCurso.get();
        } else {
            throw new RuntimeException("Curso não encontrado!");
        }
    }

    // Editar Curso por ID
    public GenericDTO editarCurso(Curso cursoRequest) {
        try {
            Curso curso = buscarPorCodigo(cursoRequest.getCodigoCurso());
            // esse set vai ser para quando tiver a coluna Nome Curso no banco.
            // curso.setNomeCurso(cursoRequest.getNomeCurso());

            cursoRepository.save(curso);
            return GenericDTO.builder().status(HttpStatus.OK)
                    .mensagem("Curso " + cursoRequest.getCodigoCurso() + " editado com sucesso")
                    .build();
        } catch (Exception e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem(e.getMessage()).build();
        }
    }

    // Excluir Curso
    public GenericDTO excluirCurso(Long id) {
        try {
            var cursoDB = cursoRepository.findById(id);
            if (!cursoDB.isPresent()) {
                return GenericDTO.builder().status(HttpStatus.NOT_FOUND).mensagem("curso não encontrado").build();
            }
            Curso curso = cursoDB.get();
            cursoRepository.delete(curso);
            return GenericDTO.builder().status(HttpStatus.OK)
                    // Está sendo passando o get pelo codigo curso, pois, não tem a coluna nome ainda no banco.
                    .mensagem("Curso " + curso.getCodigoCurso() + " excluído com sucesso")
                    .build();
        } catch (EmptyResultDataAccessException e) {
            return GenericDTO.builder().status(HttpStatus.NOT_FOUND)
                    .mensagem("Curso " + id + " não encontrada")
                    .build();
        }
    }
}