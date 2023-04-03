
import React, { lazy, Suspense } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate, Outlet } from 'react-router-dom';
//Routes
const Login = lazy(() => import('../pages/login.jsx'));
const Menu = lazy(() => import('../pages/menu.jsx'));
const PageNotFound = lazy(() => import('../pages/errors/PageNotFound.jsx'))
// const Componente = lazy(() => import('./pages/Auth/Login'))

export default function AppRoutes() {
  const PrivateRoute = () => {
    const auth = localStorage.getItem('token') ? true : false
    return (
        auth ? (
          <Outlet/>
        ) : (
          <Navigate to='/login'/>
        )
    );
  };
  
  const PublicRoute = () => {
    const auth = localStorage.getItem('token') ? true : false
    return (
      !auth ? (
        <Outlet/>
      ) : (
        <Navigate to='/menu' replace={true}/>
      )
    );
  };

  return (
    <Router>
      <Suspense fallback={<div> Loading... </div>}>
        <Routes>
          <Route path='*' element={<PageNotFound/>} />
          <Route element={<PublicRoute/>}>
            <Route path='/login' element={<Login/>} />
          </Route>
          <Route element={<PrivateRoute/>}>
            <Route path='/menu' element={<Menu/>} />
          </Route>
        </Routes>
      </Suspense>
    </Router>
  );
}
