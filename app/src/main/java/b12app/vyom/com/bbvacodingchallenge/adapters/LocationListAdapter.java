package b12app.vyom.com.bbvacodingchallenge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import b12app.vyom.com.bbvacodingchallenge.R;
import b12app.vyom.com.bbvacodingchallenge.model.NearbyLocation;

public class LocationListAdapter extends BaseAdapter {

    List<NearbyLocation> mLocationlist;
    Context context;
    LayoutInflater layoutInflater;

    public LocationListAdapter(List<NearbyLocation> mLocationlist, Context context) {
        this.mLocationlist = mLocationlist;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mLocationlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mLocationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LocationListViewHolder locationListViewHolder;
        convertView = layoutInflater.inflate(R.layout.location_item_format,parent,false);

        if(true) {

            locationListViewHolder = new LocationListViewHolder();

//            locationListViewHolder.tvId = convertView.findViewById(R.id.tvId);
            locationListViewHolder.tvName = convertView.findViewById(R.id.tvName);
            locationListViewHolder.tvLatitude = convertView.findViewById(R.id.tvLatitude);
            locationListViewHolder.tvLongitude = convertView.findViewById(R.id.tvLongitude);
            locationListViewHolder.tvAddress = convertView.findViewById(R.id.tvAddress);

            convertView.setTag(locationListViewHolder);


//            locationListViewHolder.tvId.setText(mLocationlist.get(position).getID());
            locationListViewHolder.tvName.setText(mLocationlist.get(position).getName());
            locationListViewHolder.tvLatitude.setText(String.valueOf(mLocationlist.get(position).getLocation_lat()));
            locationListViewHolder.tvLongitude.setText(String.valueOf(mLocationlist.get(position).getLocation_lng()));
            locationListViewHolder.tvAddress.setText(mLocationlist.get(position).getFormatted_address());


        }
        return convertView;
    }

   public class LocationListViewHolder{

        TextView tvId,tvName, tvLatitude, tvLongitude, tvAddress;

   }
}
