import api from './api.js';
import { Component } from 'react';
export class AuthService extends Component {
    async cadastroMembroCpa(name, email, cpf, telefone, funcao) {
        const params = {
            name: name,
            email: email,
            cpf: cpf,
            telefone: telefone,
            funcao: funcao
        };

        console.log({ params })
        return await api.post(`/api/auth/public/membros-cpa`, params)
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

const authService = new AuthService();
export default authService;