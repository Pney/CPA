FROM node:18.15.0-slim

WORKDIR /app

COPY package.json yarn.lock ./
RUN yarn install

COPY . .
RUN yarn build
EXPOSE 3000
CMD ["yarn", "dev"]
