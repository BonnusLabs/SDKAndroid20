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
                "xSc7khPOtO10S3Pm12QSDs8Gro9Snebg",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik1qUTFNVVJEUVVWRlEwUXpRekl3TXpNek9EVkROa1ZHUkRKRk16Y3lSREU1UWtNelJrTkNRUSJ9.eyJpc3MiOiJodHRwczovL3ZhbG1hbnMuYXV0aDAuY29tLyIsInN1YiI6InhTYzdraFBPdE8xMFMzUG0xMlFTRHM4R3JvOVNuZWJnQGNsaWVudHMiLCJhdWQiOiJodHRwOi8vYm9ubnVzYXBpLmF6dXJld2Vic2l0ZXMubmV0L2FwaSIsImlhdCI6MTUxMTU3ODA4MSwiZXhwIjoxNTI3MTMwMDgxLCJzY29wZSI6Im9wZW5pZDpwcm9maWxlOmVtYWlsIG9wZW5pZCIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.JdtPV-ouuQnOquXbaEYXNxn6D8cnVUfke_H9IGsmn-cTerYy7SNFldNxlejJZvjzrqnV02AewyamINKMECrfbHqS_g04A8LDcJ5xabtVhmtkwV0pWxaZjcJ-frAFrzqSiyA_i20lAYNBKseBucDVBxFM7uEAA7PF43g6evXVBG6iW1l7YzgnFFqYkcx2Nu4lcWOM1OMbA274kO-_ECbvFEGqLSfva2VPGgvcpQgKdD9WICyHnxYJEPrjPE5Zm7j8thtR8PF5jzB2mcpnxIy6tejitxPpGqRs6oZ_fWOu07byESPue4VkAY2o7I11jNBylINxDG84R6gfhdQOw8SQtA",
                "be07223552934a7cbf4e589f80d7e7c0");


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
