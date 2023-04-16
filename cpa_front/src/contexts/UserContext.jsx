import { createContext, useState, useContext} from 'react';
const Context = createContext();

export const UserContext  = ({ children }) => {
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  return (
    <Context.Provider
      value={{
        user, setUser,
        role, setRole,
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
