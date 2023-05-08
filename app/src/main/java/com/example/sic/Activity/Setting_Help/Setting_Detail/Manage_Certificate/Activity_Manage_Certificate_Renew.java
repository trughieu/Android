package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.model.Manage_Certificate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Activity_Manage_Certificate_Renew extends DefaultActivity {
    TextView txt_select_id_certificate_autho, txt_select_id_certificate_profile, txt_select_id_signing_counter, title;
    TextView mobile_id, mobile_id_1, mobile_id_2, mobile_id_3, mobile_id_4, mobile_id_5,
            mobile_id_6, mobile_id_7, mobile_id_8, mobile_id_9,
            one_year, two_year, third_year, unlimited, tenk_signed_profile,
            ten_signed_profile, five_signed_profiled;

    String s;

    FrameLayout btnBack;
    TextView btnContinue;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_renew);

        txt_select_id_certificate_profile = findViewById(R.id.txt_select_id_certificate_profile);
        txt_select_id_certificate_autho = findViewById(R.id.txt_select_id_certificate_autho);
        txt_select_id_signing_counter = findViewById(R.id.txt_select_id_signing_counter);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        title=findViewById(R.id.title);
        btnBack.setOnClickListener(view -> {
            Intent intent= new Intent(Activity_Manage_Certificate_Renew.this, Activity_Manage_Certificate.class);
           startActivity(intent);
                finish();
        });
        btnContinue.setOnClickListener(view -> {
            Intent intent= new Intent(Activity_Manage_Certificate_Renew.this, Activity_Manage_Certificate_Renew_Check.class);
           startActivity(intent);
                finish();

        });

        Manage_Certificate manage_certificate= (Manage_Certificate) getIntent().getSerializableExtra("certificate");
        title.setText(manage_certificate.getCNSubjectDN());
        txt_select_id_certificate_autho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Activity_Manage_Certificate_Renew.this, R.style.BottomSheetDialogTheme);

                View bottomsheet_autho = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.bottom_sheet_layout_manage_certificate_renew_certificate_autho,
                                findViewById(R.id.bottom_sheet_certificate_autho));

                mobile_id = bottomsheet_autho.findViewById(R.id.mobile_id);
                mobile_id_1 = bottomsheet_autho.findViewById(R.id.mobile_id_1);
                mobile_id_2 = bottomsheet_autho.findViewById(R.id.mobile_id_2);
                mobile_id_3 = bottomsheet_autho.findViewById(R.id.mobile_id_3);
                mobile_id_4 = bottomsheet_autho.findViewById(R.id.mobile_id_4);
                mobile_id_5 = bottomsheet_autho.findViewById(R.id.mobile_id_5);
                mobile_id_6 = bottomsheet_autho.findViewById(R.id.mobile_id_6);
                mobile_id_7 = bottomsheet_autho.findViewById(R.id.mobile_id_7);
                mobile_id_8 = bottomsheet_autho.findViewById(R.id.mobile_id_8);
                mobile_id_9 = bottomsheet_autho.findViewById(R.id.mobile_id_9);

                mobile_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                mobile_id_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id_1.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                mobile_id_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id_2.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                mobile_id_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id_3.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                mobile_id_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id_4.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                mobile_id_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id_5.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                mobile_id_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id_6.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                mobile_id_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id_7.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                mobile_id_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id_8.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                mobile_id_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = mobile_id_9.getText().toString();
                        txt_select_id_certificate_autho.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomsheet_autho);
                bottomSheetDialog.show();
            }
        });

        txt_select_id_certificate_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Activity_Manage_Certificate_Renew.this, R.style.BottomSheetDialogTheme);

                View bottomsheet_profile = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.bottom_sheet_layout_manage_certificate_renew_certificate_profile,
                                findViewById(R.id.bottom_sheet_certificate_profile));

                one_year = bottomsheet_profile.findViewById(R.id.one_year);
                two_year = bottomsheet_profile.findViewById(R.id.two_year);
                third_year = bottomsheet_profile.findViewById(R.id.third_year);

                one_year.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = one_year.getText().toString();
                        txt_select_id_certificate_profile.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                two_year.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = two_year.getText().toString();
                        txt_select_id_certificate_profile.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                third_year.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = third_year.getText().toString();
                        txt_select_id_certificate_profile.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomsheet_profile);
                bottomSheetDialog.show();
            }
        });
        txt_select_id_signing_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Activity_Manage_Certificate_Renew.this, R.style.BottomSheetDialogTheme);

                View bottomsheet_counter = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.bottom_sheet_layout_manage_certificate_renew_signing_counter,
                                findViewById(R.id.bottom_sheet_signing_counter));

                unlimited = bottomsheet_counter.findViewById(R.id.unlimited);

                tenk_signed_profile = bottomsheet_counter.findViewById(R.id.tenk_signed_profile);
                ten_signed_profile = bottomsheet_counter.findViewById(R.id.ten_signed_profile);
                five_signed_profiled = bottomsheet_counter.findViewById(R.id.five_signed_profile);

                unlimited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = unlimited.getText().toString();
                        txt_select_id_signing_counter.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                ten_signed_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = ten_signed_profile.getText().toString();
                        txt_select_id_signing_counter.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                tenk_signed_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tenk_signed_profile.getText().toString();
                        txt_select_id_signing_counter.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                five_signed_profiled.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = five_signed_profiled.getText().toString();
                        txt_select_id_signing_counter.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });


                bottomSheetDialog.setContentView(bottomsheet_counter);
                bottomSheetDialog.show();
            }
        });


/**
 LinearLayout llBottomSheet_autho = findViewById(R.id.bottom_sheet_certificate_autho);
 LinearLayout llBottomSheet_profile = findViewById(R.id.bottom_sheet_certificate_profile);
 LinearLayout llBottomSheet_signing_counter = findViewById(R.id.bottom_sheet_signing_counter);
 BottomSheetBehavior bottomSheetBehavior_autho = BottomSheetBehavior.from(llBottomSheet_autho);
 BottomSheetBehavior bottomSheetBehavior_profile = BottomSheetBehavior.from(llBottomSheet_profile);
 BottomSheetBehavior bottomSheetBehavior_signing_counter = BottomSheetBehavior.from(llBottomSheet_signing_counter);
 /** Profile
 txt_select_id_certificate_profile.setOnClickListener(new View.OnClickListener() {
@Override public void onClick(View view) {
if (bottomSheetBehavior_profile.getState() != BottomSheetBehavior.STATE_EXPANDED) {

bottomSheetBehavior_autho.setState(BottomSheetBehavior.STATE_HIDDEN);
bottomSheetBehavior_signing_counter.setState(BottomSheetBehavior.STATE_HIDDEN);
bottomSheetBehavior_profile.setState(BottomSheetBehavior.STATE_EXPANDED);


//                bottomSheetBehavior.setPeekHeight(200);
} else bottomSheetBehavior_profile.setState(BottomSheetBehavior.STATE_HIDDEN);

// set the peek height
bottomSheetBehavior_profile.setPeekHeight(0);

// set hideable or not
bottomSheetBehavior_profile.setHideable(true);
}
});

 /**            Autho
 txt_select_id_certificate_autho.setOnClickListener(new View.OnClickListener() {
@Override public void onClick(View view) {
if (bottomSheetBehavior_autho.getState() != BottomSheetBehavior.STATE_EXPANDED) {
bottomSheetBehavior_signing_counter.setState(BottomSheetBehavior.STATE_HIDDEN);
bottomSheetBehavior_profile.setState(BottomSheetBehavior.STATE_HIDDEN);
bottomSheetBehavior_autho.setState(BottomSheetBehavior.STATE_EXPANDED);


//                bottomSheetBehavior.setPeekHeight(200);
} else
bottomSheetBehavior_autho.setState(BottomSheetBehavior.STATE_HIDDEN);

// set the peek height
bottomSheetBehavior_autho.setPeekHeight(0);

// set hideable or not
bottomSheetBehavior_autho.setHideable(true);

}
});

 /**           Singing Counter
 txt_select_id_signing_counter.setOnClickListener(new View.OnClickListener() {
@Override public void onClick(View view) {
if (bottomSheetBehavior_signing_counter.getState() != BottomSheetBehavior.STATE_EXPANDED) {
bottomSheetBehavior_profile.setState(BottomSheetBehavior.STATE_HIDDEN);
bottomSheetBehavior_autho.setState(BottomSheetBehavior.STATE_HIDDEN);
bottomSheetBehavior_signing_counter.setState(BottomSheetBehavior.STATE_EXPANDED);

//                bottomSheetBehavior.setPeekHeight(200);
} else
bottomSheetBehavior_signing_counter.setState(BottomSheetBehavior.STATE_HIDDEN);

// set the peek height
bottomSheetBehavior_signing_counter.setPeekHeight(0);

// set hideable or not
bottomSheetBehavior_signing_counter.setHideable(true);

}
});*/
    }
}
