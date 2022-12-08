package fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstone2.R;
import com.example.capstone2.databinding.FragmentWasteDrugInfoBinding;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WasteDrugInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WasteDrugInfoFragment extends Fragment {

    private FragmentWasteDrugInfoBinding binding;

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WasteDrugInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WasteDrugInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WasteDrugInfoFragment newInstance(String param1, String param2) {
        WasteDrugInfoFragment fragment = new WasteDrugInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        viewBinding();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_waste_drug_info, container, false);

        net.daum.mf.map.api.MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);

        mapViewContainer.addView(mapView);

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(36.3382, 127.4458), true);

        mapView.setMapViewEventListener((MapView.MapViewEventListener) getActivity());
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);

        MapPOIItem marker1 = new MapPOIItem();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(36.3365, 127.4471);
        marker1.setItemName("봄약국");
        marker1.setTag(0);
        marker1.setMapPoint(mapPoint);
        marker1.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker1);

        return view;
    }

    public View viewBinding(){
        binding = FragmentWasteDrugInfoBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

}