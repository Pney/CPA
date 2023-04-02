import React, { lazy, Suspense } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

//Routes
const Login = lazy(() => import('../pages/login.jsx'))
const Home = lazy(() => import('../pages/home.jsx'))
// const Componente = lazy(() => import('./pages/Auth/Login'))

export default function AppRoutes() {
  return (
    <Router>
      <Suspense fallback={<div> Loading... </div>} >
        <Routes>
          <Route path={'/login'} element={<Login />} />
          <Route path={'/home'} element={<Home />} />
          {/* <Route path={'/outraRota'} element={<componente/>} /> */}
        </Routes>
      </Suspense>
    </Router>
  );
}

