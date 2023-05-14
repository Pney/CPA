import React from 'react'
import { TextField, styled } from '@mui/material'


export const InputText = styled(TextField)(({theme})=> ({
    margin: '0px',
    "& .MuiOutlinedInput-root":{
        "&.Mui-focused fieldset":{
            bordercolor:'#000000'
         }
     

    }
    

}));


