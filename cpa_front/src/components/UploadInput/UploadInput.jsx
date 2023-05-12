import { Button } from "@mui/material"
import './UploadInput.css'
import {  useState } from 'react';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';

export default function ButtonCustom({text, label, type, variant, width, color}){
  const [file, setFile] = useState('');
  return(
    <>
      <div className="form-upload" onClick={() => document.querySelector('.input-field').click()}>
        <input type="file" accept="csv" className="input-field"/>
        <CloudUploadIcon color="azureRadiance" style={{ fontSize: '100px'}} />
      </div>
    </>
  )
}