# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación en el contenedor
COPY target/entidad-financiera-0.0.1-SNAPSHOT.jar /app/entidad-financiera.jar

# Exponer el puerto en el que Spring Boot estará escuchando
EXPOSE 8080

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "entidad-financiera.jar"]
