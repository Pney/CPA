import { DataGrid } from '@mui/x-data-grid';

export default function TabelaCSV({rows, columns, ...props}) {
  return(
    <div style={{ 
      height: 450, width: '80%', margin: '0 auto', backgroundColor: '#000000', padding: '10px', borderRadius: '20px'
    }}>
      <DataGrid
        rows={rows}
        columns={columns}
        checkboxSelection
        sx={{margin: '0 auto'}}
        {...props}
      />
    </div>
  )
}