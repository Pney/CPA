import React from 'react'
import { Container } from './styles'
import { Link } from 'react-router-dom'

const SidebarItem = ({ Icon, Text, link }) => {
    return (
        <Link 
            to={link} 
            style={{textDecoration: 'none'}}
        >
            <Container>
                <Icon />
                {Text}
            </Container>
        </Link>
    )
}

export default SidebarItem