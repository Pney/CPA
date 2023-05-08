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
      console.log("ops! ocorreu um erro no createAccount" + err);
    });
  }

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
      console.err("ops! ocorreu um erro no login" + err);
    });
  }

  async getToken(){
    const token = localStorage.getItem('token')
    console.log(token);
    return token;  
  }

  async authToken(){
    await api.post(`/api/auth/authenticate`)
    .then((response) => {
      if(response.status === 200) return;
    })
    .catch((err) => {
      if(err.response.status === 403){
        localStorage.removeItem('token')
        return window.location.reload();
      }
      console.error("ops! ocorreu um erro no authToken" + err);
    })
  }

  async logout(){
    await api.post(`/api/auth/logout`)
    .then((response) => {
      if (response.data){
        localStorage.removeItem('token')
        return window.location.reload();
      }
    })
    .catch((err) => {
      console.error("ops! ocorreu um erro no logout" + err);
    })
  }
}

const authService = new AuthService();
export default authService;