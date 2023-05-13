import api from './api.js';
import { Component } from 'react';
export class CadastroService extends Component {
    async cadastroMembroCpa(name, email, cpf, telefone) {
        const params = {
            name: name,
            email: email,
            cpf: cpf,
            telefone: telefone,            
        };

        console.log({ params })
        return await api.post(`/api/membros-cpa`, params)
            .then((response) => {
                console.log({ response })
                const data = {
                    'token': response.data.token,
                    'role': response.data.level,
                }
                console.log({ data })
                return data;
            })
            .catch((err) => {
                console.log("ops! ocorreu um erro" + err);
            });
    }

}

const cadastroService = new CadastroService();
export default cadastroService;