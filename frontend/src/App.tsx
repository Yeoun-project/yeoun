import { Outlet } from 'react-router-dom';

import Provider from './components/provider/index.tsx';

import NavBar from './components/NavBar.tsx';

function App() {
  return (
    <>
      <Provider>
        <NavBar />
        <Outlet />
      </Provider>
    </>
  );
}

export default App;
