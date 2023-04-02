import api from './api.js';
import { Component } from 'react';
export class AuthService extends Component {
  createAccount(email, password) {
    const params = {
      email: email,
      password: password
    };

    console.log({params})
    api.post(`/api/auth/public/register`, params)
    .then((response) => {
      const token = response.data.token;
      // var data = new Date();
      // document.cookie = `token=${token} expires= ${data}`;
      localStorage.setItem('token', token);
      return document.location.reload(true);
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
    api.post(`/api/auth/public/login`, params)
    .then((response) => {
      const token = response.data.token;
      // var data = new Date(24);
      // document.cookie = `token=${token} expires=${data}`;
      localStorage.setItem('token', token);
      return document.location.reload(true);
    })
    .catch((err) => {
      console.log("ops! ocorreu um erro" + err);
    });
  }
}

const authService = new AuthService();
export default authService;