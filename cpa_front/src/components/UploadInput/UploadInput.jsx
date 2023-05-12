import './UploadInput.css'
import {  useState } from 'react';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';

export default function ButtonCustom({text, label, type, variant, width, color}){
  const [fileName, setFileName] = useState('');
  return(
    <>
      <div 
        className="form-upload"
        onClick={() => document.querySelector('.input-field').click()}
        onChange={(e) => {
        setFileName(e.target.files[0].name)
      }}>
        <input type="file" accept=".csv" className="input-field"/>
        <CloudUploadIcon color="azureRadiance" style={{ fontSize: '100px'}} />
        <label> {fileName} </label>
      </div>
    </>
  )
}