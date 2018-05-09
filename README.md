# Bonnus SDK Android V2.0

Configuración del SDK de Bonnus para Android.

- Incluir librería al proyecto:
Primero hay que agregar el repositorio de maven en el build.gradle a nivel proyectoÑ

    allprojects {
        repositories {
            google()
            jcenter()
            maven {
                url  "https://dl.bintray.com/jorshhh/BonnusSDK"
            }
        }

Para agregar el SDK, lo incluimos en nuestro build.gradle a nivel modulo de la siguiente manera:

    compile ('mx.aidc.bonnussdk:bonnus-sdk:1.0.6@aar'){
        transitive=true
    }

Una vez que eso esta hecho, debemos asegurarnos que haya los siguientes permisos en nuestro manifest:

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

Para usar el SDK, tenemos que instanciarlo una vez cada vez que la aplicación abra:

     Bonnus.getInstance().initWithCredentials(getApplicationContext(),
                "ParnerId",
                "token",
                "sdkId");

Las recompensas disponibles deben bajarse por lo menos una vez. Se recomienda después de que el usuario haga login.
Se puede actualizar todas las veces que sea necesario.

        Bonnus.getInstance().readRemoteData();


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

Y para enseñar la lista de promociones ganadas:

        Bonnus.getInstance().showEarnedBonnusList();
