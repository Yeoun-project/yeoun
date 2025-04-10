import { useId } from 'react';
import { CircleLayerProps } from './CircleProps';

const CircleTopLayer = ({ size = 150, animate = false, colors }: CircleLayerProps) => {
  const uniqueId = useId();

  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 163 160"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`absolute z-4 ${animate && 'animate-spin-third'}`}
    >
      <g id="Vector 15" opacity="0.4" filter="url(#filter0_dddddd_98_1052)">
        <path
          d="M143.798 57.5668C158.613 96.7687 137.103 118.995 119.078 126.542C100.23 146.434 39.1879 156.138 29.5581 129.211C-8.87868 64.1166 35.6487 31.2866 50.546 20.1277C65.4434 8.96871 125.28 8.5644 143.798 57.5668Z"
          fill="white"
        />
        <path
          d="M143.798 57.5668C158.613 96.7687 137.103 118.995 119.078 126.542C100.23 146.434 39.1879 156.138 29.5581 129.211C-8.87868 64.1166 35.6487 31.2866 50.546 20.1277C65.4434 8.96871 125.28 8.5644 143.798 57.5668Z"
          fill={`url(#paint0_radial_98_1052_${uniqueId})`}
        />
        <path
          d="M143.798 57.5668C158.613 96.7687 137.103 118.995 119.078 126.542C100.23 146.434 39.1879 156.138 29.5581 129.211C-8.87868 64.1166 35.6487 31.2866 50.546 20.1277C65.4434 8.96871 125.28 8.5644 143.798 57.5668Z"
          fill={`url(#paint1_radial_98_1052_${uniqueId})`}
        />
        <path
          d="M143.798 57.5668C158.613 96.7687 137.103 118.995 119.078 126.542C100.23 146.434 39.1879 156.138 29.5581 129.211C-8.87868 64.1166 35.6487 31.2866 50.546 20.1277C65.4434 8.96871 125.28 8.5644 143.798 57.5668Z"
          fill={`url(#paint2_radial_98_1052_${uniqueId})`}
        />
      </g>
      <defs>
        <filter
          id="filter0_dddddd_98_1052"
          x="0.863223"
          y="0.542178"
          width="161.318"
          height="158.631"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feFlood floodOpacity="0" result="BackgroundImageFix" />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha1"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="3.84" in="hardAlpha1" result="blur1" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0"
            in="blur1"
            result="colorBlur1"
          />
          <feBlend
            mode="normal"
            in="colorBlur1"
            in2="BackgroundImageFix"
            result="effect1_simplified2"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha2"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="1" in="hardAlpha2" result="blur2" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0"
            in="blur2"
            result="colorBlur2"
          />
          <feBlend
            mode="normal"
            in="colorBlur2"
            in2="effect1_simplified2"
            result="effect2_simplified2"
          />
          <feBlend mode="normal" in="SourceGraphic" in2="effect2_simplified2" result="shape" />
        </filter>
        <radialGradient
          id={`paint0_radial_98_1052_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(147.781 79.5975) rotate(152.748) scale(120.287 232.319)"
        >
          <stop stopColor={colors.right} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`paint1_radial_98_1052_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(30.1098 60.7196) rotate(26.9131) scale(165.447 212.959)"
        >
          <stop stopColor={colors.topLeft} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`paint2_radial_98_1052_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(31.8661 143.997) rotate(-19.2714) scale(169.31 233.495)"
        >
          <stop stopColor={colors.bottomLeft} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
      </defs>
    </svg>
  );
};

export default CircleTopLayer;
