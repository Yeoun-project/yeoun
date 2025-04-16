import { useId } from 'react';
import { CircleLayerProps } from './CircleProps';

const CircleMiddleLayer = ({ size = 150, animate = false, colors }: CircleLayerProps) => {
  const uniqueId = useId();

  return (
    <svg
      width={size}
      height={size}
      viewBox="3 2 170 160"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={`absolute z-3 ${animate && 'animate-spin-circle-layer will-change-transform'}`}
    >
      <g id="Vector 16" opacity="0.4" filter="url(#filter0_dddddd_98_1051)">
        <path
          d="M46.4178 129.868C-12.5789 72.3586 21.8358 41.5393 46.4178 33.3184C107.489 2.67166 134.238 4.77407 150.37 60.1646C166.502 115.555 150.37 134.315 129.464 143.776C108.559 153.237 82.3854 160.514 46.4178 129.868Z"
          fill="white"
        />
        <path
          d="M46.4178 129.868C-12.5789 72.3586 21.8358 41.5393 46.4178 33.3184C107.489 2.67166 134.238 4.77407 150.37 60.1646C166.502 115.555 150.37 134.315 129.464 143.776C108.559 153.237 82.3854 160.514 46.4178 129.868Z"
          fill={`url(#paint0_radial_98_1051_${uniqueId})`}
        />
        <path
          d="M46.4178 129.868C-12.5789 72.3586 21.8358 41.5393 46.4178 33.3184C107.489 2.67166 134.238 4.77407 150.37 60.1646C166.502 115.555 150.37 134.315 129.464 143.776C108.559 153.237 82.3854 160.514 46.4178 129.868Z"
          fill={`url(#paint1_radial_98_1051_${uniqueId})`}
        />
        <path
          d="M46.4178 129.868C-12.5789 72.3586 21.8358 41.5393 46.4178 33.3184C107.489 2.67166 134.238 4.77407 150.37 60.1646C166.502 115.555 150.37 134.315 129.464 143.776C108.559 153.237 82.3854 160.514 46.4178 129.868Z"
          fill={`url(#paint2_radial_98_1051_${uniqueId})`}
        />
      </g>
      <defs>
        <filter
          id="filter0_dddddd_98_1051"
          x="0.402652"
          y="0.111758"
          width="169.974"
          height="165.296"
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
          id={`paint0_radial_98_1051_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(155.915 82.4865) rotate(153.052) scale(127.686 244.733)"
        >
          <stop stopColor={colors.right} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`paint1_radial_98_1051_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(30.667 62.6536) rotate(26.6125) scale(175.635 224.325)"
        >
          <stop stopColor={colors.topLeft} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`paint2_radial_98_1051_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(32.5364 150.143) rotate(-19.0397) scale(179.958 245.652)"
        >
          <stop stopColor={colors.bottomLeft} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
      </defs>
    </svg>
  );
};

export default CircleMiddleLayer;
