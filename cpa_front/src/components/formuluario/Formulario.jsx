
import React, { useState } from 'react'
import './formulario.css'
import { TextField, Button } from '@mui/material'
import { useFormik } from 'formik';
import * as Yup from 'yup';
import MaskedInput from 'react-text-mask'
import cadastroService from 'services/CadastroService';
import { InputText } from 'components/InputText';

const Formulario = () => {

    const schema = Yup.object().shape({
        cpf: Yup.string().required('CPF é obrigatório').matches(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/, 'CPF inválido'),
        name: Yup.string().required('Nome é obrigatório'),
        email: Yup.string().required('E-mail é obrigatório').email(),
        telefone: Yup.string().required(''),

    });

    function TextMaskCustom(props) {
        const { inputRef, ...other } = props;

        const cpfMask = [/\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '-', /\d/, /\d/];

        return (
            <MaskedInput
                {...other}
                ref={(ref) => {
                    inputRef(ref ? ref.inputElement : null);
                }}
                mask={cpfMask}
                placeholderChar={'\u2000'}
                showMask={false}
            />
        );
    }

    const [cpf, setCpf] = useState("");

    const formik = useFormik({
        initialValues: {
            cpf: '',
            name: '',
            email: '',
            telefone: ''
        },
        validationSchema: schema,
        onSubmit: async values => {
            try {
                cadastroService.cadastroMembroCpa(values.name, values.email, values.cpf, values.telefone)

            } catch (error) {
                console.log(error);

            }
        },
    });

    const handleCpfChange = (event) => {
        setCpf(event.target.value);
        formik.handleChange(event);
    };
    return (
        <div style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            minHeight: '100vh'
        }}>
            <div style={{
                width: '850px',
                margin: '0 auto'
            }}>
                <form onSubmit={formik.handleSubmit} className='formulario-padrao'>
                    <div>
                        <div style={{ margin: '35px' }}>
                            <InputText sx={{ width: '800px' }}
                                name={'name'}
                                label="NOME"
                                value={formik.values.name}
                                onChange={formik.handleChange}
                                variant="outlined"
                                type='text' />
                        </div>
                        <div style={{ margin: '35px' }}>
                            <TextField sx={{ width: '800px' }}
                                name={'email'}
                                label="E-MAIL"
                                value={formik.values.email}
                                onChange={formik.handleChange}
                                variant="outlined"
                                type='email' />
                        </div>
                        <div style={{ padding: '35px', display: 'flex', justifyContent: 'space-around', width: '800px' }}>

                            <TextField
                                sx={{ width: '45%' }}

                                name={'cpf'}
                                label="CPF"
                                variant="outlined"
                                type="string"
                                value={formik.values.cpf}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                                error={formik.touched.cpf && Boolean(formik.errors.cpf)}
                                helperText={formik.touched.cpf && formik.errors.cpf}
                                InputProps={{
                                    // inputComponent: TextMaskCustom
                                }}
                            />

                            <TextField sx={{ width: '45%' }}
                                name={'telefone'}
                                valeu={formik.values.telefone}
                                onChange={formik.handleChange}
                                label="TELEFONE" variant="outlined" type='tel' />
                        </div>
                        {/* <div style={{ padding: '35px', margin: '8px' }}>
                        <FormControl sx={{ width: '800px' }} fullWidth>
                            <InputLabel id="demo-simple-select-label">Função</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                name={'funcao'}
                                value={formik.values.funcao}
                                label="Função"
                                onChange={formik.handleChange}
                            >
                                <MenuItem value={"PROFESSOR"}>PROFESSOR</MenuItem>
                                <MenuItem value={"ALUNO"}>ALUNO</MenuItem>
                                <MenuItem value={"EXTERNO"}>EXTERNO</MenuItem>
                                <MenuItem value={"FUNCIONARIO"}>FUNCIONÁRIO</MenuItem>
                            </Select>
                        </FormControl>
                    </div> */}
                        <Button type={'submit'}
                            sx={{ width: '600px' }}
                            variant="contained"
                        >
                            GRAVAR
                        </Button>

                    </div>

                </form>

            </div>
        </div>
    )
}

export default Formulario