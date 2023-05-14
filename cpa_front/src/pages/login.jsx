import {
  Box,
  Button,
  TextField,
  InputAdornment,
  IconButton,
  Link,
  useTheme,
} from '@mui/material';
import VisibilityIcon from '@mui/icons-material/Visibility';
import '../assets/css/login.css'
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import logoCpa from '../assets/image/logoCpa.jpg';
import Image from '../components/Image.jsx';
import { useState } from 'react';
import * as Yup from 'yup';
import authService from 'services/AuthService.js';

export default function Login() {
  const theme = useTheme();

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
        const response = await authService.login(email, password)
        return response;
      })
      .then((res) => {
        if (res.token) localStorage.setItem('token', res.token);
          return window.location.reload();
      })
      .catch((validationErrors) => {
        validationErrors.path === 'passwordText' ? setErroPassword(true) : setErroEmail(true)
      });
  };

  return (
      <Box
        className='container-center'
        width={'380px'}
        height={'480px'}
        sx={{ 'backgroundColor': theme.palette.elephant.main }}
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
            style={{'borderRadius': '1.50rem'}}
          />
        </div>
        <form className='form-body'>
          <div style={{ 'width': '235px' }}>
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
              className='mt-10 mb-20'
              fullWidth
              error={erroPassword}
              onChange={(e) => setPassword(e.target.value)}
              autoComplete={'off'}
              variant={'standard'}
              label={'Senha'}
              type={showPassword ? 'text' : 'password'}
              InputProps={{
                endAdornment: (
                  <InputAdornment
                    position='end'
                  >
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
            color={theme.palette.azureRadiance.main}
            fontSize={'0.8rem'}
            marginRight={'30%'}
            marginBottom={'25px'}
          >
            Esqueceu sua senha?
          </Link>
          <Button
            className='w-200 mt-20 br-20'
            variant='contained'
            color='white'
            onClick={handleSubmit}
          >
            Entrar
          </Button>
        </form>
      </Box>
  );
}