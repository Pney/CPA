import React from 'react';

export default function Image({url, alt, width, height, ...props}) {
  return(
      <img
        src={url} 
        alt={alt}
        width={width}
        height={height}
        {...props}
      />
  )
} 