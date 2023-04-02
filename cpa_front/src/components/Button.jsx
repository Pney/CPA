import { Button } from "@mui/material"

export default function ButtonCustom({text, label, type, variant, width, color}){

  return(
    <>
      <Button
        type={`${type}`}
        variant={`${variant}`}
        color={`${color}`}
      >

      </Button>
    </>
  )
}