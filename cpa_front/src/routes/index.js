
import AuthLayout from 'layouts/AuthLayout.jsx';
import React, { lazy, Suspense } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate, Outlet } from 'react-router-dom';
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.min.css';
//Routes
const Login = lazy(() => import('../pages/login.jsx'));
const Home = lazy(() => import('../pages/home.jsx'));
const Perguntas = lazy(() => import('../pages/perguntas.jsx'));
const Respostas = lazy(() => import('../pages/respostas.jsx'));
const Graficos = lazy(() => import('../pages/graficos.jsx'));
const Relatorios = lazy(() => import('../pages/relatorios.jsx'));
const Avaliacoes = lazy(() => import('../pages/avaliacoes.jsx'));
const Cadastro = lazy(() => import('../pages/cadastro.jsx'));
const Importar = lazy(() => import('../pages/importar.jsx'));
const PageNotFound = lazy(() => import('../pages/errors/PageNotFound.jsx'));
// const Componente = lazy(() => import('./pages/Auth/Login'))

export default function AppRoutes() {
  const PrivateRoute = () => {
    const auth = localStorage.getItem('token') ? true : false
    return (
        auth ? (
          <AuthLayout>
            <Outlet/>
          </AuthLayout>
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
      <>
        <ToastContainer 
          position="top-center"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
        <Suspense fallback={<div> Loading... </div>}>
          <Routes>
            <Route path='*' element={<PageNotFound/>} />
            <Route element={<PublicRoute/>}>
              <Route path='/login' element={<Login/>} />
            </Route>
            <Route element={<PrivateRoute/>}>
              <Route path='/home' element={<Home/>} />
              <Route path='/perguntas' element={<Perguntas/>} />
              <Route path='/importações' element={<Importar/>} />
              <Route path='/respostas' element={<Respostas/>} />
              <Route path='/graficos' element={<Graficos/>} />
              <Route path='/relatorios' element={<Relatorios/>} />
              <Route path='/avaliacoes' element={<Avaliacoes/>} />
              <Route path='/cadastros' element={<Cadastro/>} />
            </Route>
          </Routes>
        </Suspense>
      </>
    </Router>
  );
}
