import BackArrowButton from '../components/button/BackArrowButton';
import BottomTabBar from '../components/nav/BottomTabBar';
import { getAlarmList, postTestAlarm } from '../services/api/alarm/alarmList';

const AlarmPage = () => {
  // const alarmList = getAlarmList(); 
  // const test = postTestAlarm();
  // console.log(test)
  // console.log(alarmList);

  return (
    <>
      <main className="flex min-h-[100svh] flex-col gap-4">
        {/* Header */}
        <header className="relative flex justify-center p-6">
          <div className="absolute top-6 left-6">
            <BackArrowButton />
          </div>
          <h3 className="w-full text-center">알람</h3>
        </header>
        <ul className="font-desc w-full"></ul>
      </main>
      <BottomTabBar />
    </>
  );
};

export default AlarmPage;
