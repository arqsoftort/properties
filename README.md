#Laboratorio de 'Configuración Externa'

##Objetivo
La práctica de externalizar configuraciones es muy común en el desarrollo de aplicaciones Java. Sobre todo cuando existen parámetros que pueden llegar a cambiar durante la vida de la aplicación y de otra manera implicarían un cambio en el código, recompilaciones, etc.

Este laboratorio intenta ayudar en el manejo de estas configuraciones desde una aplicación Java de ejemplo, tanto como para leerlas como para escribirlas.

##Requisitos
- NetBeans IDE o similar

##Introducción

####Escenario de aplicación
Imaginemos una aplicación de escritorio en Java que admite varios 'skins'. Es decir, el usuario puede cambiar el esquema de colores, las fuentes, etc. mientras la aplicación se ejecuta.

En principio, podríamos tener una variable estática que pueda ser accedida desde toda la aplicación que contenga el skin seleccionado.

```java
public static String SKIN_ACTUAL = "rojo";
```

Para poder cambiar el skin, deberíamos editar el código. Esto implica apagar la aplicación, modificar el código, compilar, etc.

Sería bueno que exista una manera de poder editar ese valor sin tener que pasar por ese proceso, verdad?

####Properties

A pesar de que se pueden utilizar otros tipos de archivos, los archivos de extensión *.properties* son ideales para este escenario.

Cada línea en estos archivos representa una *property*. A su vez, cada property tiene una *key* y un *value*, que pueden ser representadas de las siguientes maneras (siendo la primera la más común):

```java
key=value
key = value
key:value
key value
```

En el siguiente ejemplo se definen las claves `color`, `habilitado` y `cantidad`, cada una con un valor asignado.

```java
color=rojo
habilitado=true
cantidad=5
```

Además, admiten la utilización del caracter <kbd>#</kbd> o <kbd>!</kbd> al comienzo de una línea para marcar que el resto del texto en ella es un comentario. En el siguiente ejemplo, la única clave es `answer`, ya que las demás líneas están comentadas.

```java
#Esto es un comentario
answer=42
#Otro comentario
```

Se debe tener en cuenta que para poder usar los caracteres que tienen significados especiales sin su significado, deben ser "escapados". La manera de hacerlo, es agregar una <kbd>\</kbd> antes. Por ejemplo:

```java
#escapeando el :
website=http\:www.google.com

#escapeando el =
cuenta=2 + 2 \= 4

#escapeando el #
guia=Manda un SMS al \#1122
```

Es una práctica muy común y recomendada el utilizar una nomenclatura similar a la de paquetes para las keys, lo que permite visualizarlas de manera ordenada y agrupada. Por ejemplo:

```java
app.config.enabled=true

app.config.color.primary=rojo
app.config.color.secondary=blanco

app.config.font.family=Arial
app.config.font.size=12
app.config.font.style.bold=true
app.config.font.style.underline=false
```

En Java, la clase `java.util.Properties` se encarga de mapear estos archivos a un objeto en memoria que nos permite acceder a las properties como un diccionario de clave y valor. En la [documentación](http://docs.oracle.com/javase/7/docs/api/java/util/Properties.html), se puede encontrar la lista de métodos disponibles para listarlas, obtenerlas y escribirlas, entre otras operaciones.

##Ejemplo de Uso
Crearemos una aplicación muy simple en Java que lea y escriba configuraciones externas en un archivo *.properties* utilizando la clase `Properties`.

####1. Crear la aplicación

Crear una aplicación de nombre `PropertiesLab`, habilitando la opción de crear una clase principal con el nombre `arqsoft.lab.properties.Main`.

####2. Crear el administrador de properties
Crear una clase llamada `PropertiesHelper` bajo el paquete `arqsoft.lab.properties.helper`.

####3. Agregar el método de lectura

Usaremos un método público y estático para poder utilizarlo desde cualquier lugar de nuestra aplicación. Recibe el nombre del archivo (incluyendo el path relativo al directorio de ejecución de la aplicación si es necesario) y la key a buscar en él. Devuelve el valor de la key si lo encuentra o null en caso de que no exista la clave o se de una excepción.

```java
public static String read(String filename, String key) {
        
    String value = null;
    Properties prop = new Properties();
	InputStream input = null;
 
	try {
        String path = filename + ".properties";
        input = new FileInputStream(path);
        prop.load(input);
        value = prop.getProperty(key);
	} catch (IOException ex) {
        ex.printStackTrace();
	} finally {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
    
    return value;
    
}
```

####4. Crear el archivo .properties

Crear un archivo .properties en la raíz del directorio de código del proyecto con el siguiente contenido:

```java
app.name=PropertiesLab
app.version=0.0.1
app.config.skin.name=SuperJavaSkin
app.config.skin.creator=Steve Stevens
```

**NOTA:** Se debe tener especial cuidado con la ubicación del archivo de configuración. Por ejemplo, se aconseja tratar de que su ubicación sea relativa al proyecto y no absoluta, ya que si se apunta a un directorio como `C:\carpeta\config.properties` y se intenta ejecutar la aplicación en un SO distinto, no funcionará. Es recomendable investigar sobre cómo y dónde ubicar y luego encontrar recursos en una aplicación Java, como se plantea [éste post](http://www.mkyong.com/java/java-getresourceasstream-in-static-method/) o en [éste otro](http://stackoverflow.com/questions/333363/loading-a-properties-file-from-java-package).

####5. Utilizar el método desde el main

```java
public static void main(String[] args) {
        
    String filename = "config";
    
	String key = "app.config.skin.name";

    String value = PropertiesHelper.read(filename, key);
    
    System.out.println("Value = " + value);
    
}
```

####6. Ejecutar

Al ejecutar, se debe leer en la consola el siguiente output:

```java
Value = SuperJavaSkin
```

##Feedback
Por cualquier consulta sobre el laboratorio, pueden crear un [Issue](https://github.com/asortlabs/properties/issues) en la sección correspondiente y lo responderemos a la brevedad.

Por aportes sobre el código o documentación, por favor realizar un [Pull Request](https://github.com/asortlabs/properties/pulls) para que podamos evaluarlo e incluirlo.