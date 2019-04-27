package cs.utep.edu.app10;

import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import cs.utep.edu.app10.R;

public class PlaceInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context ctx;
    private RatingBar ratingBar;
    private View view;

    public PlaceInfoWindowGoogleMap(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        view = ((Activity)ctx).getLayoutInflater()
                .inflate(R.layout.place_window_info, null);
        view.setClickable(false);

        TextView placeName = view.findViewById(R.id.place_name);
        TextView placeRating = view.findViewById(R.id.place_rating);
        ratingBar = view.findViewById(R.id.ratingBar);



        placeName.setText(marker.getTitle());
        placeRating.setText("Rating");
        ratingBar.setRating(3);
        return view;
    }

    public void updateRating(float rating){
        ratingBar.setRating(rating);
    }



}
