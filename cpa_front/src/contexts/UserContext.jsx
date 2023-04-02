import { createContext, useState, useContext, useEffect } from 'react';
const UserContext = createContext();

export const UserProvider  = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  return (
    <UserContext.Provider
      value={{
        user, setUser,
        isAuthenticated, setIsAuthenticated
      }}
    >
      {children}
    </UserContext.Provider>
  )
}


export const useUserContext = () => {
  const context = useContext(UserContext);
  return context;
}
