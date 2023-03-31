import { createContext, useState, useContext, useEffect } from 'react';

const Context = createContext();

export const UserContext = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  return (
    <Context.Provider
      value={{
        user, setUser,
        isAuthenticated, setIsAuthenticated,
      }}
    >
      {children}
    </Context.Provider>
  )
}


export const useLoadContext = () => {
  const context = useContext(Context);
  console.log(Context)
  return context || { isAuthenticated: false };
}
