
import React, { lazy, Suspense } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate, Outlet } from 'react-router-dom';
//Routes
const Login = lazy(() => import('../pages/login.jsx'));
const Home = lazy(() => import('../pages/home.jsx'));
const Import = lazy(() => import('../pages/importar.jsx'))
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
        <Navigate to='/home' replace={true}/>
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
            <Route path='/home' element={<Home/>} />
            <Route path='/importar' element={<Import/>} />
          </Route>
        </Routes>
      </Suspense>
    </Router>
  );
}
