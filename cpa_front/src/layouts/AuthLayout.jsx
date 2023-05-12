import React from 'react'
import Sidebar from './components/Sidebar';

export default function AuthLayout({children}) {

  return (
    <React.Fragment>
      <nav className='sidebar'>
        <Sidebar/>
      </nav>
      <main className='content'>
        {children}
      </main>
    </React.Fragment>   
  );
}