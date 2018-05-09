# Bonnus SDK Android V2.0

- Release 9 mayo 2018.

Actualizaciones:

    - Modo desarrollador. / se implementó la opción de  modo desarrollador para controlarse desde la App.
    - Configuración de colores: Se implemento más colores configurables mejor integración de UX.
    - Plantillas de estilos: Se implementaron plantillas de estilos (Botones, textos, Nofificación, Pop-up) para mejor integración de UX
    - Soporte versiones anteriores de android: Se incremenento la funcionalidad en dispositivos Android hasta la versión SDK 16.
    - Soporte diferentes tipos de pantallas: Se incrementó la correcta funcionalidad con diferentes tipos de pantallas.
    - Boton Cerrar notificación: Se incluyó la opción de cerrar la notificación con un botón.
    
   
    
    
    
Configuración del SDK de Bonnus para Android.

- Incluir SDK al proyecto:

Primero hay que agregar el repositorio de maven en el build.gradle a nivel proyecto:


    allprojects {
        repositories {
            google()
            jcenter()
            maven {
                url  "https://dl.bintray.com/jorshhh/BonnusSDK"
            }
        }

Para agregar el SDK, lo incluimos en nuestro build.gradle a nivel modulo de la siguiente manera:

    compile ('mx.bonnus.bonnussdk:bonnus-sdk:2.0.0@aar'){
        transitive=true
    }

Una vez que eso esta hecho, debemos asegurarnos que haya los siguientes permisos en nuestro manifest:

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

- Credenciales para inicio de sesión en la API de Bonnus:

Para usar el SDK, tenemos que instanciarlo una vez cada vez que la aplicación abra:

     Bonnus.getInstance().initWithCredentials(getApplicationContext(),
                "ParnerId",
                "token",
                "sdkId");

- Configuración del SDK.
La configuración del SDK vive dentro de la plataforma de Bonnus, al activar el SDK se guardaran los datos de configuración en el dispositivo. La configuración del SDK sólo puede ser modificada por Bonnus.

Limite de usuarios (opcional): Se puede establecer un límite de usuarios, al alcanzar este límite el SDK no activará la funcionalidad a otros usuarios.

Mensaje de Abandono (opcional): Algunos Bonnus contienen una liga a sitios o apps externas, es posible enviar al usuario un mensaje de abandono antes de que deje la Aplicación.

Color Fondo: Establece el color a utilizar para el fondo de los Bonnus.
Color notificación: Establece el color de la notificación in-App cuando el usuario ha obtenido un Bonnus.
Color Botón: Establece el color de los botones.
Color Texto: Establece el color de los textos principales (Títulos)

Mensaje Aceptar: Establece el texto del botón para aceptar el Bonnus (Predeterminado: "Aceptar").
Mensaje Cerrar: Establece el texto del botón para cerrar el Pop-up del Bonnus (Predeterminado: "Guardar").

Estilo: Estable la plantilla a utilizar: Actualmente se cuentan con dos plantillas diferentes.
Mensaje de Soporte: Es posible establecer un mensaje personalizado para Dudas / Contacto / Soporte.

Momentos: Para configurar los momentos es necesario la siguiente información:
Trigger: Establece el texto o textos para establecer en la acción: (ej. "Momento1", "Mom1")
Logica de activación: Establece cuándo se va a dar un Bonnus, y consta de dos elementos, Logica (Igual, Mayor o Menor), Numero (1, 2, 100, 50.60).
Reusable: Establece si el usuario puede ganar un Bonnus cada vez que se cumpla la condición o sólo una vez.
Notificación: Establece si el bonnus mostrará una notificación in-App o directamente un Pop-up.


- Configuración opcional del SDK.
El SDK permite configuración opcional desde la aplicación y se debe incluir antes de la activación del SDK

Reestricción de funcionalidad por marca de dispositivo y carrier:

Nos permite limitar el uso del SDK por dispositivo o por carriers, si se omiten estas lineas funcionará para todos los dispositivos Android, al incluir esta funcionalidad se minimiza el uso de datos y conexiones a la API.

        String[] carriers = {"Movistar", "At&t 4g"};
        String[] manufacturers = {"motorola","samsung"};

        Bonnus.getInstance().setCarriers(carriers);
        Bonnus.getInstance().setManufacturers(manufacturers);
        
        // En este ejmplo se limitará la activación del SDK a dispotivos Samsung y Motorola y carriers "Movistar" y "AT&T 4g".
        
Modo Desarrollador.
Es posible inicilizar el SDK como modo desarrollador, de esta forma los desarrolladores solo necesitan borrar el cache de su aplicación para poder utilizar el SDK

Si el modo desarrollador no esta activado, el uso del SDK y Bonnus ganados se limitarán por dispositivo.


- Activar el SDK.
La funcionalidad de Bonnus será transparente hasta que no se active el SDK, de esta forma la Aplicación puede decidir cuando inicializar, por ejemplo si se desea activar la funcionalidad sólo a usuario nuevos entones el SDK se deberá activar en el registro o en el proceso que se deseé.

Una vez que se inicialice el SDK se bajaran lo momentos y configuración de la API de Bonnus.

        Bonnus.getInstance().readRemoteData();

- Registro de Actividad / Momento

En la actividad en la que queramos mandar acciones y mostrar popups tenemos que hacer lo siguiente:

    public void onResume(){
        super.onResume();
        View topView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bonnus.getInstance().registerActivityForPopup(topView, MainActivity.this);
    }

    public void onPause(){
        super.onPause();
        Bonnus.getInstance().unregisterActivityForPopup();
    }

Para procesar una acción:

        Bonnus.getInstance().triggerAction("La accion");

- Listado de Bonnus obtenidos.

Para enseñar la lista de Bonnus obtenidos es necesario llamar a esta función, de esta forma la aplicación puede incluir dentro de se menú o con algún botón en especial.

        Bonnus.getInstance().showEarnedBonnusList();
