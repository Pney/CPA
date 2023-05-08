import React from 'react'
import { Link } from 'react-router-dom'
import { Typography, useTheme, styled, Icon } from '@mui/material'

const Container = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    backgroundColor: '#13212D',
    fontSize: '20px',
    color: 'white',
    padding: '10px',
    cursor: 'pointer',
    bordeRadius: '10px',
    [theme.breakpoints.down("sm")]:{
        margin: '0 0px 20px',
        width: '15px'
    },
    '> svg': {
        margin: '0 30px',
        [theme.breakpoints.down("sm")]:{
            margin: '0 15px',
        },
    },
    '&:hover': { 
        borderRadius: '20px',
        backgroundColor: '#273C4E',
        color: '#43BC98',
    }
}));
const Text = styled(Typography)(({theme}) => ({
    fontWeight: 'bold',
    [theme.breakpoints.down("sm")]:{
        display: 'none',
    },
}));



const SidebarItem = ({ text, link, children, ...props }) => {
    return (
        <Link 
            to={link} 
            className='no-line'
            {...props}
        >
            <Container>
                {children}
                <Text>
                    {text}
                </Text>
            </Container>
        </Link>
    )
}

export default SidebarItem