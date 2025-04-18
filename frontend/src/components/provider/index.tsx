import React from 'react';
import QueryProvider from './query';
import ToastProvider from './toast';

interface ProviderProps {
  children: React.ReactNode;
}

const Provider = ({ children }: ProviderProps) => {
  return (
    <QueryProvider>
      <ToastProvider />
      {children}
    </QueryProvider>
  );
};

export default Provider;
