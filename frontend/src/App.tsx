import { Outlet } from 'react-router-dom';
import NavBar from "./components/NavBar.tsx";

function App() {
  return (
    <>
      <NavBar />
      <Outlet />
    </>
  );
}

export default App;
