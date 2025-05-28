import { useState, useEffect, useCallback } from 'react';

const MOBILE_BREAKPOINT_PX = 430;

export const useIsMobile = (): boolean => {
  const checkIsMobile = useCallback(() => {
    if (typeof window !== 'undefined') {
      return window.matchMedia(`(max-width: ${MOBILE_BREAKPOINT_PX}px)`).matches;
    }
    return false;
  }, []);

  const [isMobile, setIsMobile] = useState(checkIsMobile());

  useEffect(() => {
    const handleResize = () => {
      setIsMobile(checkIsMobile());
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, [checkIsMobile]);

  return isMobile;
};
