package com.example.a3;

import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.lang.*;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapView;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class MapsFragment extends Fragment implements View.OnClickListener {
    private View vMaps;
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    private LatLng addressLatLng = null; //new LatLng(37.7749, -122.4194);
    private String[] parkNames;
    private LatLng[] parkLatlng;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // Set Variables and listeners
        super.onCreate(savedInstanceState);
//        MapQuest.

        vMaps = inflater.inflate(R.layout.fragment_maps, container, false);
        String userId = Integer.toString(((MainActivity) getActivity()).getTheUserId());

        AddressAsyncTask addressAsyncTask = new AddressAsyncTask();
        addressAsyncTask.execute(userId);

        mMapView = (MapView) vMaps.findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);

        return vMaps;
    }

    @Override
    public void onClick(View v) {
        TextView tvWelcome = (TextView) vMaps.findViewById(R.id.welcome);
        tvWelcome.setText("Welcome");
    }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(addressLatLng);
        markerOptions.title("You!");
        markerOptions.snippet("Your Home Location!");
        mapboxMap.addMarker(markerOptions);
    }

    private void addParkMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();

        IconFactory iconFactory = IconFactory.getInstance(this.getActivity());
        Icon icon = iconFactory.fromResource(R.drawable.greenmarker);


        for (int i = 0; i < parkNames.length; i++) {
            markerOptions.position(parkLatlng[i]);
            markerOptions.title(parkNames[i]);
            markerOptions.snippet("Water Parks!");
            mapboxMap.addMarker(markerOptions).setIcon(icon);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mMapView!=null)
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mMapView!=null)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMapView!=null)
            mMapView.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mMapView!=null)
            mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mMapView!=null)
            mMapView.onSaveInstanceState(outState);
    }


    private class AddressAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return restClient.findAddress(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {

                    String address = result;
                    Geocoder coder = new Geocoder(getApplicationContext());
                    double lat = 0;
                    double lng = 0;
                    if (address != null && !address.isEmpty()) {
                        try {
                            List<Address> addressList = coder.getFromLocationName(address, 1);
                            if (addressList != null && addressList.size() > 0) {
                                lat = addressList.get(0).getLatitude();
                                lng = addressList.get(0).getLongitude();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } // end catch
                    }

                    addressLatLng = new LatLng(lat, lng);

                    mMapboxMap = mapboxMap;
                    mMapView.setStreetMode();
                    mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addressLatLng, 11));
                    addMarker(mapboxMap);

                    ParksAsyncTask parksAsyncTask = new ParksAsyncTask();
                    parksAsyncTask.execute(address);
                }
            });
        }
    }


    private class ParksAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return MapQuest.searchParks(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {




            try {


                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("searchResults");
                parkNames = new String[jsonArray.length()];
                parkLatlng = new LatLng[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    parkNames[i] = jsonArray.getJSONObject(i).getString("name");
                    parkLatlng[i] = new LatLng(Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("fields").getJSONObject("mqap_geography").getJSONObject("latLng").getString("lat")),
                            Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("fields").getJSONObject("mqap_geography").getJSONObject("latLng").getString("lng")));

                }

                addParkMarker(mMapboxMap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
}