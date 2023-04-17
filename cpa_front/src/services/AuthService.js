import api from './api.js';
import { Component } from 'react';
export class AuthService extends Component {
  async createAccount(email, password) {
    const params = {
      email: email,
      password: password
    };

    console.log({params})
    return await api.post(`/api/auth/public/register`, params)
    .then((response) => {
      console.log({response})
      const data = {
        'token': response.data.token,
        'role': response.data.level,
      }
      console.log({data})
      return data;
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
  async login (email, password) {
    const params = {
      email: email,
      password: password
    }

    await api.post(`/api/auth/public/login`, params)
    .then((response) => {
        console.log({response})
        const data = {
          'token': response.data.token,
          'role': response.data.level,
        }
        console.log({data})
        return data;
    })
    .catch((err) => {
      console.log("ops! ocorreu um erro" + err);
    });
  }
}

const authService = new AuthService();
export default authService;