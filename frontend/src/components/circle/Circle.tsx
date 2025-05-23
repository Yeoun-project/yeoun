import MainCircle from './MainCircle';
import CircleTopLayer from './CircleTopLayer';
import CircleMiddleLayer from './CircleMiddleLayer';
import CircleBottomLayer from './CircleBottomLayer';

import QuestionCategory from '../../type/questionCategory';

import { CircleGradientColors, CircleProps } from './CircleProps';

const getGradientColors = (category: QuestionCategory): CircleGradientColors => {
  const gradientColors = {
    selfReflection: {
      bottomLeft: '#AAFFBA',
      right: '#DBFC90',
      topLeft: '#C5FFDF',
    },
    relationships: {
      bottomLeft: '#38B1FC',
      right: '#398DFA',
      topLeft: '#7DD8FF',
    },
    valuesAndBeliefs: {
      bottomLeft: '#AAD2FF',
      right: '#FC90D1',
      topLeft: '#F3C5FF',
    },
    dreamsAndGoals: {
      bottomLeft: '#AAEDFF',
      right: '#C090FC',
      topLeft: '#C8C5FF',
    },
    memories: {
      bottomLeft: '#FFCCA7',
      right: '#FFDDA6',
      topLeft: '#FFD798',
    },
    challengesAndCourage: {
      bottomLeft: '#F9FFAA',
      right: '#FCE390',
      topLeft: '#FFE7C5',
    },
    mindAndEmotions: {
      bottomLeft: '#FFC3AA',
      right: '#FC9092',
      topLeft: '#FFC9C5 ',
    },
  };
  return gradientColors[category];
};

const Circle = ({ size = 150, category = 'valuesAndBeliefs', children, animate }: CircleProps) => {
  const gradientColors = getGradientColors(category);
  return (
    <div
      style={{ width: `${size}px`, height: `${size}px` }}
      className={`relative flex items-center justify-center`}
    >
      {children && (
        <div className="z-6 flex h-auto w-full items-center justify-center text-center">
          {children}
        </div>
      )}
      <MainCircle size={size} colors={gradientColors} />
      <CircleTopLayer size={size + size / 18} colors={gradientColors} animate={animate} />
      <CircleMiddleLayer size={size + size / 13} colors={gradientColors} animate={animate} />
      <CircleBottomLayer size={size + size / 18} colors={gradientColors} animate={animate} />
    </div>
  );
};

export default Circle;
