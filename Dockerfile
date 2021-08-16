FROM adoptopenjdk:16-jre-hotspot
RUN mkdir /opt/app
COPY target/character-creator-0.0.1-SNAPSHOT.jar /opt/app/character-creator.jar
CMD ["java","-jar","/opt/app/character-creator.jar"]