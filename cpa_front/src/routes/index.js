import React, { lazy, Suspense, useState, useContext } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate, redirect } from 'react-router-dom';
import { UserContext, useLoadContext } from '../contexts/UserContext.jsx';

//Routes
const Login = lazy(() => import('../pages/login.jsx'));
const Menu = lazy(() => import('../pages/menu.jsx'));
// const Componente = lazy(() => import('./pages/Auth/Login'))

export default function AppRoutes() {
  const { isAuthenticated, setIsAuthenticated } = useLoadContext();

  const PrivateRoute = ({ path, element }) => {
    // const auth = localStorage.getItem('token') ? true : false
    const Element = () => element;
    const auth = true;
    console.log({auth})
    // console.log(element)
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
    // const auth = localStorage.getItem('token') ? true : false
    const Element = () => element;
    const auth = true;
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
        {/* </Routes> */}
      </Suspense>
    </Router>
  );
}
