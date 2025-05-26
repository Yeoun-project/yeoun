import { Outlet } from 'react-router-dom';

import Provider from './components/provider/index.tsx';
import OnboardingPage from './pages/OnboardingPage.tsx';
import BackgroundBanner from './components/backgroundBanner/BackgroundBanner.tsx';

function App() {
  const hasComplete = localStorage.getItem('onboarding') === 'complete';
  return (
    <>
      <Provider>
        <div className="relative">
          <BackgroundBanner />
          <div className="relative mx-auto min-h-svh w-full max-w-[430px] overflow-x-hidden bg-[#1a1a1a] text-[#ffffff] xl:ml-[55%]">
            {hasComplete ? <Outlet /> : <OnboardingPage />}
          </div>
        </div>
      </Provider>
    </>
  );
}

export default App;
