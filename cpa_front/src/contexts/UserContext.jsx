import { createContext, useState, useContext, useEffect} from 'react';
import jwtDecode from 'jwt-decode';
export const Context = createContext();

export const UserContext  = ({ children }) => {
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const nivel = jwtDecode(token);
      setRole(nivel.userRole[0].authority);
    } else {
      setRole(null)
    }
  }, []);


  return (
    <Context.Provider
      value={{
        user, setUser,
        role,
        isAuthenticated, setIsAuthenticated
      }}
    >
      {children}
    </Context.Provider>
  )
}


export const useUserContext = () => {
  const context = useContext(Context);
  return context;
};

export default Context;
