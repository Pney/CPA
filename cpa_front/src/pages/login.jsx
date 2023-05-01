import {
  Box,
  Button,
  Container,
  TextField,
  InputAdornment,
  IconButton,
  Link,
} from '@mui/material';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
// import '../assets/css/login.css';
import logoCpa from '../assets/image/logoCpa.jpg';
import Image from '../components/Image.jsx';
import { useState } from 'react';
import * as Yup from 'yup';
import authService from 'services/AuthService.js';
import { useUserContext } from 'contexts/UserContext.jsx';


export default function Login() {

  const [showPassword, setShowPassword] = useState(false);
  const [erroPassword, setErroPassword] = useState(false);
  const [erroEmail, setErroEmail] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleChangeTypePassword = () => {
    setShowPassword(!showPassword);
  };

  const schema = Yup.object().shape({
    emailText: Yup.string()
      .email('Email Invalido')
      .required('Não pode ser nulo'),
    passwordText: Yup.string()
      .min(2, 'Senha muito pequena!')
      .required('Não pode ser nulo'),
  });

  const handleSubmit = async (event) => {
    event.preventDefault();
    setErroPassword(false);
    setErroEmail(false);
    schema
      .validate({ emailText: email, passwordText: password })
      .then(async () => {
        const response = await authService.createAccount(email, password)
        return response;
      })
      .then((res) => {
        console.log({ res });
        if (res.token) localStorage.setItem('token', res.token);
        return window.location.reload();
      })
      .catch((validationErrors) => {
        console.log({ validationErrors });
        validationErrors.path === 'passwordText' ? setErroPassword(true) : setErroEmail(true)
      });
  };

  return (
    <Container sx={{
      'backgroundColor': 'primary',
      'width': '100vw',
      'height': '100vh',
      'display': 'flex',
      'alignItems': 'center',
      'justifyContent': 'center',
    }}>
      <Box
        sx={{
          'width': '380px',
          'height': '480px',
          'backgroundColor': 'elephant',
          'boxShadow': '0px 15px 15px rgba(0, 0, 0, 0.25)'
        }}
      >
        <div
          style={{
            'marginTop': '2.5rem',
            'marginBottom': '2rem',
          }}
        >
          <Image
            url={logoCpa}
            alt={'Logo da CPA'}
            width={'230px'}
            height={'100px'}
            style={{'border-radius': '1.50rem'}}
          />
        </div>
        <form
          style={{
            'display': 'flex',
            'flexDirection': 'column',
            'justifyContent': 'center',
            'alignItems': 'center',
          }}
        >
          <div style={{
            'width': '235px',
          }}>
            <TextField
              fullWidth
              error={erroEmail}
              onChange={(e) => setEmail(e.target.value)}
              autoComplete={'off'}
              variant={'standard'}
              label={'Login'}
              sx={{ 'marginTop': '10px' }}
            />
            <TextField
              fullWidth
              error={erroPassword}
              onChange={(e) => setPassword(e.target.value)}
              autoComplete={'off'}
              variant={'standard'}
              label={'Senha'}
              type={showPassword ? 'text' : 'password'}
              sx={{ 'marginTop': '10px', 'marginBottom': '20px' }}
              InputProps={{
                endAdornment: (
                  <InputAdornment>
                    <IconButton
                      onClick={handleChangeTypePassword}
                    >
                      {showPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />
          </div>
          <Link
            href='#'
            color={'azureRadiance'}
            fontSize={'0.8rem'}
            marginRight={'30%'}
            marginBottom={'25px'}
          >
            Esqueceu sua senha?
          </Link>
          <Button
            onClick={handleSubmit}
            variant='contained'
            color='white'
            sx={{
              'width': '200px',
              'borderRadius': '20px',
              'marginTop': '20px',
            }}
          >
            Entrar
          </Button>
        </form>
      </Box>
    </Container>
  );
}