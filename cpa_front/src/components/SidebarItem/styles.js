import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  align-items: center;
  background-color: #13212D; 
  font-size: 20px;
  color: white;
  padding: 10px;
  cursor: pointer;
  border-radius: 10px;
  margin: 0 15px 20px;
  > svg {
    margin: 0 30px;
  }
  &:hover {
    background-color: #273C4E;
    color: #43BC98;
    
  }
`;