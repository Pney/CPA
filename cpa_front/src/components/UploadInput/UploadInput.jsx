import './UploadInput.css'
import {  useState } from 'react';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';

export default function ButtonCustom({handleFinal, ...props}){
  const [fileName, setFileName] = useState('');
  return(
    <>
      <div
        {...props}
        className="form-upload"
        onClick={() => document.querySelector('.input-field').click()}
        onChange={(e) => {
          setFileName(e.target.files[0].name)
          handleFinal(e.target.files[0])
        }}
      >
        <input type="file" accept=".csv" className="input-field"/>
        <CloudUploadIcon color="azureRadiance" style={{ fontSize: '100px'}} />
        <label> {fileName} </label>
      </div>
    </>
  )
}