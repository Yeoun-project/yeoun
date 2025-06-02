import { useId } from 'react';
import { CircleLayerProps } from './CircleProps';

const CircleTopLayer = ({ size = 150, animate = false, colors }: CircleLayerProps) => {
  const uniqueId = useId();

  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width={size}
      height={size}
      fill="none"
      className={`absolute z-4 ${animate && 'animate-spin-circle-layer'}`}
      viewBox="1 -2 160 172"
    >
      <g filter="url(#filter0_dddddd_98_1052)" opacity=".6">
        <path
          fill="#fff"
          d="M144 58c15 39-7 61-25 69-19 19-80 29-89 2-39-65 6-98 21-109 14-11 74-11 93 38Z"
        />
        <path
          fill={`url(#b_${uniqueId})`}
          d="M144 58c15 39-7 61-25 69-19 19-80 29-89 2-39-65 6-98 21-109 14-11 74-11 93 38Z"
        />
        <path
          fill={`url(#c_${uniqueId})`}
          d="M144 58c15 39-7 61-25 69-19 19-80 29-89 2-39-65 6-98 21-109 14-11 74-11 93 38Z"
        />
        <path
          fill={`url(#d_${uniqueId})`}
          d="M144 58c15 39-7 61-25 69-19 19-80 29-89 2-39-65 6-98 21-109 14-11 74-11 93 38Z"
        />
      </g>
      <defs>
        <radialGradient
          id={`b_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="rotate(153 64 58) scale(120 233)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.right} />
          <stop offset="0.7" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`c_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="matrix(148 75 -96 190 30 61)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.topLeft} />
          <stop offset="0.7" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`d_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientTransform="matrix(160 -56 77 220 32 144)"
          gradientUnits="userSpaceOnUse"
        >
          <stop stopColor={colors.bottomLeft} />
          <stop offset="0.7" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <filter
          id="filter0_dddddd_98_1052"
          x="0.863223"
          y="0.542178"
          width="161.318"
          height="158.631"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect3_dropShadow_98_1052"
            result="effect4_dropShadow_98_1052"
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
            in2="effect4_dropShadow_98_1052"
            result="effect5_dropShadow_98_1052"
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
            in2="effect5_dropShadow_98_1052"
            result="effect6_dropShadow_98_1052"
          />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="effect6_dropShadow_98_1052"
            result="shape"
          />
        </filter>
      </defs>
    </svg>
  );
};

export default CircleTopLayer;
