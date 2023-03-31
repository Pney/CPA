import React, { lazy, Suspense, useState, useContext } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { UserContext, useLoadContext } from '../contexts/UserContext.jsx';

//Routes
const Login = lazy(() => import('../pages/login.jsx'));
const Menu = lazy(() => import('../pages/menu.jsx'));
// const Componente = lazy(() => import('./pages/Auth/Login'))

export default function AppRoutes() {
  const { isAuthenticated, setIsAuthenticated } = useLoadContext();

  const PrivateRoute = ({ element: Element, ...rest }) => {
    const auth = localStorage.getItem('token') ? true : false
    console.log({auth})
    console.log( localStorage.getItem('token'))
    return (
      <Routes>
        auth ? (
          <Route {...rest} element={Element ? <Element /> : null} />
        ) : (
          <Navigate to='/login' replace />
        )
      </Routes>
    );
  };

  return (
    <Router>
      <Suspense fallback={<div> Loading... </div>}>
        <Routes>
          <Route path='/login' element={<Login />} />
          <Route path='/' element={<Menu />} />
        </Routes>
      </Suspense>
    </Router>
  );
}
