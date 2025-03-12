import React from 'react';

interface CircleProps {
  children?: React.ReactNode;
}

const Circle = ({ children }: CircleProps) => {
  return (
    <div className="relative rounded-full bg-white min-w-[130px] min-h-[130px] max-w-[300px] size-auto inline-flex items-center justify-center">
      <div className="aspect-square circle bg-circle-gradient">
        <p className="text-xl p-4 flex flex-col items-center justify-center text-black drop-shadow-md text-center break-keep [text-shadow:_0px_0px_3px_rgb(255,255,255)]">
          {children}
        </p>
      </div>
      <div className="circle-layer-container animate-spin-first">
        <div className="bg-circle-gradient circle-layer skew-5 scale-108" />
      </div>
      <div className="circle-layer-container animate-spin-second">
        <div className="bg-circle-gradient circle-layer animate-pulse scale-110 skew-2" />
      </div>
      <div className="circle-layer-container animate-spin-third">
        <div className="bg-circle-gradient circle-layer scale-112 skew-4" />
      </div>
    </div>
  );
};

export default Circle;
