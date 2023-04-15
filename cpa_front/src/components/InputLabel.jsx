export default function InputLabel({label, inputText, type}){

  return(
    <>
      <label>
        {label}
        <input 
          type={type}
        />
      </label>
    </>
  )
}