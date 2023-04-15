
import React from 'react'

import HomeIcon from '@mui/icons-material/Home';
import SmsIcon from '@mui/icons-material/Sms';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import LockIcon from '@mui/icons-material/Lock';
import EqualizerIcon from '@mui/icons-material/Equalizer';
import ClassIcon from '@mui/icons-material/Class';
import PieChartIcon from '@mui/icons-material/PieChart';
import cpa from '../assets/image/logoCpa.jpg';


import SidebarItem from '../components/NavSide/SidebarItem/index'

import styled from 'styled-components';

  const Container = styled.div`
  background-color: #13212D;
  position: fixed;
  height: 100%;
  top: 0px;
  left: 0px;
  width: 300px;
  left: ${props => props.sidebar ? '0' : '0%'};
  
  > svg {
    position: fixed;
    color: white;
    width: 30px;
    height: 30px;
    margin-top: 130px;
    margin-left: 32px;
    cursor: pointer;
  }
  
`;

const Content = styled.div`
  margin-top: 30px;
`;




export default  function Home() {

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
                            <SidebarItem Icon={ThumbUpIcon} Text="Respostas"  />
                            <SidebarItem Icon={LockIcon} Text="Cadastros" />
                            <SidebarItem Icon={EqualizerIcon} Text="Gráficos" />
                            <SidebarItem Icon={ClassIcon} Text="Relatórios" />
                            <SidebarItem Icon={PieChartIcon} Text="Avaliações" />
                        </div>
                    )
                }

            </Content>
        </Container>
        
        
                
                
    );
}