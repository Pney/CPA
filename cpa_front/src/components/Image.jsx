import React from 'react';
import { Paper } from '@mui/material';
import logoCpa from '../assets/image/logoCpa.jpg'

export default function Image({ url, alt, width, height, border }) {
  return (
    <>
      <img
        src={url}
        alt={alt}
        width={width}
        height={height}
        style={border ? { borderRadius: `${border}` } : { borderRadius: `${border}` }}
      />
    </>
  )
} 