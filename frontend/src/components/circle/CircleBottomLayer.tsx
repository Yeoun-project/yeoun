import { useId } from 'react';
import { CircleLayerProps } from './CircleProps';

const CircleBottomLayer = ({ size = 150, animate = false, colors }: CircleLayerProps) => {
  const uniqueId = useId();
  return (
    <svg
      width={size}
      height={size}
      viewBox="-3 6 168 155"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`absolute z-2 ${animate && 'animate-spin-circle-layer will-change-transform'}`}
    >
      <g id="Vector 17" opacity="0.3" filter="url(#filter0_dddddd_98_1050)">
        <path
          d="M30.1717 120.376C44.1839 179.344 99.669 155.721 123.702 137.922C175.606 89.9915 154.235 75.3853 158.861 62.2031C163.585 48.7433 121.184 -1.4241 64.6188 17.7454C8.05371 36.9149 1.5706 76.3735 30.1717 120.376Z"
          fill="white"
        />
        <path
          d="M30.1717 120.376C44.1839 179.344 99.669 155.721 123.702 137.922C175.606 89.9915 154.235 75.3853 158.861 62.2031C163.585 48.7433 121.184 -1.4241 64.6188 17.7454C8.05371 36.9149 1.5706 76.3735 30.1717 120.376Z"
          fill={`url(#paint0_radial_98_1050_${uniqueId})`}
        />
        <path
          d="M30.1717 120.376C44.1839 179.344 99.669 155.721 123.702 137.922C175.606 89.9915 154.235 75.3853 158.861 62.2031C163.585 48.7433 121.184 -1.4241 64.6188 17.7454C8.05371 36.9149 1.5706 76.3735 30.1717 120.376Z"
          fill={`url(#paint1_radial_98_1050_${uniqueId})`}
        />
        <path
          d="M30.1717 120.376C44.1839 179.344 99.669 155.721 123.702 137.922C175.606 89.9915 154.235 75.3853 158.861 62.2031C163.585 48.7433 121.184 -1.4241 64.6188 17.7454C8.05371 36.9149 1.5706 76.3735 30.1717 120.376Z"
          fill={`url(#paint2_radial_98_1050_${uniqueId})`}
        />
      </g>
      <defs>
        <filter
          id="filter0_dddddd_98_1050"
          x="0.146914"
          y="-0.00664997"
          width="172.733"
          height="171.428"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feGaussianBlur stdDeviation="2" />
          <feBlend
            mode="normal"
            in2="effect2_dropShadow_98_1050"
            result="effect3_dropShadow_98_1050"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />

          <feGaussianBlur stdDeviation="2" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect4_dropShadow_98_1050"
            result="effect5_dropShadow_98_1050"
          />

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
        <radialGradient
          id={`paint0_radial_98_1050_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(119.36 19.8725) rotate(93.8716) scale(132.91 254.199)"
        >
          <stop stopColor={colors.right} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`paint1_radial_98_1050_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(34.8531 123.459) rotate(-33.6638) scale(185.076 230.161)"
        >
          <stop stopColor={colors.bottomLeft} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`paint2_radial_98_1050_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(114.04 166.114) rotate(-78.267) scale(188.036 254.182)"
        >
          <stop stopColor={colors.topLeft} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
      </defs>
    </svg>
  );
};

export default CircleBottomLayer;
