FROM nginx:mainline
MAINTAINER weifufa/weifufa <wff933@foxmail.com>

ADD ./dist/ /usr/share/nginx/html
ADD ./default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
