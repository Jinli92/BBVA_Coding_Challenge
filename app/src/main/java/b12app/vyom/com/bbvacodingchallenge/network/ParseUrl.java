package b12app.vyom.com.bbvacodingchallenge.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import b12app.vyom.com.bbvacodingchallenge.model.NearbyLocation;


public class ParseUrl {

    RequestQueue mQueue;
    public static String TAG="parse url";

    public ParseUrl(RequestQueue mQueue) {
        this.mQueue = mQueue;
    }

    public void parseUrl(final List<NearbyLocation> resultList, double myLat, double myLng){

        String api_key = "AIzaSyAeSKz8VEHnLGHo7aj5a1HXOvChalyOj0g";
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location="+myLat+","+myLng+"&radius=10000&key="+api_key;
        JsonObjectRequest locationsRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i(TAG, "Google map response: \n"+response);

                try {
                    JSONArray results = response.getJSONArray("results");

                    for(int i=0; i<results.length();i++){

                        JSONObject nearbylocation = results.getJSONObject(i);

                        String formatted_address = nearbylocation.getString("formatted_address");

                        JSONObject geometry = nearbylocation.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        double location_lat = location.getDouble("lat");
                        double location_lng = location.getDouble("lng");
                        String name = nearbylocation.getString("name");
                        JSONArray types = nearbylocation.getJSONArray("types");
                        String place_id = nearbylocation.getString("place_id");
                        String poi = types.getString(0);

                        NearbyLocation nearbyLocation = new NearbyLocation(place_id,name,formatted_address,location_lat,location_lng,poi);
                        resultList.add(nearbyLocation);

                    }

//                    Log.i(TAG, "resultList: "+resultList.get(3).getFormatted_address());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: "+error);
            }
        });

        mQueue.add(locationsRequest);


    }}
