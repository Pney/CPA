package com.biopark.cpa.services.pessoas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biopark.cpa.dto.cadastroCsv.CadastroDTO;
import com.biopark.cpa.dto.cadastroCsv.ErroValidation;
import com.biopark.cpa.dto.cadastroCsv.ValidationModel;
import com.biopark.cpa.entities.pessoas.Funcionario;
import com.biopark.cpa.entities.user.User;
import com.biopark.cpa.entities.user.enums.Level;
import com.biopark.cpa.entities.user.enums.Role;
import com.biopark.cpa.form.cadastroCsv.FuncionarioModel;
import com.biopark.cpa.repository.pessoas.FuncionarioRepository;
import com.biopark.cpa.repository.pessoas.UserRepository;
import com.biopark.cpa.services.security.GeneratePassword;
import com.biopark.cpa.services.utils.CsvParserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FuncionarioService {
    private final CsvParserService csvParserService;
    private final GeneratePassword generatePassword;
    private final UserRepository userRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Transactional
    public CadastroDTO cadastrarFuncionario(List<FuncionarioModel> funcionariosModel, boolean update) {
        List<ErroValidation> errors = csvParserService.validaEntrada(funcionariosModel);
        List<ErroValidation> warnings = new ArrayList<>();

        if (!errors.isEmpty()) {
            return CadastroDTO.builder().status(HttpStatus.BAD_REQUEST).erros(errors).warnings(warnings).build();
        }

        if (!update) {

            ValidationModel<FuncionarioModel> model = checarDuplicatas(funcionariosModel);
            List<ErroValidation> duplicatas = model.getErrors();
            warnings = model.getWarnings();
            funcionariosModel = model.getObjects();

            if (!duplicatas.isEmpty()) {
                return CadastroDTO.builder().status(HttpStatus.CONFLICT).erros(duplicatas).warnings(warnings).build();
            }

            List<User> users = new ArrayList<>();
            List<Funcionario> funcionarios = new ArrayList<>();

            for (FuncionarioModel funcionarioModel : funcionariosModel) {
                User user = User.builder()
                        .cpf(funcionarioModel.getCpf())
                        .name(funcionarioModel.getName())
                        .telefone(funcionarioModel.getTelefone())
                        .email(funcionarioModel.getEmail())
                        .password(generatePassword.getPwd())
                        .role(Role.FUNCIONARIO)
                        .level(Level.USER)
                        .build();

                Funcionario funcionario = Funcionario.builder()
                        .cracha(funcionarioModel.getCracha())
                        .area(funcionarioModel.getArea())
                        .user(user)
                        .build();

                users.add(user);
                funcionarios.add(funcionario);

            }

            userRepository.saveAll(users);
            funcionarioRepository.saveAll(funcionarios);

            return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
        }

        for (FuncionarioModel funcionarioModel : funcionariosModel) {
            User user = User.builder()
                    .cpf(funcionarioModel.getCpf())
                    .name(funcionarioModel.getName())
                    .telefone(funcionarioModel.getTelefone())
                    .email(funcionarioModel.getEmail())
                    .password(generatePassword.getPwd())
                    .role(Role.FUNCIONARIO)
                    .level(Level.USER)
                    .build();

            user = userRepository.findByCpf(user.getCpf()).get();

            Funcionario funcionario = Funcionario.builder()
                    .cracha(funcionarioModel.getCracha())
                    .area(funcionarioModel.getArea())
                    .user(user)
                    .build();

            userRepository.upsert(user);
            funcionarioRepository.upsert(funcionario);
        }

        return CadastroDTO.builder().status(HttpStatus.OK).erros(errors).warnings(warnings).build();
    }

    private ValidationModel<FuncionarioModel> checarDuplicatas(List<FuncionarioModel> funcionarios) {
        List<ErroValidation> erroValidations = new ArrayList<>();
        List<ErroValidation> warnings = new ArrayList<>();
        List<FuncionarioModel> unicosEmail = new ArrayList<>();
        List<FuncionarioModel> unicosCracha = new ArrayList<>();
        List<FuncionarioModel> unicosCpf = new ArrayList<>();

        HashMap<String, Integer> uniqueEmail = new HashMap<String, Integer>();
        HashMap<String, Integer> uniqueCracha = new HashMap<String, Integer>();
        HashMap<String, Integer> uniqueCpf = new HashMap<String, Integer>();

        int linha = 0;
        for (FuncionarioModel funcionario : funcionarios) {
            linha++;

            if (!uniqueEmail.containsKey(funcionario.getEmail())) {
                uniqueEmail.put(funcionario.getEmail(), linha);
                unicosEmail.add(funcionario);
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(linha)
                        .mensagem("Esta linha foi ignorada pois o Email já existe na linha: "
                                + uniqueEmail.get(funcionario.getEmail()))
                        .build());
                continue;
            }

            if (!uniqueCracha.containsKey(funcionario.getCracha())) {
                uniqueCracha.put(funcionario.getCracha(), linha);
                unicosCracha.add(funcionario);
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(linha)
                        .mensagem("Esta linha foi ignorada pois o crachá já existe na linha: "
                                + uniqueCracha.get(funcionario.getCracha()))
                        .build());
                continue;
            }

            if (!uniqueCpf.containsKey(funcionario.getCpf())) {
                uniqueCpf.put(funcionario.getCpf(), linha);
                unicosCpf.add(funcionario);
            } else {
                warnings.add(ErroValidation.builder()
                        .linha(linha)
                        .mensagem("Esta linha foi ignorada pois o cpf já existe na linha: "
                                + uniqueCpf.get(funcionario.getCpf()))
                        .build());
                continue;
            }

            if (userRepository.findByEmail(funcionario.getEmail()).isPresent()
                    | userRepository.findByCpf(funcionario.getCpf()).isPresent()
                    | funcionarioRepository.findByCracha(funcionario.getCracha()).isPresent()) {
                erroValidations
                        .add(ErroValidation.builder().linha(linha).mensagem("funcionario já cadastrado").build());
            }
        }

        List<FuncionarioModel> unicos = unicosEmail;
        unicos.retainAll(unicosCracha);

        return ValidationModel.<FuncionarioModel>builder().errors(erroValidations).warnings(warnings).objects(unicos)
                .build();
    }
}
