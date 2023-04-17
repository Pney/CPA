import React from 'react'
import { Container, Content } from './styles'
import HomeIcon from '@mui/icons-material/Home';
import CloseIcon from '@mui/icons-material/Close';
import SmsIcon from '@mui/icons-material/Sms';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import LockIcon from '@mui/icons-material/Lock';
import EqualizerIcon from '@mui/icons-material/Equalizer';
import ClassIcon from '@mui/icons-material/Class';
import PieChartIcon from '@mui/icons-material/PieChart';
import cpa from '../../../assets/image/logoCpa.jpg';


import SidebarItem from '../SidebarItem'
import { width } from '@mui/system';

const Sidebar = () => {
    const isAdmin = true;

    return (
        <Container>

            <img src={cpa} width={300} height={100} />
           
            <Content>
                <SidebarItem Icon={HomeIcon} Text="Home" />
                <SidebarItem Icon={SmsIcon} Text="Perguntas" />
                {
                    isAdmin && (
                        <div>
                            <SidebarItem Icon={ThumbUpIcon} Text="Respostas" />
                            <SidebarItem Icon={LockIcon} Text="Cadastros" />
                            <SidebarItem Icon={EqualizerIcon} Text="Gráficos" />
                            <SidebarItem Icon={ClassIcon} Text="Relatórios" />
                            <SidebarItem Icon={PieChartIcon} Text="Avaliações" />
                        </div>
                    )
                }

            </Content>
        </Container>
    )
}

export default Sidebar