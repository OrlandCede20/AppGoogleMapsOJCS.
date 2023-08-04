package com.example.googlecloud;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    GoogleMap mMap;
    List<LatLng> lstlongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}
