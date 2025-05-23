import { useId } from 'react';
import { CircleLayerProps } from './CircleProps';

const CircleBottomLayer = ({ size = 150, animate = false, colors }: CircleLayerProps) => {
  const uniqueId = useId();
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      width={size}
      height={size}
      viewBox="-3 6 168 155"
      className={`absolute z-2 ${animate && 'animate-spin-circle-layer'}`}
    >
      <g filter="url(#filter0_dddddd_98_1050)" opacity=".6">
        <path
          fill="#fff"
          d="M30 120c14 59 70 36 94 18 52-48 30-63 35-76S121-1 65 18C8 37 2 76 30 120Z"
        />
        <path
          fill={`url(#b_${uniqueId})`}
          d="M30 120c14 59 70 36 94 18 52-48 30-63 35-76S121-1 65 18C8 37 2 76 30 120Z"
        />
        <path
          fill={`url(#c_${uniqueId})`}
          d="M30 120c14 59 70 36 94 18 52-48 30-63 35-76S121-1 65 18C8 37 2 76 30 120Z"
        />
        <path
          fill={`url(#d_${uniqueId})`}
          d="M30 120c14 59 70 36 94 18 52-48 30-63 35-76S121-1 65 18C8 37 2 76 30 120Z"
        />
      </g>
      <defs>
        <radialGradient
          id={`b_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="matrix(-9 133 -254 -17 119 20)"
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
          gradientTransform="matrix(154 -103 128 192 35 123)"
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
          gradientTransform="matrix(38 -184 249 52 114 166)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.bottomLeft} />
          <stop offset=".7" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <filter
          id="filter0_dddddd_98_1050"
          x="0.146914"
          y="-0.00664997"
          width="172.733"
          height="171.428"
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
          <feGaussianBlur stdDeviation="2" />
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
          <feGaussianBlur stdDeviation="2" />
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

export default CircleBottomLayer;
