package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.booking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.ListIterator;

import dagger.hilt.android.AndroidEntryPoint;
import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

@AndroidEntryPoint
public class BookingFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    /////////////////////////////////////////////////////////////////////////////
    //Membervariables
    private BookingFragmentViewModel viewModel;
    private TextView date;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private RecyclerView mKorbRecyclerView;

    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    //////////////////////////////////////////////////////////////////////////////
    //Lifecyclemethods

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booking, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        viewModel = new ViewModelProvider(this).get(BookingFragmentViewModel.class);
        binding.setVariable(BR.viewmodel, viewModel);
        mMapView = binding.getRoot().findViewById(R.id.korb_map);
        initGoogleMap(savedInstanceState);
        return binding.getRoot();*/
        return inflater.inflate(R.layout.fragment_booking_new,container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.korb_map);
        initGoogleMap(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookingFragmentViewModel.class);

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Override Methods
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap = map;
        mGoogleMap.setMyLocationEnabled(true);
        viewModel.getAllBeachchairs().observe(getViewLifecycleOwner(), this::onChanged);
        viewModel.getmSelectedBeachchair().observe(getViewLifecycleOwner(),this::onSelectedBeachchairChanged);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Helper Methods
    private void initSpinnerAndCalendar() {
        /*LocalDateTime today = LocalDateTime.now();

        date = getView().findViewById(R.id.editTextDate);
        date.setText(DateTimeFormatter.ofPattern("dd.LLL.", Locale.GERMANY).format(today));
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds())).build();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(requireActivity().getSupportFragmentManager(), "Matterial Range");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        date.setText(datePicker.getHeaderText());
                    }
                });
            }
        });

         */
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        viewModel.setSelectedBeachchair(Integer.valueOf(marker.getTitle()));
        return false;
    }

    private void onChanged(List<Korb> beachchairs) {
        mMapView.onResume();

        ListIterator<Korb> listIterator = beachchairs.listIterator();
        while (listIterator.hasNext() && mGoogleMap != null) {
            Korb korb = listIterator.next();
            if (korb.getLongitude() != 0 && korb.getLatitude() != 0) {
                LatLng position = new LatLng(korb.getLatitude(), korb.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createMarker(korb)));
                markerOptions.position(position);
                markerOptions.title(String.valueOf(korb.getNumber()));
                mGoogleMap.addMarker(markerOptions);
                Log.d("DataBindingAdapter", String.valueOf(listIterator.nextIndex()));
                //if last element in list position Camera
                if (listIterator.nextIndex() == viewModel.getAllBeachchairs().getValue().size()) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 21); //if only grey Tiles displayed reduce zoom
                    mGoogleMap.moveCamera(cameraUpdate);
                    //googleMap.animateCamera(cameraUpdate);
                    mGoogleMap.setOnMarkerClickListener(this);
                }
            }
        }

    }

    private void onSelectedBeachchairChanged(Korb korb) {
        if(korb != null)
            Toast.makeText(getContext(), korb.getType() + String.valueOf(korb.getNumber()), Toast.LENGTH_SHORT).show();
    }

    private Bitmap createMarker(Korb korb) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(80,80,conf);
        Canvas canvas1 = new Canvas(bitmap);
        Paint color = new Paint();
        color.setTextSize(45);
        switch (korb.getType()) {
            case "Normal":
                color.setColor(Color.RED);
                break;
            case "XL":
                color.setColor(Color.BLUE);
                break;
            case "XXL":
                color.setColor(Color.GREEN);
                break;
        }
        canvas1.drawText(String.valueOf(korb.getNumber()), 30,40,color);
        return bitmap;
    }
}
