import api from './api.js';
import { Component } from 'react';
export class ImportService extends Component {
  async importCSVDesafio(csv, isUpdate) {
    const params = {
      file: csv,
      update: isUpdate,
    };

    console.log({params})
    console.log({api})
    return await api.post(`/api/instituicao `, params)
    .then((response) => {
      console.log({response})
      const data = {}
      console.log({data})
      return data;
    })
    .catch((err) => {
      console.log("ops! ocorreu um erro" + err);
    });
  }
  async importCSVInstituicao(email, password) {
    const params = {
      email: email,
      password: password
    };

    console.log({params})
    return await api.post(`/api/turma`, params)
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

const importService = new ImportService();
export default importService;