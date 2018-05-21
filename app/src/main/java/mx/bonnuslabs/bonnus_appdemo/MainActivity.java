package mx.bonnuslabs.bonnus_appdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import mx.bonnus.bonnussdk.Bonnus;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Llamada para configurar el SDK. Se debe realizar sólo una vez, cada que corra la aplicación.
        Bonnus.getInstance().initWithCredentials(getApplicationContext(),
                "","","");


        String[] carriers = {"Movistar", "At&t 4g"};
        String[] manufacturers = {"motorola","samsung"};


        //Bonnus.getInstance().setCarriers(carriers);
        //Bonnus.getInstance().setManufacturers(manufacturers);

        TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        String manufacturer = Build.MANUFACTURER;

        TextView carrierView = (TextView)findViewById(R.id.textView);
        TextView manufacturerView = (TextView)findViewById(R.id.textView2);

        carrierView.setText("Carrier: "+carrierName);
        manufacturerView.setText("Manufactured: "+manufacturer);

        Bonnus.getInstance().setDeveloperMode(false);
    }

    public void onResume(){
        super.onResume();

        //Esta llamada debe de hacerse cada que se entre a una Actividad, para poder poner elementos gráficos.
        View topView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bonnus.getInstance().  registerActivityForPopup(topView, MainActivity.this);
    }

    public void onPause(){
        super.onPause();

        //Esto se debe de hacer cada vez que se salga de una actividad registrada.
        Bonnus.getInstance().unregisterActivityForPopup();
    }

    public void process(View view) {

        String tag = view.getTag().toString();

        //Este método nos permite registrar una función. Mandamos un texto dependiendo de la acción.
        //Si se terminó las veces necesarias para la acción, se mostrara un banner con la promoción.
        Bonnus.getInstance().triggerAction(tag);
    }

    public void recompensas(View view) {
        Bonnus.getInstance().showEarnedBonnusList();
    }

    public void readRemoteData(View view) {
        Bonnus.getInstance().readRemoteData();
    }
}
