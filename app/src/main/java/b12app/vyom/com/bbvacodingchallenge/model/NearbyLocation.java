package b12app.vyom.com.bbvacodingchallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class NearbyLocation implements Parcelable {


    String place_id;
    String name;
    String formatted_address;
    double location_lat;
    double location_lng;
    String poi;

    public NearbyLocation(Parcel in){

        place_id = in.readString();
        name = in.readString();
        formatted_address = in.readString();
        location_lat = in.readDouble();
        location_lng = in.readDouble();
        poi = in.readString();

    }

    public NearbyLocation(String place_id, String name, String formatted_address, double location_lat, double location_lng, String poi) {
        this.place_id = place_id;
        this.name = name;
        this.formatted_address = formatted_address;
        this.location_lat = location_lat;
        this.location_lng = location_lng;
        this.poi = poi;
    }

    public static final Creator<NearbyLocation> CREATOR = new Creator<NearbyLocation>() {
        @Override
        public NearbyLocation createFromParcel(Parcel in) {
            return new NearbyLocation(in);
        }

        @Override
        public NearbyLocation[] newArray(int size) {
            return new NearbyLocation[size];
        }
    };

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    public double getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(double location_lng) {
        this.location_lng = location_lng;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(place_id);
        dest.writeString(name);
        dest.writeString(formatted_address);
        dest.writeDouble(location_lat);
        dest.writeDouble(location_lng);
        dest.writeString(poi);
    }
}
