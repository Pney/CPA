
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
        <div className='box'>
            <form onSubmit={formik.handleSubmit} className='formulario-padrao'>
                <div className='ibox-content centralized' style={{width: '1000px', height: '500px', borderRadius: '40px',}}>
                    <div className='divider-title' style={{ width: '1033px',}}>
                        <h2 style={{ color: '#000000'}}> Cadastrar Membro CPA</h2>
                    </div>
                    <div>
                        <TextField sx={{ width: '800px' }}
                            name={'name'}
                            label="NOME"
                            color='nightRide'
                            value={formik.values.name}
                            onChange={formik.handleChange}
                            variant="outlined"
                            type='text' 
                            required
                        />
                    </div>
                    <div >
                        <TextField sx={{ width: '800px' }}
                            name={'email'}
                            label="E-MAIL"
                            color='nightRide'
                            value={formik.values.email}
                            onChange={formik.handleChange}
                            variant="outlined"
                            type='email'
                            required
                        />
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'space-around', width: '800px' }}>

                        <TextField
                            sx={{ width: '45%' }}
                            name={'cpf'}
                            label="CPF"
                            color='nightRide'
                            variant="outlined"
                            type="string"
                            value={formik.values.cpf}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.cpf && Boolean(formik.errors.cpf)}
                            helperText={formik.touched.cpf && formik.errors.cpf}
                            required
                        />

                        <TextField sx={{ width: '45%' }}
                            name={'telefone'}
                            color='nightRide'
                            valeu={formik.values.telefone}
                            onChange={formik.handleChange}
                            label="TELEFONE" variant="outlined" type='tel' 
                            required
                        />
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
                        color='success'
                    >
                        SALVAR
                    </Button>

                </div>
            </form>
        </div>
    )
}

export default Formulario