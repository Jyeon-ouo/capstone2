package fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstone2.MainActivity;
import com.example.capstone2.R;
import com.example.capstone2.databinding.FragmentOtcDrugManagementBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtcDrugManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtcDrugManagementFragment extends Fragment {

    MainActivity mainActivity;
    private FragmentOtcDrugManagementBinding binding;
    private View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OtcDrugManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtcDrugManagementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtcDrugManagementFragment newInstance(String param1, String param2) {
        OtcDrugManagementFragment fragment = new OtcDrugManagementFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otc_drug_management, container, false);
        OtcDrugAddFragment otcDrugAddFragment = new OtcDrugAddFragment();
        Button addBtn = view.findViewById(R.id.otc_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, otcDrugAddFragment, "DETAIL")
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button deleteBtn = view.findViewById(R.id.otc_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public View viewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentOtcDrugManagementBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}