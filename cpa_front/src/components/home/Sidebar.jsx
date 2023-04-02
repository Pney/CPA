import cpa from '../../assets/image/logoCpa.jpg'
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import HomeIcon from '@mui/icons-material/Home';
import QuestionMarkIcon from '@mui/icons-material/QuestionMark';
import SmsIcon from '@mui/icons-material/Sms';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import LockIcon from '@mui/icons-material/Lock';
import EqualizerIcon from '@mui/icons-material/Equalizer';
import ClassIcon from '@mui/icons-material/Class';
import PieChartIcon from '@mui/icons-material/PieChart';

function Sidebar() {
  return (
    <aside class="sidebar">
      <header class="sidebar-header">
        <img src={cpa} />
      </header>
      <nav>
        <button class={Button}>
          <span id='i'>
            <HomeIcon />
            <span>Home</span>
          </span>
        </button>
        <button class={Button}>
          <span id='i'>
            <SmsIcon />
            <span>Perguntas</span>
          </span>
        </button>
        <button class={Button}>
          <span id='i'>
            <ThumbUpIcon />
            <span>Respostas</span>
          </span>
        </button>
        <button class={Button}>
          <span id='i'>
            <LockIcon />
            <span>Cadastros</span>
          </span>
        </button>
        <button class={Button}>
          <span id='i'>
            <EqualizerIcon />
            <span>Gráficos</span>
          </span>
        </button>
        <button class={Button}>
          <span id='i'>
            <PieChartIcon />
            <span>Relatórios</span>
          </span>
        </button>
        <button class={Button}>
          <span id='i'>
            <ClassIcon />
            <span>Avaliações</span>
          </span>
        </button>
      </nav>
    </aside>

  )
}

export default Sidebar