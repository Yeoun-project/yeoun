import { Outlet } from 'react-router-dom';

import Provider from './components/provider/index.tsx';
import OnboardingPage from './pages/OnboardingPage.tsx';

function App() {
  const hasComplete = localStorage.getItem('onboarding') === 'complete';
  return (
    <>
      <Provider>
        {/* <NavBar /> */}
        {hasComplete ? <Outlet /> : <OnboardingPage />}
      </Provider>
    </>
  );
}

export default App;
