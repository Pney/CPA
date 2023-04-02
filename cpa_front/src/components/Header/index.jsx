import React, { useState } from 'react'
import { Container } from './styles'
import MenuIcon from '@mui/icons-material/Menu';
import Sidebar from '../Sidebar'

const Header = () => {
    const [sidebar, setSidebar] = useState(false)

    const showSiderbar = () => setSidebar(!sidebar)

    return (
        <Container>
            <MenuIcon onClick={showSiderbar} />
            {sidebar && <Sidebar active={setSidebar} />}
        </Container>
    )
}

export default Header