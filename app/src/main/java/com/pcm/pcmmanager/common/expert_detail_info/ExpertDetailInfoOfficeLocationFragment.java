package com.pcm.pcmmanager.common.expert_detail_info;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertDetailInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpertDetailInfoOfficeLocationFragment extends Fragment implements OnMapReadyCallback, ExpertDetailInfoActivity.OnNotifyReceiver {

    private GoogleMap mMap;
    TextView name, email, domain, address;
    Button tel;
    ExpertDetailInfo expertDetailInfo;
    Double lat,lon;
    RecyclerView recyclerView;

    public ExpertDetailInfoOfficeLocationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expert_detail_info_office_location, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.detail_map_fragment);
        mapFragment.getMapAsync(this);
        recyclerView = (RecyclerView)view.findViewById(R.id.expert_detail_info_office_rv_list);
        name = (TextView) view.findViewById(R.id.expert_detail_info_office_name);
        email = (TextView) view.findViewById(R.id.expert_detail_info_office_email);
        domain = (TextView) view.findViewById(R.id.expert_detail_info_office_domain);
        address = (TextView) view.findViewById(R.id.expert_detail_info_office_address);
        tel = (Button) view.findViewById(R.id.expert_detail_info_office_tel);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        onNotify();
        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        if (isCache) {
            moveMap(lat, lon);
        }
    }

    boolean isCache = false;
    private void moveMap(double lat, double lon) {
        if (mMap == null) {
            this.lat = lat;
            this.lon = lon;
            isCache = true;
            return;
        }
        isCache = false;
        LatLng latLng = new LatLng(lat,lon);
        MarkerOptions marker = new MarkerOptions().position(latLng);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ExpertDetailInfoActivity) getActivity()).registerNotifyReceiver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((ExpertDetailInfoActivity) getActivity()).unregisterNotifyReceiver(this);
    }

    @Override
    public void onNotify() {
        expertDetailInfo = ((ExpertDetailInfoActivity) getActivity()).getExportDetailInfo();
        // update...
        if (expertDetailInfo != null) {
            name.setText(expertDetailInfo.getOfficeName());
            email.setText(expertDetailInfo.getEmail());
            if (!TextUtils.isEmpty(expertDetailInfo.getHomepage())) {
                domain.setText(expertDetailInfo.getHomepage());
            } else {
                domain.setVisibility(View.GONE);
            }
            address.setText(expertDetailInfo.getAddress());
            lat = Double.valueOf(expertDetailInfo.getLatitude());
            lon = Double.valueOf(expertDetailInfo.getLongitude());

            moveMap(lat,lon);
            tel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel: "+expertDetailInfo.getTel()));
                    startActivity(callIntent);
                }
            });
        }
    }
}
