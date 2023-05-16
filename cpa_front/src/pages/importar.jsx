import { Button, useTheme } from '@mui/material'
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import CheckboxComplete from 'components/CheckboxComplete'
import TabelaCSV from 'components/TabelaCSV'
import UploadInput from '../components/UploadInput/UploadInput.jsx'
import { Formik, Form } from 'formik';
import { useState } from 'react';
import importService from 'services/ImportService';

export default function Importar(){
  const [csvData, setCsvData] = useState('', '', '', '', '');
  const [rowData, setRowData] = useState(['', '', '', '', '']);
  const [columnsData, setColumnsData] = useState([]);
  const [showTabelaCSV, setShowTabelaCSV] = useState(false);

  const handleChangeInputArchive = (event) => {
    const reader = new FileReader();
    console.log({event})
    reader.readAsText(event);                 
    reader.onload = function() {
      const csvTempl = reader.result;
      let columnsTempl = [];
      console.log({csvTempl})
      const rowTempl = csvTempl.split('\r\n');
      console.log({rowTempl})
      rowTempl.forEach((element, index) => {
        console.log({element});
        console.log({index});
        columnsTempl.push([(index + 1)+ ', ' + element])
        console.log({columnsTempl});
      });
      columnsTempl = rowTempl[0].split(', ');
      console.log({columnsTempl})
      const columnsFields = [{field: 'id'}];
      
      columnsTempl.forEach(element => {
        columnsFields.push({'field': element})
      });
      console.log({columnsFields})
      setColumnsData(columnsFields);
      setRowData(rowTempl);
      setCsvData(csvTempl);
      setShowTabelaCSV(true);
      setTimeout(() => {
        console.log({rowData})
        console.log({csvData})
      }, 2000);
    }
    reader.onerror = function() {
      console.log(reader.error);
    };
  }
  return(
    <>
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          // alignItems: 'center',
        }}
      >
        <div className='panel-body'>
        <h2 style={{ color: '#000000'}}> Importações</h2>
          <Formik
            initialValues={{
              tipo: '',
              csv: '',
              isUpdate: false,
            }}
            onSubmit={(values, actions) => {
              importService.importCSVDesafio(values.csv, values.isUpdate);
              console.log({values});
            }}  
          >
            {({ values, errors, touched, handleChange, handleBlur, handleSubmit }) => (
              <Form
                style={{
                  textAlign: 'center'
                }}
              >
                <div style={{display: 'flex', marginBottom: '1.25rem'}}>
                  <UploadInput
                    name='csv'
                    onBlur={handleBlur}
                    value={values.csv}
                    onChange={handleChange}
                    handleFinal={handleChangeInputArchive}
                  />
                  <div style={{marginLeft: '1.25rem'}}>
                    <FormControl variant="filled" sx={{ m: 1, minWidth: 120 }}>
                      <InputLabel id="demo-simple-select-filled-label">Tipo</InputLabel>
                      <Select
                        labelId="demo-simple-select-filled-label"
                        id="demo-simple-select-filled"
                        value={values.tipo}
                        name='tipo'
                        onBlur={handleBlur}
                        onChange={handleChange}
                      >
                        <MenuItem value=""> </MenuItem>
                        <MenuItem value={1}>Cursos</MenuItem>
                        <MenuItem value={2}>Instituições</MenuItem>
                        <MenuItem value={3}>Turmas</MenuItem>
                        <MenuItem value={4}>Professores</MenuItem>
                        <MenuItem value={5}>Alunos</MenuItem>
                        <MenuItem value={6}>Funcionários</MenuItem>
                      </Select>
                    </FormControl>
                    <CheckboxComplete
                      name={'isUpdate'}
                      value={values.isUpdate}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      color={'success'}
                      checked={values.isUpdate}
                      label={'Atualizar usuarios existentes'}
                    />
                    </div>
                  </div>
                {/* <InputArchive
                  name={'csv'}
                  onChange={(event) => {
                    setShowTabelaCSV(event.target.files.length > 0); 
                    handleChangeInputArchive(event)
                    handleChange(event);
                  
                /> */}
                {/* <MuiFileInput 
                  name={'csv'}
                  value={values.csv} 
                  onBlur={handleBlur}
                  onChange={(event) => {
                    console.log({event})
                    setShowTabelaCSV(event.File ? true : false); 
                    handleChangeInputArchive(event)
                    handleChange(event.File)
                  }}
                /> */}
                <Button
                  type={'submit'}
                  onClick={handleSubmit}
                  color='oceanGreen'
                  variant='contained'
                >
                  Enviar
                </Button>
              </Form>
            )}
          </Formik>
          {/* <ToastContainer
            position="top-right"
            autoClose={5000}
            hideProgressBar={false}
            newestOnTop={false}
            closeOnClick
            rtl={false}
            pauseOnFocusLoss
            draggable
            pauseOnHover
            theme="light"
            />
          <ToastContainer /> */}
          {/* <DropInputFile
            name={'csv'}
            onFileChange={(files) => console.log({files})}
          /> */}
        </div>
        { showTabelaCSV && columnsData  ? (
          <>
            <TabelaCSV
              columns={columnsData}
              rows={{rowData}}
              // rows={[
              //   {id: 1, Nome: 'Sofia', Sobrenome: 'Silva', Idade: '27', Cidade: 'São Paulo', País: 'Brasil'},
              //   {id: 2, Nome: 'Lucas', Sobrenome: 'Santos', Idade: '35', Cidade: 'Rio de Janeiro', País: 'Brasil'},
              //   {id: 3, Nome: 'Emma', Sobrenome: 'Johnson', Idade: '42', Cidade: 'Nova York', País: 'Estados Unidos'},
              //   {id: 4, Nome: 'Liam', Sobrenome: 'Garcia', Idade: '19', Cidade: 'Los Angeles', País: 'Estados Unidos'},
              //   {id: 5, Nome: 'Isabella', Sobrenome: 'Martinez', Idade: '31', Cidade: 'Cidade do México', País: 'México'},
              //   {id: 6, Nome: 'Noah', Sobrenome: 'Hernandez', Idade: '24', Cidade: 'Buenos Aires', País: 'Argentina'},
              //   {id: 7, Nome: 'Mia', Sobrenome: 'Perez', Idade: '29', Cidade: 'Madrid', País: 'Espanha'},
              //   {id: 8, Nome: 'Oliver', Sobrenome: 'Rodriguez', Idade: '38', Cidade: 'Barcelona', País: 'Espanha'},
              //   {id: 9, Nome: 'Charlotte', Sobrenome: 'Gonzalez', Idade: '21', Cidade: 'Paris', País: 'França'},
              //   {id: 10, Nome: 'Ethan', Sobrenome: 'Nguyen', Idade: '26', Cidade: 'Ho Chi Minh', País: 'Vietnã'},
              //   {id: 11, Nome: 'Amelia', Sobrenome: 'Kim', Idade: '33', Cidade: 'Seul', País: 'Coreia do Sul'},
              //   {id: 12, Nome: 'Mason', Sobrenome: 'Lee', Idade: '23', Cidade: 'Tóquio', País: 'Japão'},
              //   {id: 13, Nome: 'Harper', Sobrenome: 'Kim', Idade: '20', Cidade: 'Sydney', País: 'Austrália'},
              //   {id: 14, Nome: 'Aiden', Sobrenome: 'Brown', Idade: '30', Cidade: 'Toronto', País: 'Canadá'},
              //   {id: 15, Nome: 'Emily', Sobrenome: 'Davis', Idade: '28', Cidade: 'Vancouver', País: 'Canadá'},
              //   {id: 16, Nome: 'Carter', Sobrenome: 'Miller', Idade: '39', Cidade: 'Londres', País: 'Reino Unido'},
              //   {id: 17, Nome: 'Abigail', Sobrenome: 'Wilson', Idade: '25', Cidade: 'Manchester', País: 'Reino Unido'},
              //   {id: 18, Nome: 'Alexander', Sobrenome: 'Smith', Idade: '22', Cidade: 'Berlim', País: 'Alemanha'},
              //   {id: 19, Nome: 'Madison', Sobrenome: 'Schmidt', Idade: '36', Cidade: 'Brasil', País: 'AUAU'}
              // ]}
            />
          </>
        ) : null}
      </div>
    </>
  )
}