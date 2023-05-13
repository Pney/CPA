import React from 'react';
import { Box, Button, Typography } from '@mui/material';

export default function PageNotFound() {
  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'column',
        minHeight: '100vh',
      }}
    >
      <Typography variant="h1">
        404 :(
      </Typography>
      <Typography variant="h6">
        Oops! Está página não pode ser encontrada!
      </Typography>
      <Button href='/home' color={'secondary'} variant="contained" st>Voltar para Página Inicial</Button>
    </Box>
  );
}