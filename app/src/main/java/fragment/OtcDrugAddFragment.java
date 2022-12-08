package fragment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone2.MemberInfo;
import com.example.capstone2.R;
import com.example.capstone2.databinding.FragmentOtcDrugAddBinding;
import com.example.capstone2.otcDrugInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

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

    /**
     * 아래 쪽에 view 생성 이후에 지정하는 것으로 옮김
     */


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
//        initialize();
//        setAdapter();
//        spinnerSelect();
    }


    @SuppressLint("ResourceType")
    private  void initialize(){     //초기화
        otcReasonList = getResources().getStringArray(R.array.otc_reason_array);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, otcReasonList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otc_drug_add, container, false);

        /**
         * view 가 생긴 다음에 지정을 해야 오류가 나지 않음.
         */
        EditText otcAddName = view.findViewById(R.id.otc_drug_add_name);
        EditText otcAddReason = view.findViewById(R.id.otc_drug_add_reason);
        EditText otcAddDate = view.findViewById(R.id.otc_drug_add_date);
        Button otcAddSave = view.findViewById(R.id.otc_drug_add_save);
        Button otcAddCancel = view.findViewById(R.id.otc_drug_add_cancel);

        otcAddSave.setOnClickListener(onClickListener);

        //otcAddName 클릭 시 빈칸으로 바뀜
//        otcAddName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                otcAddName.setText(null);
//            }
//        });

        //저장버튼
//        otcAddSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                otcDrugUpdate();
//            }
//        });
//
//        //취소버튼
//        otcAddCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.otc_add_btn:
                    otcDrugUpdate();
                    break;
            }
        }
    };

    private void otcDrugUpdate() {
        String name = ((EditText) view.findViewById(R.id.otc_drug_add_name)).getText().toString();
        String reason = ((EditText) view.findViewById(R.id.otc_drug_add_reason)).getText().toString();
        String date = ((EditText) view.findViewById(R.id.otc_drug_add_date)).getText().toString();

        if(name.length() > 0 && reason. length() > 0 && date. length() > 0){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            otcDrugInfo otcDrugInfo = new otcDrugInfo(name,reason,date);

            if(user != null){
                db.collection("otcDrug").document(user.getUid()).set(otcDrugInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("일반의약품 정보 등록을 성공하였습니다.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("일반의약품 정보 등록에 실패하였습니다.");
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }
        }
        else{
            startToast("일반의약품 정보를 입력해주세요.");
        }
    }


    private void startToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public View viewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentOtcDrugAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}