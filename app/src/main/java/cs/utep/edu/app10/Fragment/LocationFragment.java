package cs.utep.edu.app10.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import cs.utep.edu.app10.DisabilityAdapter;
import cs.utep.edu.app10.PlaceInfoWindowGoogleMap;
import cs.utep.edu.app10.R;

import static android.content.Context.LOCATION_SERVICE;


public class LocationFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPoiClickListener, GoogleMap.OnInfoWindowLongClickListener, View.OnCreateContextMenuListener {

    private GoogleMap googleMap;
    private PlaceInfoWindowGoogleMap customWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);

        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.mapContainer, mMapFragment).commit();
        mMapFragment.getMapAsync(this);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setOnPoiClickListener(this::onPoiClick);
        googleMap.setOnInfoWindowLongClickListener(this::onInfoWindowLongClick);
        googleMap.setMyLocationEnabled(true);

         customWindow = new PlaceInfoWindowGoogleMap(getActivity());

        this.googleMap.setInfoWindowAdapter(customWindow);
        setStartingLocation();




    }

    public void setStartingLocation() {
        int GPSMode = 0;
        LocationManager locManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        try {
            GPSMode = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        String provide = locManager.getBestProvider(criteria, true);
        if (GPSMode == 0) {

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 2);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        Location myLoc = locManager.getLastKnownLocation(provide);
        if (myLoc == null) {
            double lat = 29.5528;
            double lon = -95.0932;
            LatLng JSC = new LatLng(lat, lon);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(JSC));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        } else {
            double lat = myLoc.getLatitude();
            double lon = myLoc.getLongitude();
            LatLng latLng = new LatLng(lat, lon);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }

    }


    @Override
    public void onInfoWindowLongClick(Marker marker) {
        Toast.makeText(getActivity(),"LCLICKED INFO WINDOW",Toast.LENGTH_SHORT).show();
        View curr = customWindow.getInfoWindow(marker);
        DialogFragment placeFragment = new PlaceRatingFragment();
        placeFragment.setTargetFragment(this,1);
        placeFragment.show(getFragmentManager(),"placeRating");

    }

    @Override
    public void onPoiClick(PointOfInterest poi) {

        googleMap.addMarker(new MarkerOptions()
                .position(poi.latLng)
                .title(poi.name)
                .snippet(poi.placeId));
    }

    public static class PlaceRatingFragment extends DialogFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_place_rate, null);


            RecyclerView rv = view.findViewById(R.id.disability_recycler_view);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            rv.setLayoutManager(manager);
            rv.setAdapter(new DisabilityAdapter(getActivity()));

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view).setTitle("Place Details")
                    // Add action buttons
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            return builder.create();

        }
    }
}
