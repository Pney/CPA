import { ClassNames } from '@emotion/react'
import React, { useState } from 'react'
import './formulario.css'
import { FormControl, MenuItem, Select, InputLabel, TextField, Button } from '@mui/material'


const Formulario = () => {
    const [funcao, setFuncao] = useState('')
    // const handleChange = (value) =>{
    //     setfuncao(value)
    // }
    return (
        <div style={{
            marginTop: '10px',
            width: '850px',
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%,-50%)'
        }}>
            <form className='formulario-padrao'>
                <div>
                    <div style={{ margin: '8px' }}>
                        <TextField sx={{ width: '800px' }} id="outlined-basic" label="NOME" variant="outlined" type='text' />
                    </div>
                    <div style={{ margin: '8px' }}>
                        <TextField sx={{ width: '800px' }} id="outlined-basic" label="E-MAIL" variant="outlined" type='email' />
                    </div>
                    <div style={{ margin: '8px', display: 'flex',justifyContent: 'space-around' }}>
                        <TextField sx={{ width:'200px' }} id="outlined-basic" label="CPF" variant="outlined" type='string' />


                        <TextField sx={{width:'200px'}}id="outlined-basic" label="TELEFONE" variant="outlined" type='tel' />
                    </div>
                    <div style={{ margin: '8px' }}>
                        <FormControl sx={{ width: '800px' }} fullWidth>
                            <InputLabel id="demo-simple-select-label">Função</InputLabel>
                            <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                value={funcao}
                                label="Função"
                                onChange={(event) => setFuncao(event.target.value)}
                            >
                                <MenuItem value={"PROFESSOR"}>PROFESSOR</MenuItem>
                                <MenuItem value={"ALUNO"}>ALUNO</MenuItem>
                                <MenuItem value={"EXTERNO"}>EXTERNO</MenuItem>
                                <MenuItem value={"FUNCIONARIO"}>FUNCIONÁRIO</MenuItem>
                            </Select>
                        </FormControl>
                    </div>

                </div>
                <Button sx={{}} variant="contained">GRAVAR</Button>


            </form>

        </div>
    )
}

export default Formulario