import { useId } from 'react';
import { MainCircleProps } from './CircleProps';

const MainCircle = ({ size = 150, colors }: MainCircleProps) => {
  const uniqueId = useId();

  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 157 157"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className="absolute z-5"
    >
      <g opacity="0.8" filter="url(#filter0_dddddd_188_1110)" data-figma-bg-blur-radius="20">
        <path
          d="M143 78.5C143 114.122 114.122 143 78.5 143C42.8776 143 14 114.122 14 78.5C14 42.8776 42.8776 14 78.5 14C114.122 14 143 42.8776 143 78.5Z"
          fill="white"
        />
        <path
          d="M143 78.5C143 114.122 114.122 143 78.5 143C42.8776 143 14 114.122 14 78.5C14 42.8776 42.8776 14 78.5 14C114.122 14 143 42.8776 143 78.5Z"
          fill={`url(#paint0_radial_188_1110_${uniqueId})`}
        />
        <path
          d="M143 78.5C143 114.122 114.122 143 78.5 143C42.8776 143 14 114.122 14 78.5C14 42.8776 42.8776 14 78.5 14C114.122 14 143 42.8776 143 78.5Z"
          fill={`url(#paint1_radial_188_1110_${uniqueId})`}
        />
        <path
          d="M143 78.5C143 114.122 114.122 143 78.5 143C42.8776 143 14 114.122 14 78.5C14 42.8776 42.8776 14 78.5 14C114.122 14 143 42.8776 143 78.5Z"
          fill={`url(#paint2_radial_188_1110_${uniqueId})`}
        />
      </g>

      <defs>
        {/* Blur */}
        <filter
          id="filter0_dddddd_188_1110"
          x="-6"
          y="-6"
          width="169"
          height="169"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feFlood floodOpacity="0" result="BackgroundImageFix" />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="0.16" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow_188_1110" />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="0.32" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect1_dropShadow_188_1110"
            result="effect2_dropShadow_188_1110"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="1.12" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect2_dropShadow_188_1110"
            result="effect3_dropShadow_188_1110"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="2.24" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect3_dropShadow_188_1110"
            result="effect4_dropShadow_188_1110"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="3.84" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect4_dropShadow_188_1110"
            result="effect5_dropShadow_188_1110"
          />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="6.72" />
          <feColorMatrix type="matrix" values="0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 1 0" />
          <feBlend
            mode="normal"
            in2="effect5_dropShadow_188_1110"
            result="effect6_dropShadow_188_1110"
          />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="effect6_dropShadow_188_1110"
            result="shape"
          />
        </filter>
        <clipPath id="bgblur_0_188_1110_clip_path" transform="translate(6 6)">
          <path d="M143 78.5C143 114.122 114.122 143 78.5 143C42.8776 143 14 114.122 14 78.5C14 42.8776 42.8776 14 78.5 14C114.122 14 143 42.8776 143 78.5Z" />
        </clipPath>

        <radialGradient
          id={`paint0_radial_188_1110_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(142.079 78.2454) rotate(152.275) scale(115.919 226.492)"
        >
          <stop stopColor={colors.right} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`paint1_radial_188_1110_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(29.1672 59.7617) rotate(27.3829) scale(159.424 207.638)"
        >
          <stop stopColor={colors.topLeft} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
        <radialGradient
          id={`paint2_radial_188_1110_${uniqueId}`}
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(30.8525 141.3) rotate(-19.6347) scale(162.825 228.109)"
        >
          <stop stopColor={colors.bottomLeft} />
          <stop offset="0.796674" stopColor="#F0F0F0" stopOpacity="0" />
        </radialGradient>
      </defs>
    </svg>
  );
};

export default MainCircle;
