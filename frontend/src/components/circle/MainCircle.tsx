import { useId } from 'react';
import { MainCircleProps } from './CircleProps';

const MainCircle = ({ size = 150, colors }: MainCircleProps) => {
  const uniqueId = useId();

  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width={size}
      height={size}
      className="absolute z-5"
      fill="none"
      viewBox="0 0 157 157"
    >
      <g filter="url(#b)" opacity="1">
        <path
          fill="#fff"
          d="M143 78.5c0 35.622-28.878 64.5-64.5 64.5C42.8776 143 14 114.122 14 78.5 14 42.8776 42.8776 14 78.5 14c35.622 0 64.5 28.8776 64.5 64.5Z"
        />
        <path
          fill={`url(#c_${uniqueId})`}
          d="M143 78.5c0 35.622-28.878 64.5-64.5 64.5C42.8776 143 14 114.122 14 78.5 14 42.8776 42.8776 14 78.5 14c35.622 0 64.5 28.8776 64.5 64.5Z"
        />
        <path
          fill={`url(#d_${uniqueId})`}
          d="M143 78.5c0 35.622-28.878 64.5-64.5 64.5C42.8776 143 14 114.122 14 78.5 14 42.8776 42.8776 14 78.5 14c35.622 0 64.5 28.8776 64.5 64.5Z"
        />
        <path
          fill={`url(#e_${uniqueId})`}
          d="M143 78.5c0 35.622-28.878 64.5-64.5 64.5C42.8776 143 14 114.122 14 78.5 14 42.8776 42.8776 14 78.5 14c35.622 0 64.5 28.8776 64.5 64.5Z"
        />
      </g>
      <defs>
        <radialGradient
          id={`c_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="matrix(-103 54 -105 -200 142.079 78.2454)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.right} />
          <stop offset=".796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`d_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="matrix(142 73 -95 184 29.1672 59.7617)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.topLeft} />
          <stop offset=".796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`e_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="matrix(153 -55 77 215 30.8525 141.3)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.bottomLeft} />
          <stop offset=".796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <clipPath id="a" transform="translate(6 6)">
          <path d="M143 78.5c0 35.622-28.878 64.5-64.5 64.5C42.8776 143 14 114.122 14 78.5 14 42.8776 42.8776 14 78.5 14c35.622 0 64.5 28.8776 64.5 64.5Z" />
        </clipPath>
        <filter
          id="b"
          width="169"
          height="169"
          x="-6"
          y="-6"
          colorInterpolationFilters="sRGB"
          filterUnits="userSpaceOnUse"
        >
          <feFlood floodOpacity="0" result="BackgroundImageFix" />
          <feColorMatrix
            in="SourceAlpha"
            result="hardAlpha"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
          />
          <feOffset />

          <feColorMatrix values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feColorMatrix
            in="SourceAlpha"
            result="hardAlpha"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
          />
          <feOffset />
          <feColorMatrix values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feColorMatrix
            in="SourceAlpha"
            result="hardAlpha"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="1" />
          <feColorMatrix values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend in2="effect2_dropShadow_188_1110" result="effect3_dropShadow_188_1110" />

          <feOffset />
          <feColorMatrix values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend in2="effect3_dropShadow_188_1110" result="effect4_dropShadow_188_1110" />
          <feColorMatrix
            in="SourceAlpha"
            result="hardAlpha"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="3" />
          <feColorMatrix values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend in="effect4_dropShadow_188_1110" result="effect5_dropShadow_188_1110" />
          <feColorMatrix
            in="SourceAlpha"
            result="hardAlpha"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="3" />
          <feColorMatrix values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend in2="effect5_dropShadow_188_1110" result="effect6_dropShadow_188_1110" />
          <feBlend in="SourceGraphic" in2="effect6_dropShadow_188_1110" result="shape" />
        </filter>
      </defs>
    </svg>
  );
};

export default MainCircle;
