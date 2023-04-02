
import { Alert } from '@mui/material';
import 'assets/css/App.css';
import api from 'services/api.js';
import AppRoutes from './routes/index.js'

function App() {
  api.get(`/user/login`)
    .then((response) => {
      console.log("Response:")
      console.log(response.data)
    })
    .catch((err) => {
      console.log("ops! ocorreu um erro" + err);
    });
  return (
    <div className="App">
      <AppRoutes />
    </div>
  );
}

export default App;
