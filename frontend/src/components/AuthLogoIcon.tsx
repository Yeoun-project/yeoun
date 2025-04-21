import GoogleIcon from '../assets/Icons/GoogleIcon';
import KaKaoIcon from '../assets/Icons/KaKaoIcon';
import NaverIcon from '../assets/Icons/NaverIcon';

interface AuthLogoIconProps {
  identifier: 'kakao' | 'naver' | 'google';
  size?: number;
  fill?: string;
}

const AuthLogoIcon = ({ identifier, size, fill }: AuthLogoIconProps) => {
  switch (identifier) {
    case 'kakao':
      return <KaKaoIcon size={size} />;
    case 'naver':
      return <NaverIcon size={size} fill={fill} />;
    case 'google':
      return <GoogleIcon size={size} />;
  }
};

export default AuthLogoIcon;
