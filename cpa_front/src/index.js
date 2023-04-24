import React from 'react';
import ReactDOM from 'react-dom/client';
import 'assets/css/index.css';
import App from './App.js';
import reportWebVitals from './reportWebVitals.js';
import {ThemeProvider, createTheme } from '@mui/material'
import { palette } from 'palette.js';

const root = ReactDOM.createRoot(document.getElementById('root'));

const theme = createTheme({palette});

root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <App/>
    </ThemeProvider>
  </React.StrictMode>
);

reportWebVitals();
