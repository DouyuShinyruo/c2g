# frontend/Dockerfile
FROM node:20

WORKDIR /app

COPY package.json ./
COPY package-lock.json ./
COPY .env ./

RUN npm install

COPY . .

EXPOSE 80

CMD ["npm", "start"]
