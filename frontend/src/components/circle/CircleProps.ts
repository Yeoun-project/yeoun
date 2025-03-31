import QuestionCategory from '../../type/questionCategory';

// 그라데이션 컬러
export interface CircleGradientColors {
  bottomLeft: string;
  right: string;
  topLeft: string;
}

// 공통 Props
export interface BasicCircleProps {
  size?: number;
}

// Circle Layer Props
export interface CircleLayerProps extends BasicCircleProps {
  animate?: boolean;
  colors: CircleGradientColors;
}

// Main Circle Props
export interface MainCircleProps extends BasicCircleProps {
  colors: CircleGradientColors;
}

// Circle Props
export interface CircleProps extends BasicCircleProps {
  category?: QuestionCategory;
  animate?: boolean;
  children?: React.ReactNode;
}
