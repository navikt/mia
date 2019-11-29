FROM nginx:alpine

ADD default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80