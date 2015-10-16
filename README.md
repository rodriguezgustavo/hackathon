# hackathon

Para armar el ambiente local:

- bajarse maven 3.3.1 (seguramente anda con otros pero no esta testeado)
- instalarse el tomcat 7
- crear base de datos y crear las tablas con el script db.sql que esta en resources
- modificar el pom, en el profile development con la info de la base de datos local
- copiar  mysql-connector-java-5.1.25.jar que esta en resources/lib en el dir lib del tomcat
- modificar el archivo server.xml que esta en el conf del tomcat y agregar esta seccion abajo  de otra seccion Realm que ya debe estar (hay que completar con los datos de la base)

```xml
<Realm  className="org.apache.catalina.realm.JDBCRealm"
             driverName="com.mysql.jdbc.Driver"
          connectionURL="jdbc:mysql://{db.host}:{db.port}/{db.name}"
         connectionName="{db.user}" connectionPassword="{db.pass}"
              userTable="user" userNameCol="user_name" userCredCol="user_pass"
          userRoleTable="user_role" roleNameCol="role_name" />
```

Para correrlo:
Opcion 1) agarrar el IDE, abrir el pom, configurar el tomcat de tu maquina y correrlo
Opcion 2) correr mvn package -P dev, agarrar el war que se genero en target dir y moverlo al webapp de tu tomcat
Para generar war de production: mvn package -P production
