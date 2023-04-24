import { Input, Button } from "@mui/material";
import { useField } from 'formik';

export default function InputArchive(props) {
  const [field] = useField(props.name);

  // const handleChange = (event) => {
  //   const reader = new FileReader();
  //   reader.onload = (e) => {
  //     field.onChange({
  //       target: {
  //         name: field.name,
  //         value: e.target.result,
  //       }
  //     });
  //   };
  // }
  return(
    <div>
      <Input
        type="file"
        inputProps={{
          accept: '.csv'
        }}
        {...field}
        {...props}
      />
    </div>
  )
}