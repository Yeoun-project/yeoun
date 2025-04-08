import { ConfigEnv, defineConfig, loadEnv } from 'vite';
import { resolve } from 'path';

import react from '@vitejs/plugin-react';
import tailwindcss from '@tailwindcss/vite';
import svgr from 'vite-plugin-svgr';
import mkcert from 'vite-plugin-mkcert';
// https://vite.dev/config/

export default defineConfig(({ mode }: ConfigEnv) => {
  const env = loadEnv(mode, process.cwd(), '');
  const isDev = mode === 'development';

  return {
    define: {
      'import.meta.env.VITE_API_BASE_URL': JSON.stringify(env.VITE_API_BASE_URL),
      'import.meta.env.VITE_OAUTH_GOOGLE': JSON.stringify(env.VITE_OAUTH_GOOGLE),
      'import.meta.env.VITE_OAUTH_KAKAO': JSON.stringify(env.VITE_OAUTH_KAKAO),
      'import.meta.env.VITE_OAUTH_NAVER': JSON.stringify(env.VITE_OAUTH_NAVER),
    },
    server: {
      https: isDev ? { key: env.VITE_PEM_KEY_PATH, cert: env.VITE_PEM_CERT_PATH } : undefined,
      proxy: {
        '/public': {
          target: env.VITE_API_BASE_URL,
          changeOrigin: true,
          secure: false,
        },

        '/api': {
          target: env.VITE_API_BASE_URL,
          changeOrigin: true,
          secure: false,
        },
      },

      cors: true,
    },

    plugins: [react(), tailwindcss(), svgr(), mkcert()],
    resolve: {
      alias: [
        { find: '@components', replacement: resolve(__dirname, 'src/components') },
        { find: '@pages', replacement: resolve(__dirname, 'src/pages') },
      ],
    },
  };
});
