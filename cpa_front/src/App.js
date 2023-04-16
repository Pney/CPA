import 'assets/css/App.css';
import AppRoutes from './routes/index.js'
import { userContext } from 'contexts/UserContext.jsx'

function App() {
  return (
    <div className="App">
      <userContext>
        <AppRoutes/>
      </userContext>
    </div>
  );
}

export default App;
