
import React, { lazy, Suspense, useContext } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { UserContext, useLoadContext } from '../contexts/UserContext.jsx';

//Routes
const Login = lazy(() => import('../pages/login.jsx'));
const Menu = lazy(() => import('../pages/menu.jsx'));
// const Componente = lazy(() => import('./pages/Auth/Login'))

export default function AppRoutes() {
  // const { isAuthenticated, setIsAuthenticated } = useContext(UserContext);

  const PrivateRoute = ({ path, element }) => {
    const auth = localStorage.getItem('token') ? true : false
    const Element = () => element;
    console.log({auth})
    console.log({element})
    console.log({Element})
    console.log({path})
    return (
      <Routes>
        {auth ? (
          <Route path={path} element={Element ? <Element /> : null} />
        ) : (
          <Navigate to='/login' replace={true}/>
        )}
      </Routes>
    );
  };
  
  const PublicRoute = ({ path, element }) => {
    const auth = localStorage.getItem('token') ? true : false
    const Element = () => element;
    console.log({auth})
    // console.log(element)
    console.log({element})
    console.log({Element})
    console.log({path})
    return (
      <Routes>
        {!auth ? (
          <Route path={path} element={Element ? <Element /> : null} />
        ) : (
          <Route path='/login' element={<Navigate to='/login' replace={true}/>}/>
        )}
      </Routes>
    );
  };

  return (
    <Router>
      <Suspense fallback={<div> Loading... </div>}>
        {/* <Routes> */}
        <PublicRoute path='/login' element={<Login />} />
        <PrivateRoute path='/' element={<Menu/>} />
      </Suspense>
    </Router>
  );
}
