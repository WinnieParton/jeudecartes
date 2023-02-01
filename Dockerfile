FROM maven:3-openjdk-18

WORKDIR /JeuDeCartes
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run