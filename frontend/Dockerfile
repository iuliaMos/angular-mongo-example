FROM node:16.16.0 As node
RUN mkdir -p /app
WORKDIR /app
COPY package.json /app
RUN npm install
COPY . /app
CMD ["npm", "run", "build"]

FROM nginx:1.25.3-alpine-slim
COPY --from=node /app/dist/frontend /usr/share/nginx/html
ENTRYPOINT ["nginx", "-g", "daemon off;"]

