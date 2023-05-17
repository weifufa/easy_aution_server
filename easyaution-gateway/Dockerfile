
FROM adoptopenjdk:8-jdk-openj9
MAINTAINER feisjy/ChenHongRui <veryrui@gmail.com>

# 数据卷
VOLUME /tmp/data

ADD ./easyaution-gateway/target/easyaution-gateway-1.0.0.jar /app.jar




# 环境配置参数 -e env='dev'
# dev/prod/local  开发/生产/本地
#ENV env "staging"

EXPOSE 99

# 测试的新参数
ENTRYPOINT ["nohup","java","-jar","/app.jar","-Xmx128m","-Xss256k","-XX:ParallelGCThreads=2","-Djava.compiler=NONE","--spring.profiles.active=${env}",">","nohup.out","2>&1","&"]
