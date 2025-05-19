import { useId } from 'react';
import { CircleLayerProps } from './CircleProps';

const CircleTopLayer = ({ size = 150, animate = false, colors }: CircleLayerProps) => {
  const uniqueId = useId();

  return (
    <svg
      width={size}
      height={size}
      viewBox="1 -2 150 162"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`absolute z-4 ${animate && 'animate-spin-circle-layer'}`}
    >
      <g id="Vector 15" opacity="0.6" filter="url(#filter0_dddddd_98_1052)">
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
          <feGaussianBlur stdDeviation="2" />
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
          <feGaussianBlur stdDeviation="3" />
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
          <feGaussianBlur stdDeviation="4" />
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
