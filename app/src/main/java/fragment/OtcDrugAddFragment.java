package fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone2.MainActivity;
import com.example.capstone2.R;
import com.example.capstone2.databinding.ActivityMainBinding;
import com.example.capstone2.databinding.FragmentOtcDrugAddBinding;
import com.example.capstone2.databinding.FragmentOtcDrugManagementBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtcDrugAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtcDrugAddFragment extends Fragment {
    private FragmentOtcDrugAddBinding binding;
    private View view;
    private String[] otcReasonList;
    private ArrayAdapter<String> adapter;

    EditText otcAddName = view.findViewById(R.id.otc_drug_add_name);
    Spinner otcAddReason = view.findViewById(R.id.otc_drug_add_reason);
    TextView otcAddReasonView = view.findViewById(R.id.otc_drug_add_reason_textview);
    DatePicker otcAddDate = view.findViewById(R.id.otc_drug_add_date);
    Button otcAddSave = view.findViewById(R.id.otc_drug_add_save);
    Button otcAddCancel = view.findViewById(R.id.otc_drug_add_cancel);

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference otcName = database.getReference("일반의약품_의약품명");
    DatabaseReference otcReason = database.getReference("일반의약품_복용_이유");
    DatabaseReference otcDate = database.getReference("일반의약품_복용_날짜");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OtcDrugAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtcDrugAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtcDrugAddFragment newInstance(String param1, String param2) {
        OtcDrugAddFragment fragment = new OtcDrugAddFragment();
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
        initialize();
        setAdapter();
        spinnerSelect();
    }

    @SuppressLint("ResourceType")
    private  void initialize(){     //초기화
        otcReasonList = getResources().getStringArray(R.array.otc_reason_array);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, otcReasonList);
    }

    private void setAdapter(){      //adapter set
        otcAddReason.setAdapter(adapter);
    }

    private void spinnerSelect(){
        otcAddReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                otcAddReasonView.setText(otcReasonList[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otc_drug_add, container, false);

        //otcAddName 클릭 시 빈칸으로 바뀜
        otcAddName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otcAddName.setText(null);
            }
        });

        //저장버튼
        otcAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파이어베이스에 의약품명 작성
                otcName.child("일반의약품_의약품명").setValue(otcAddName.getText().toString());

                //Toast 메시지로 저장이 완료되었다는 것을 알려줌
                Toast.makeText(getActivity(), "저장되었습니다.", Toast.LENGTH_LONG).show();
                otcAddName.setText(null);
            }
        });

        //취소버튼
        otcAddCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    public View viewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentOtcDrugAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}