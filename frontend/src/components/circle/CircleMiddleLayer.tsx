import { useId } from 'react';
import { CircleLayerProps } from './CircleProps';

const CircleMiddleLayer = ({ size = 150, animate = false, colors }: CircleLayerProps) => {
  const uniqueId = useId();

  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="-1 2 180 165"
      width={size}
      height={size}
      className={`absolute z-3 ${animate && 'animate-spin-circle-layer'}`}
    >
      <g filter="url(#filter0_dddddd_98_1051)" opacity=".4">
        <path
          fill="#fff"
          d="M46 130c-59-58-24-88 0-97 61-30 88-28 104 27 17 56 0 74-21 84-20 9-47 17-83-14Z"
        />
        <path
          fill={`url(#b_${uniqueId})`}
          d="M46 130c-59-58-24-88 0-97 61-30 88-28 104 27 17 56 0 74-21 84-20 9-47 17-83-14Z"
        />
        <path
          fill={`url(#c_${uniqueId})`}
          d="M46 130c-59-58-24-88 0-97 61-30 88-28 104 27 17 56 0 74-21 84-20 9-47 17-83-14Z"
        />
        <path
          fill={`url(#d_${uniqueId})`}
          d="M46 130c-59-58-24-88 0-97 61-30 88-28 104 27 17 56 0 74-21 84-20 9-47 17-83-14Z"
        />
      </g>
      <defs>
        <radialGradient
          id={`b_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="rotate(153 68 60) scale(128 244)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.right} />
          <stop offset=".7" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`c_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="matrix(157 79 -100 201 31 63)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.topLeft} />
          <stop offset=".7" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`d_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="matrix(170 -59 80 232 33 150)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.bottomLeft} />
          <stop offset=".7" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <filter
          id="filter0_dddddd_98_1051"
          x="0.402652"
          y="0.111758"
          width="169.974"
          height="165.296"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feBlend
            mode="normal"
            in2="effect1_dropShadow_98_1050"
            result="effect2_dropShadow_98_1050"
          />

          <feOffset />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />

          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="1" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect3_dropShadow_98_1050"
            result="effect4_dropShadow_98_1050"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="1" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect4_dropShadow_98_1050"
            result="effect5_dropShadow_98_1050"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="3" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect5_dropShadow_98_1050"
            result="effect6_dropShadow_98_1050"
          />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="effect6_dropShadow_98_1050"
            result="shape"
          />
        </filter>
      </defs>
    </svg>
  );
};

export default CircleMiddleLayer;
