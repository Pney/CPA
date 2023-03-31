import { LocalSeeOutlined } from '@mui/icons-material';
import api from './api.js';
// import { Jwt } from 'jsonwebtoken';
import { Component } from 'react';
// import { useHistory } from 'react-router';


export class AuthService extends Component {
  createAccount(email, password) {
    const params = {
      email: email,
      password: password
    };

    console.log({params})
    api.post(`/api/auth/public/register`, params)
    .then((response) => {
      console.log({response});
      const token = response.data.token;
      localStorage.setItem('token', token);
      return;
    })
    .catch((err) => {
      console.log("ops! ocorreu um erro" + err);
    });
  }
      // {
      //   headers: {
      //     Authorization: `Bearer ${localStorage.getItem('token')}`
      //   }
      // }
  login (email, password) {
    const params = {
      email: email,
      password: password
    }
    return document.location.reload(true);
    api.post(`/api/auth/public/login`, params)
    .then((response) => {
      console.log({response});
      const token = response.data.token;
      console.log({token})
      localStorage.setItem('token', token);
      const retorno = localStorage.getItem('token')
      console.log({retorno})
      return;
      // const decoded = Jwt.verify(token, 'secretKey');
      // navigate('/');
    })
    .catch((err) => {
      console.log("ops! ocorreu um erro" + err);
    });
  }

  // componentDidUpdate(prevProps, prevState) {
  //   if (prevState.authToken !== this.state.authToken) {
  //     if (this.state.authToken) {
  //       api.defaults.headers.common['Authorization'] = `Bearer ${this.state.authToken}`;
  //     } else {
  //       delete api.defaults.headers.common['Authorization'];
  //     }
  //   }
  // }

    // render() {
    //   return null;
    // }
}

const authService = new AuthService();
export default authService