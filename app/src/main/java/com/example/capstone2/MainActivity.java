package com.example.capstone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import com.example.capstone2.databinding.ActivityMainBinding;

import fragment.MedicineCalendarFragment;
import fragment.OtcDrugAddFragment;
import fragment.OtcDrugManagementFragment;
import fragment.PrescriptionAddFragment;
import fragment.PrescriptionManagementFragment;
import fragment.WasteDrugInfoFragment;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private  static final String  TAG="MainActivity";
    private ActivityMainBinding binding;
    private Fragment MedicineCalendarFragment;
    private Fragment OtcDrugManagementFragment;
    private Fragment PrescriptionManagementFragment;
    private Fragment WasteDrugInfoFragment;
    private Fragment OtcDrugAddFragment;
    private Fragment PrescriptionAddFragment;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewBinding();
        initialize();
        bottomNavigationSelect();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            myStartActivity(SignUpActivity.class);
        }else{
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document !=null){
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                myStartActivity(MemberInitActivity.class);
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

        findViewById(R.id.logout_button).setOnClickListener(onClickListener);

        OtcDrugAddFragment = new OtcDrugAddFragment();
        getSupportFragmentManager().findFragmentById(R.id.otc_add_btn);

        PrescriptionAddFragment = new PrescriptionAddFragment();
        getSupportFragmentManager().findFragmentById(R.id.pre_add_btn);

    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.logout_button:
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(SignUpActivity.class);
                    break;
            }
        }
    };

    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    //뷰바인딩
    private void viewBinding(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    //초기화
    private void initialize(){
        binding.bottomNav.setItemIconTintList(null);
        menu = binding.bottomNav.getMenu();

        MedicineCalendarFragment = new MedicineCalendarFragment();
        //첫화면 -> 여기 아마 손봐야할 것 같은데 감이 안잡혀용,,
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, MedicineCalendarFragment).commit();
//        menu.findItem(R.id.medicine_calendar_fragment).setIcon(R.drawable.icon_calendar);
    }

    private void bottomNavigationSelect(){
        binding.bottomNav.setOnItemSelectedListener(item -> {
            changeFragment(item);
            return true;
        });
    }

    //아이콘 클릭
    @SuppressLint("NonConstantResourceId")
    public void changeFragment(MenuItem item){
        switch (item.getItemId()){
            case R.id.medicine_calendar_fragment:
                if(MedicineCalendarFragment == null){
                    MedicineCalendarFragment = new MedicineCalendarFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, MedicineCalendarFragment).commit();
                }
                screenChange(MedicineCalendarFragment, item);
                break;
            case R.id.otc_drug_management_fragment:
                if(OtcDrugManagementFragment == null){
                    OtcDrugManagementFragment = new OtcDrugManagementFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, OtcDrugManagementFragment).commit();
                }
                screenChange(OtcDrugManagementFragment, item);
                break;
            case R.id.prescription_management_fragment:
                if(PrescriptionManagementFragment == null){
                    PrescriptionManagementFragment = new PrescriptionManagementFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, PrescriptionManagementFragment).commit();
                }
                screenChange(PrescriptionManagementFragment, item);
                break;
            case R.id.waste_drug_info_fragment:
                if(WasteDrugInfoFragment == null){
                    WasteDrugInfoFragment = new WasteDrugInfoFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, WasteDrugInfoFragment).commit();
                }
                screenChange(WasteDrugInfoFragment, item);
                break;
            default:
                break;
        }
    }

    //화면 전환
    @SuppressLint("NonConstantResourceId")
    private void screenChange(Fragment fragment, MenuItem item){
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,fragment).commit();
        menu.findItem(R.id.medicine_calendar_fragment).setIcon(R.drawable.icon_calendar_white);
        menu.findItem(R.id.otc_drug_management_fragment).setIcon(R.drawable.icon_medicine1_white);
        menu.findItem(R.id.prescription_management_fragment).setIcon(R.drawable.icon_medicine2_white);
        menu.findItem(R.id.waste_drug_info_fragment).setIcon(R.drawable.icon_map_white);

        switch (item.getItemId()){
            case R.id.medicine_calendar_fragment:
                item.setIcon(R.drawable.icon_calendar);
                break;
            case R.id.otc_drug_management_fragment:
                item.setIcon(R.drawable.icon_medicine1);
                break;
            case R.id.prescription_management_fragment:
                item.setIcon(R.drawable.icon_medicine2);
                break;
            case R.id.waste_drug_info_fragment:
                item.setIcon(R.drawable.icon_map);
                break;
            default:
                break;
        }
    }
}