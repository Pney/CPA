
import React, { lazy, Suspense } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate, Outlet } from 'react-router-dom';
//Routes
const Login = lazy(() => import('../pages/login.jsx'));
const Menu = lazy(() => import('../pages/menu.jsx'));
// const Componente = lazy(() => import('./pages/Auth/Login'))

export default function AppRoutes() {
  const PrivateRoute = ({}) => {
    const auth = localStorage.getItem('token') ? true : false
    return (
        auth ? (
          <Outlet/>
        ) : (
          <Navigate to='/login'/>
        )
    );
  };
  
  const PublicRoute = ({ path, element }) => {
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
