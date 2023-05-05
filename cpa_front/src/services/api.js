import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': '*',
    'Access-Control-Allow-Headers': '*',
    // 'Authorization': ,
  },
  // auth: {'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZWRzYWRAc2Fkc2EuY29tIiwidXNlclJvbGUiOlt7ImF1dGhvcml0eSI6IkNQQSJ9XSwiaWF0IjoxNjgzMTI4MDI1LCJleHAiOjE2ODMxNTY4MjV9.2MKvFdobv-YB1EDiUdx2Dc6F6InyldI7ypPe7TvWfCs'}, 
  withCredentials: true
});
// res.header("Access-Control-Allow-Headers", 
// "Origin, X-Requested-With, Content-Type, Accept, ");


api.interceptors.request.use(
  async (config) => {
    const token = localStorage.getItem('token');
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
  },
  (error) => {
    console.log(error);
  }
);
console.log({api})

export default api;