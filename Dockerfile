FROM azul/zulu-openjdk-alpine:11
ENV PORT 8082
RUN apk update && apk add --no-cache tzdata curl bash && rm -rf /var/cache/apk/*
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8082
COPY target/*.jar /opt/user-profile-manager
WORKDIR /opt
CMD ["java", "-jar", "user-profile-manager"]