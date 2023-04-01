
import { Alert } from '@mui/material';
import 'assets/css/App.css';
import api from 'services/api.js';
import AppRoutes from './routes/index.js'

function App() {
  return (
    <div className="App">
      <AppRoutes/>
    </div>
  );
}

export default App;
