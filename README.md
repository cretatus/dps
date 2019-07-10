# co-Meta installation
1. Install MySQL 5.7 
2. In MySQL database create scheme "cometa"
3. Execute there the last dump from catalog /co-meta/db/dump 
4. Check credentials db.* in /co-meta/src/main/resources/config.properties
6. The property "dir.root" must direct to the actual empty folder
7. Install and configure Tomcat v8.5
8. Everything else is a simple maven project.
