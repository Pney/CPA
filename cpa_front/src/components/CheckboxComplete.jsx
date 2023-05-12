import { Checkbox } from "@mui/material";

export default function CheckboxComplete({defaultChecked, label, ...props}){
  return(
    <div>
      <Checkbox
        {...props}
        defaultChecked={defaultChecked}
      />
      <label
        color="#FFFFFF"
      >
        {label}
      </label>
    </div>
  )
}