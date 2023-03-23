import React from 'react';
import ReactDOM from 'react-dom/client';
import 'assets/css/index.css';
import App from './App.js';
import reportWebVitals from './reportWebVitals.js';
import {ThemeProvider, createTheme } from '@mui/material'

const root = ReactDOM.createRoot(document.getElementById('root'));

const theme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#273C4E',
    },
    white: {
      main: '#FFFFFF'
    },
    azureRadiance: '#008AFF',
    elephant: '#153149'
  }
})

root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <App/>
    </ThemeProvider>
  </React.StrictMode>
);

reportWebVitals();
