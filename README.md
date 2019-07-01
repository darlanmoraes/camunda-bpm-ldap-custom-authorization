# Running Camunda with LDAP Authentication + LDAP Authorization

This a test that was made using LDAP users + groups to show that is possible to customize authorization using just LDAP.

You will need Docker:

```
Docker version 18.06.1-ce, build e68fc7a
Apache Maven 3.5.4 (1edded0938998edf8bf061f1ceb3cfdeccf443fe; 2018-06-17T15:33:14-03:00)
Java version: 1.8.0_121, vendor: Oracle Corporation, runtime: /opt/jdk1.8.0_121/jre
```

Building Camunda 7.4.0:

```
mvn clean -U install
```

Starting LDAP:
```
docker run -p 389:389 -p 636:636 --detach osixia/openldap:latest
```

Starting Camunda 7.4.0:
```
java -jar /home/$(whoami)/.m2/repository/org/camunda/bpm/authorization/camunda-bpm-ldap-custom-authorization/0.0.1-SNAPSHOT/camunda-bpm-ldap-custom-authorization-0.0.1-SNAPSHOT.jar
```

Creating groups and users:

```
docker cp ldap-entries.ldif $(docker ps | grep 'openldap' | awk '{ print $1 }'):/ldap-entries.ldif
docker exec -it $(docker ps | grep 'openldap' | awk '{ print $1 }') ldapadd -x -D "cn=admin,dc=example,dc=org" -H ldap://localhost -f /ldap-entries.ldif -w admin
```

You can test to see if everything is fine with LDAP:

```
docker exec -it $(docker ps | grep 'openldap' | awk '{ print $1 }') ldapsearch -x -H ldap://localhost -b dc=example,dc=org -D "cn=admin,dc=example,dc=org" -w admin
```

Docker useful commands:
```
docker kill $(docker ps | grep 'openldap' | awk '{ print $1 }') 
docker kill $(docker ps -q)
```

Users and Groups:

- `camunda`: admin, cockpit, tasklist
- `steves`: cockpit, tasklist
- `william`: tasklist
- `lucy`: tasklist