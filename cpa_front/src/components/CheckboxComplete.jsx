import { Checkbox, FormControlLabel } from "@mui/material";

export default function CheckboxComplete({defaultChecked, label, ...props}){
  return(
    <div style={{display: 'flex', flexDirection: 'column'}}>
      {/* <Checkbox
        label={label}
        {...props}
      /> */}
      <FormControlLabel control={<Checkbox {...props} defaultChecked={defaultChecked} />} label="Atualizar Todos" />

      {/* <Typography fontSize={12}>
        {label}
      </Typography> */}
    </div>
  )
}