import { Button, useTheme } from '@mui/material'
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import CheckboxComplete from 'components/CheckboxComplete'
import InputArchive from 'components/InputArchive'
import TabelaCSV from 'components/TabelaCSV'
import { Formik, Form } from 'formik';
import { useState } from 'react';

export default function Importar(){
  const theme = useTheme();
  theme.palette.mode = 'dark';
  console.log({theme});

  const [csvData, setCsvData] = useState('');
  const [rowData, setRowData] = useState([]);
  const [columnsData, setColumnsData] = useState([]);
  const [showTabelaCSV, setShowTabelaCSV] = useState(false);

  const handleChangeInputArchive = (event) => {
    const reader = new FileReader();
    reader.readAsText(event.target.files[0]);                 
    reader.onload = function() {
      const csvTempl = reader.result;
      const rowTempl = csvTempl.split('\r\n');
      const columnsTempl = rowTempl[0].split(', ');
      const columnsFields = [{field: 'id'}];
      
      columnsTempl.forEach(element => {
        columnsFields.push({'field': element})
      });
      setColumnsData(columnsFields);
      setRowData(rowTempl);
      setCsvData(csvTempl);
      setShowTabelaCSV(true);
    }
    reader.onerror = function() {
      console.log(reader.error);
    };
  }
  return(
    <>
      <div
        style={{
        }}
      >
        <div style={{
          display: 'block',
          backgroundColor: theme.palette.alto.main,
          width: '65%',
          height: '200px',
          border: `0.05px solid ${theme.palette.nightRide.main}`,
          boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
          borderRadius: '44px',
          textAlign: 'center',
          margin: '2rem auto 2rem auto',
          marginBottom: '3%',
          padding: '10px'
        }}>
          <Formik
            initialValues={{
              tipo: '',
              csv: '',
              isUpdate: false,
            }}
            onSubmit={(values, actions) => {
              console.log({values})
            }}  
          >
            {({ values, errors, touched, handleChange, handleBlur, handleSubmit }) => (
              <Form
                style={{
                  textAlign: 'center'
                }}
              >
                <div>
                  <FormControl variant="filled" sx={{ m: 1, minWidth: 120 }}>
                    <InputLabel id="demo-simple-select-filled-label">Tipo</InputLabel>
                    <Select
                      labelId="demo-simple-select-filled-label"
                      id="demo-simple-select-filled"
                      value={values.tipo}
                      name='tipo'
                      onBlur={handleBlur}
                      onChange={handleChange}
                      sx={{
                        '& .MuiInputBase-input': {
                          backgroundColor: theme.palette.alto.dark,
                        },
                        '& .MuiSelect-select': {
                          backgroundColor: theme.palette.alto.dark,
                        },
                        '& .MuiSelect-filled': {
                          backgroundColor: theme.palette.alto.dark,
                        },
                        '&: 	.MuiSelect-filled': {
                          backgroundColor: theme.palette.alto.dark,
                        },
                        '&: focus': {
                          backgroundColor: theme.palette.alto.dark,
                        },
                        backgroundColor: theme.palette.alto.dark,
                        color: '#000000',
                        '& .MuiSelect-root': { // Adicionado a classe CSS
                          backgroundColor: '#1f2937 !important',
                        },
                      }}
                    >
                      <MenuItem value=""> </MenuItem>
                      <MenuItem value={1}>Desafio</MenuItem>
                      <MenuItem value={2}>Instituição</MenuItem>
                    </Select>
                  </FormControl>
                </div>
                <CheckboxComplete
                  name={'isUpdate'}
                  value={values.isUpdate}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  color={'azureRadiance'}
                  checked={values.isUpdate}
                  label={'Atualizar usuarios existentes'}
                />
                <InputArchive
                  name={'csv'}
                  onChange={(event) => {
                    setShowTabelaCSV(event.target.files.length > 0); 
                    handleChangeInputArchive(event)
                    handleChange(event);
                  }}
                />
                <Button
                  type={'submit'}
                  onClick={handleSubmit}
                  color='puertoRico'
                  variant='contained'
                >
                  <label>
                    Enviar
                  </label>
                </Button>
              </Form>
            )}
          </Formik>
        </div>
        { showTabelaCSV && columnsData  ? (
          <>
            <TabelaCSV
              columns={columnsData}
              rows={[
                {id: 1, Nome: 'Sofia', Sobrenome: 'Silva', Idade: '27', Cidade: 'São Paulo', País: 'Brasil'},
                {id: 2, Nome: 'Lucas', Sobrenome: 'Santos', Idade: '35', Cidade: 'Rio de Janeiro', País: 'Brasil'},
                {id: 3, Nome: 'Emma', Sobrenome: 'Johnson', Idade: '42', Cidade: 'Nova York', País: 'Estados Unidos'},
                {id: 4, Nome: 'Liam', Sobrenome: 'Garcia', Idade: '19', Cidade: 'Los Angeles', País: 'Estados Unidos'},
                {id: 5, Nome: 'Isabella', Sobrenome: 'Martinez', Idade: '31', Cidade: 'Cidade do México', País: 'México'},
                {id: 6, Nome: 'Noah', Sobrenome: 'Hernandez', Idade: '24', Cidade: 'Buenos Aires', País: 'Argentina'},
                {id: 7, Nome: 'Mia', Sobrenome: 'Perez', Idade: '29', Cidade: 'Madrid', País: 'Espanha'},
                {id: 8, Nome: 'Oliver', Sobrenome: 'Rodriguez', Idade: '38', Cidade: 'Barcelona', País: 'Espanha'},
                {id: 9, Nome: 'Charlotte', Sobrenome: 'Gonzalez', Idade: '21', Cidade: 'Paris', País: 'França'},
                {id: 10, Nome: 'Ethan', Sobrenome: 'Nguyen', Idade: '26', Cidade: 'Ho Chi Minh', País: 'Vietnã'},
                {id: 11, Nome: 'Amelia', Sobrenome: 'Kim', Idade: '33', Cidade: 'Seul', País: 'Coreia do Sul'},
                {id: 12, Nome: 'Mason', Sobrenome: 'Lee', Idade: '23', Cidade: 'Tóquio', País: 'Japão'},
                {id: 13, Nome: 'Harper', Sobrenome: 'Kim', Idade: '20', Cidade: 'Sydney', País: 'Austrália'},
                {id: 14, Nome: 'Aiden', Sobrenome: 'Brown', Idade: '30', Cidade: 'Toronto', País: 'Canadá'},
                {id: 15, Nome: 'Emily', Sobrenome: 'Davis', Idade: '28', Cidade: 'Vancouver', País: 'Canadá'},
                {id: 16, Nome: 'Carter', Sobrenome: 'Miller', Idade: '39', Cidade: 'Londres', País: 'Reino Unido'},
                {id: 17, Nome: 'Abigail', Sobrenome: 'Wilson', Idade: '25', Cidade: 'Manchester', País: 'Reino Unido'},
                {id: 18, Nome: 'Alexander', Sobrenome: 'Smith', Idade: '22', Cidade: 'Berlim', País: 'Alemanha'},
                {id: 19, Nome: 'Madison', Sobrenome: 'Schmidt', Idade: '36', Cidade: 'Brasil', País: 'AUAU'}
              ]}
            />
          </>
        ) : null}
      </div>
    </>
  )
}