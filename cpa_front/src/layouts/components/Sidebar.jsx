import React from 'react'
import HomeIcon from '@mui/icons-material/Home';
import LogoutIcon from '@mui/icons-material/Logout';
import SmsIcon from '@mui/icons-material/Sms';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import LockIcon from '@mui/icons-material/Lock';
import EqualizerIcon from '@mui/icons-material/Equalizer';
import ClassIcon from '@mui/icons-material/Class';
import PieChartIcon from '@mui/icons-material/PieChart';
import logoCpa from '../../assets/image/logoCpa.jpg';
import SidebarItem from '../../components/NavSide/SidebarItem/index'
import styled from 'styled-components';
import Image from '../../components/Image.jsx'
import authService from 'services/AuthService';
import { Button } from '@mui/material';
import { useUserContext } from 'contexts/UserContext';

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

export default function Sidebar() {
  
  const { role } = useUserContext();
  console.log({role})

  return (
    <Container>
      <Image
        url={logoCpa}
        alt={'Logo da CPA'}
        width={'230px'}
        height={'100px'}
        style={{ borderRadius: '1.50rem', marginTop: '20px' }}
      />
      <Content> 
        <SidebarItem Icon={HomeIcon} Text="Home" link={'/home'} />
        <SidebarItem Icon={PieChartIcon} Text="Avaliações" link={'/avaliacoes'}/>
        {
          role === 'CPA' && (
            <>
              <SidebarItem Icon={SmsIcon} Text="Perguntas" link={'/perguntas'}/>
              <SidebarItem Icon={ThumbUpIcon} Text="Respostas" link={'/respostas'}/>
              <SidebarItem Icon={LockIcon} Text="Cadastros" link={'/cadastros'}/>
              <SidebarItem Icon={EqualizerIcon} Text="Gráficos" link={'/graficos'}/>
              <SidebarItem Icon={ClassIcon} Text="Relatórios" link={'/relatorios'}/>
            </>
          )
        }
      </Content>
      <Button 
        variant="outlined"
        startIcon={<LogoutIcon/>}
        sx={{
          'bottom': '0',
          'left': '0',
          'position': 'absolute',
          'marginBottom': '2rem',
          'marginLeft': '30%',
        }}
        onClick={(e) => authService.logout()}
      >
        Logout
      </Button>
    </Container>     
  );
}