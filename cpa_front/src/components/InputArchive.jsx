import { Input } from "@mui/material";
import { useField } from 'formik';

export default function InputArchive(props) {
  const [field] = useField(props.name);

  const handleChange = (event) => {
    // const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (e) => {
      field.onChange({
        target: {
          name: field.name,
          value: e.target.result,
        }
      });
    };
  }
  return(
    <div>
      <Input
        type="file"
        onChange={handleChange}
        inputProps={{
          accept: '.csv'
        }}
        {...field}
        {...props}
      />
    </div>
  )
}