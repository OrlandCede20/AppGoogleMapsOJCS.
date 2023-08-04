package com.example.googlecloud;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    GoogleMap mMap;
    List<LatLng> lstlongitud;

    TextView Txtsuma;

    private RequestQueue rq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Txtsuma=(TextView)findViewById(R.id.txtSuma);
        rq= Volley.newRequestQueue(this);


        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lstlongitud=new ArrayList<>();
    }
    ///46.53553340462967, 102.49990890627434
    //63.90163828409896, -18.994459814402173
    PolylineOptions lineas;
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        CameraUpdate camUpd1 =
                CameraUpdateFactory
                        .newLatLngZoom(new LatLng(40.68931426278643, -74.04459172111524), 18);
        mMap.moveCamera(camUpd1);
        mMap.setOnMapClickListener(this);

        lineas= new PolylineOptions();
        lineas.width(15);
        lineas.color(Color.BLUE);
    }
    TextView TxtPunto1,TxtPunto2,TxtPunto3,TxtPunto4,TxtPunto5,TxtPunto6;

    @Override
    public void onMapClick(LatLng latLng) {

        MarkerOptions marcador=new MarkerOptions();
        marcador.position(latLng);
        marcador.title("Punto");

        mMap.addMarker(marcador);
        lineas.add(latLng);

        TxtPunto1=(TextView)findViewById(R.id.txtPunto1);
        TxtPunto2=(TextView)findViewById(R.id.txtPunto2);
        TxtPunto3=(TextView)findViewById(R.id.txtPunto3);
        TxtPunto4=(TextView)findViewById(R.id.txtPunto4);
        TxtPunto5=(TextView)findViewById(R.id.txtPunto5);
        TxtPunto6=(TextView)findViewById(R.id.txtPunto6);


        if (lineas.getPoints().size()==6)
        {
            lineas.add(lineas.getPoints().get(0));
            mMap.addPolyline(lineas);
            TxtPunto1.setText("Punto 1: "+lineas.getPoints().get(0).toString());
            TxtPunto2.setText("Punto 2: "+lineas.getPoints().get(1).toString());
            TxtPunto3.setText("Punto 3: "+lineas.getPoints().get(2).toString());
            TxtPunto4.setText("Punto 4: "+lineas.getPoints().get(3).toString());
            TxtPunto5.setText("Punto 5: "+lineas.getPoints().get(4).toString());
            TxtPunto6.setText("Punto 6: "+lineas.getPoints().get(5).toString());
            lineas.getPoints().clear();
        }
        if(lineas.getPoints().size()==7)
        {
            TxtPunto1.setText("");TxtPunto2.setText("");TxtPunto3.setText("");
            TxtPunto4.setText("");TxtPunto5.setText("");TxtPunto6.setText("");

        }
    }
    public void CalcuDistan(View v)
    {
        String url="https://maps.googleapis.com/maps/api/distancematrix/json?destinations=-1.01265579410353, -79.46705806993107&origins=-1.0123809106065549, -79.47098884738077&units=metters&key=AIzaSyDMmRXHBYOjJyXZruXemR11tl7uiJ2T_Q8";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray filas = response.getJSONArray("rows");
                            JSONObject fila = filas.getJSONObject(0);
                            JSONArray datos = fila.getJSONArray("elements");

                            int total = 0,subtotal=0;
                            for (int i = 0; i < datos.length(); i++) {
                                JSONObject dato = datos.getJSONObject(i);
                                JSONObject distancia = dato.getJSONObject("distance");
                                subtotal = distancia.getInt("value");
                                total += subtotal;
                            }

                            Toast.makeText(MainActivity.this, total +" metros", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de error
                    }
                });

        // Agrega la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}