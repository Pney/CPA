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
      <Typography variant="h1" style={{ color: 'white' }}>
        404
      </Typography>
      <Typography variant="h6" style={{ color: 'white' }}>
        A página que você está procurando não existe
      </Typography>
      <Button href='/home' variant="contained">Voltar para Home</Button>
    </Box>
  );
}