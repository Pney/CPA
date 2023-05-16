package com.biopark.cpa.services.pessoas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biopark.cpa.dto.cadastroCsv.CadastroDTO;
import com.biopark.cpa.dto.cadastroCsv.ErroValidation;
import com.biopark.cpa.dto.cadastroCsv.ValidationModel;
import com.biopark.cpa.entities.pessoas.Professor;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.entities.user.enums.Level;
import com.biopark.cpa.entities.user.enums.Role;
import com.biopark.cpa.form.cadastroCsv.ProfessorModel;
import com.biopark.cpa.repository.pessoas.ProfessorRepository;
import com.biopark.cpa.repository.pessoas.UserRepository;
import com.biopark.cpa.services.security.GeneratePassword;
import com.biopark.cpa.services.utils.CsvParserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final CsvParserService csvParserService;
    private final UserRepository userRepository;
    private final ProfessorRepository professorRepository;
    private final GeneratePassword generatePassword;

    @Transactional
    public CadastroDTO cadastrarProfessor(List<ProfessorModel> professoresModel, boolean update) {
        List<ErroValidation> errors = csvParserService.validaEntrada(professoresModel);
        List<ErroValidation> warnings = new ArrayList<>();

        if (!errors.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.BAD_REQUEST).erros(errors).warnings(warnings).build();
        }

        if (!update) {
            ValidationModel<ProfessorModel> model = checarDuplicatas(professoresModel);
            List<ErroValidation> duplicatas = model.getErrors();
            warnings = model.getWarnings();
            professoresModel = model.getObjects();

            if (!duplicatas.isEmpty()) {
                return CadastroDTO.builder().status(HttpStatus.CONFLICT).erros(duplicatas).warnings(warnings).build();
            }

            List<User> users = new ArrayList<>();
            List<Professor> professores = new ArrayList<>();

            for (ProfessorModel professorModel : professoresModel) {
                User user = User.builder()
                        .cpf(professorModel.getCpf())
                        .name(professorModel.getName())
                        .telefone(professorModel.getTelefone())
                        .email(professorModel.getEmail())
                        .password(generatePassword.getPwd())
                        .role(Role.PROFESSOR)
                        .level(Level.USER)
                        .build();

                Professor professor = Professor.builder()
                        .cracha(professorModel.getCracha())
                        .isCoordenador(false)
                        .user(user)
                        .build();

                users.add(user);
                professores.add(professor);

            }

            userRepository.saveAll(users);
            professorRepository.saveAll(professores);
            return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
        }

        for (ProfessorModel professorModel : professoresModel) {
            User user = User.builder()
                .cpf(professorModel.getCpf())
                .email(professorModel.getEmail())
                .name(professorModel.getName())
                .telefone(professorModel.getTelefone())
                .password(generatePassword.getPwd())
                .role(Role.PROFESSOR)
                .level(Level.USER)
                .build();
            
            userRepository.upsert(user);

            user = userRepository.findByCpf(user.getCpf()).get();

            professorRepository.upsert(
                Professor.builder().cracha(professorModel.getCracha())
                .isCoordenador(false)
                .user(user)
                .build()
            );
        }

        return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
    }

    private ValidationModel<ProfessorModel> checarDuplicatas(List<ProfessorModel> professores) {
        List<ErroValidation> erroValidations = new ArrayList<>();
        List<ErroValidation> warnings = new ArrayList<>();
        List<ProfessorModel> unicosEmail = new ArrayList<>();
        List<ProfessorModel> unicosCracha = new ArrayList<>();
        List<ProfessorModel> unicosCpf = new ArrayList<>();

        HashMap<String, Integer> uniqueEmail = new HashMap<String, Integer>();
        HashMap<String, Integer> uniqueCracha = new HashMap<String, Integer>();
        HashMap<String, Integer> uniqueCpf = new HashMap<String, Integer>();

        int linha = 0;
        for (ProfessorModel professor : professores) {
            linha++;

            if (!uniqueEmail.containsKey(professor.getEmail())) {
                uniqueEmail.put(professor.getEmail(), linha);
                unicosEmail.add(professor);
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(linha)
                        .mensagem("Esta linha foi ignorada pois o Email já existe na linha: "
                                + uniqueEmail.get(professor.getEmail()))
                        .build());
                continue;
            }

            if (!uniqueCracha.containsKey(professor.getCracha())) {
                uniqueCracha.put(professor.getCracha(), linha);
                unicosCracha.add(professor);
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(linha)
                        .mensagem("Esta linha foi ignorada pois o crachá já existe na linha: "
                                + uniqueCracha.get(professor.getCracha()))
                        .build());
                continue;
            }

            if (!uniqueCpf.containsKey(professor.getCpf())) {
                uniqueCpf.put(professor.getCpf(), linha);
                unicosCpf.add(professor);
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(linha)
                        .mensagem("Esta linha foi ignorada pois o cpf já existe na linha: "
                                + uniqueCpf.get(professor.getCpf()))
                        .build());
                continue;
            }

            if (userRepository.findByEmail(professor.getEmail()).isPresent()
                    | userRepository.findByCpf(professor.getCpf()).isPresent()
                    | professorRepository.findByCracha(professor.getCracha()).isPresent()) {
                erroValidations.add(ErroValidation.builder().linha(linha).mensagem("Professor já cadastrado").build());
            }
        }

        List<ProfessorModel> unicos = unicosEmail;
        unicos.retainAll(unicosCracha);

        return ValidationModel.<ProfessorModel>builder().errors(erroValidations).warnings(warnings).objects(unicos)
                .build();
    }
}
