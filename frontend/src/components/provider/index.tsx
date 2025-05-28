import React from 'react';
import QueryProvider from './query';
import ToastProvider from './toast';
import UTProvder from './UTProvder';
import { useIsMobile } from '../../hooks/useIsMobile';

interface ProviderProps {
  children: React.ReactNode;
}

const Provider = ({ children }: ProviderProps) => {
  const checkIsMobileView = useIsMobile();
  const utReview = localStorage.getItem('utReview') === 'complete';
  return (
    <QueryProvider>
      {checkIsMobileView && !utReview && <UTProvder />}
      <ToastProvider />
      {children}
    </QueryProvider>
  );
};

export default Provider;
