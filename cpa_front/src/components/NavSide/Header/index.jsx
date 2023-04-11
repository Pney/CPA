import React, { useState } from 'react'
import { Container } from './styles'
import MenuIcon from '@mui/icons-material/Menu';
import Sidebar from '../Sidebar'
import cpa from '../../../assets/image/logoCpa.jpg';
const Header = () => {
    const [sidebar, setSidebar] = useState(false)

    const showSiderbar = () => setSidebar(!sidebar)

    return (
        <Container>
            <img src={cpa} width={300} height={100} />
            <MenuIcon onClick={showSiderbar} />
            {sidebar && <Sidebar active={setSidebar} />}
        </Container>

    )
}

export default Header