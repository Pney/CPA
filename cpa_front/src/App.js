import AppRoutes from './routes/index.js'
import { UserContext } from 'contexts/UserContext.jsx'

function App() {
  return (
    <div className="App">
      <UserContext>
        <AppRoutes/>
      </UserContext>
    </div>
  );
}

export default App;
