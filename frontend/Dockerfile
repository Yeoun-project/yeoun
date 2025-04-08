FROM node:20-alpine AS build

WORKDIR /app

COPY frontend/package.json ./
COPY package-lock.json ./

RUN npm ci

COPY frontend/.env.production .env.production

COPY frontend/ .

RUN npm run build -- --mode=production

FROM nginx:alpine

COPY nginx/conf.d/default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
EXPOSE 443

CMD ["nginx", "-g", "daemon off;"]