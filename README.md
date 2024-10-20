# Classpath and DB.properties configuration
Estos archivos no deben ser modificados, solo se debe mantener la versión actual del archivo
Para esto, utilicen los siguientes comandos de git:

- git update-index --assume-unchanged threads/DB.properties
- git update-index --assume-unchanged threads/.Classpath

# .gitignore configuration
Git debería registrar solo cambios de código a partir de ahora, así que si notan que otros 
archivos que no sean de código están saliendo, utilicen el comando de arriba si consideran que
se necesita dejar una copia del archivo ignorando los cambios futuros que se realicen, o 
utilicen git rm --cached <file> para eliminarlos del seguimiento de git y luego añadirlo
al .gitignore

# Comentarios y orden visual
Mantengan el código comentado y el orden visual lo más que se pueda para no estar preguntando todo

# Comuníquense desgraciados, un beso