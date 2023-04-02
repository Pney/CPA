export default function Menu(){
  console.log(localStorage.getItem('token'));
  if (localStorage.getItem('token'))
    console.log('foi');
  else
    console.log('Não Foi')

  return(
    <span>
      Teste
    </span>
  )
}