import React from 'react'
import Sidebar from './components/Sidebar';

export default function AuthLayout({children}) {

  return (
    <React.Fragment>
      <Sidebar/>
      {children}
    </React.Fragment>   
  );
}