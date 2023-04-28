package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Adapter.Adapter_History;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.modle.History;
import com.example.sic.modle.Manage_Certificate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Record;
import vn.mobileid.tse.model.connector.plugin.RecordRes;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.connector.plugin.SearchConditions;

public class Activity_Manage_Certificate_GoHistory extends DefaultActivity {


    Adapter_History adapter_history;
    ArrayList<History> arrayList;
    Date date;
    RecyclerView recyclerView;
    String startDay, endDay;
    String s;
    CertificateProfilesModule module;
    String[] action = new String[1];
    TextView date_from, date_to, search, txt_select_id, All, Issue, Renew, ChangeInformation, Reissue, Revoke,
            Authorize, ExtendTransaction, SignHash, SignDocument, ChangePassphrase, ForgotPassphrase, ChangeEmail, ChangePhoneNumber, title;
    String credentialId;
    History history;
    ArrayList<History> historyArrayList = new ArrayList<>();
    FrameLayout btnBack;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_go_history);

        recyclerView = findViewById(R.id.credential_history);
        credentialId = getIntent().getStringExtra("id");
        date_from = findViewById(R.id.date_from);
        date_to = findViewById(R.id.date_to);
        search = findViewById(R.id.search);
        btnBack = findViewById(R.id.btnBack);
        txt_select_id = findViewById(R.id.txt_select_id);
        title = findViewById(R.id.title);
        Manage_Certificate manage_certificate = (Manage_Certificate) getIntent().getSerializableExtra("certificate");
        title.setText(manage_certificate.getCNSubjectDN());
        btnBack.setOnClickListener(view -> {
            finish();
        });

        txt_select_id.setOnClickListener(view -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Manage_Certificate_GoHistory.this, R.style.BottomSheetDialogTheme);

            View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(R.layout.bottom_credential_history, findViewById(R.id.bottom_layout_credential_history));
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();


            All = bottomSheetView.findViewById(R.id.all);
            Issue = bottomSheetView.findViewById(R.id.issue);
            Renew = bottomSheetView.findViewById(R.id.renew);
            ChangeInformation = bottomSheetView.findViewById(R.id.changeInformation);
            Reissue = bottomSheetView.findViewById(R.id.reissue);
            Revoke = bottomSheetView.findViewById(R.id.revoke);
            Authorize = bottomSheetView.findViewById(R.id.authorize);
            ExtendTransaction = bottomSheetView.findViewById(R.id.extendTransaction);
            SignHash = bottomSheetView.findViewById(R.id.signHash);
            SignDocument = bottomSheetView.findViewById(R.id.signDocument);
            ChangePassphrase = bottomSheetView.findViewById(R.id.changePassphrase);
            ForgotPassphrase = bottomSheetView.findViewById(R.id.forgotPassphrase);
            ChangeEmail = bottomSheetView.findViewById(R.id.changeEmail);
            ChangePhoneNumber = bottomSheetView.findViewById(R.id.changePhoneNumber);


            ForgotPassphrase.setOnClickListener(view1 -> {
                s = ForgotPassphrase.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            ChangeEmail.setOnClickListener(view1 -> {
                s = ChangeEmail.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            ChangePhoneNumber.setOnClickListener(view1 -> {
                s = ChangePhoneNumber.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            Issue.setOnClickListener(view1 -> {
                s = Issue.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });

            All.setOnClickListener(view1 -> {
                s = All.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });

            Renew.setOnClickListener(view1 -> {
                s = Renew.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            ChangeInformation.setOnClickListener(view1 -> {
                s = ChangeInformation.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            Reissue.setOnClickListener(view1 -> {
                s = Reissue.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            Revoke.setOnClickListener(view1 -> {
                s = Revoke.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            Authorize.setOnClickListener(view1 -> {
                s = Authorize.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            ExtendTransaction.setOnClickListener(view1 -> {
                s = ExtendTransaction.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            SignHash.setOnClickListener(view1 -> {
                s = SignHash.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            SignDocument.setOnClickListener(view1 -> {
                s = SignDocument.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });
            ChangePassphrase.setOnClickListener(view1 -> {
                s = ChangePassphrase.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });

        });
        prepareStartDay(date_from, true);
        prepareStartDay(date_to, false);

        SimpleDateFormat input = new SimpleDateFormat("yyyyMMddHHmmss");
        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


        module = CertificateProfilesModule.createModule(this);
        SearchConditions conditions = new SearchConditions();
        conditions.fromDate = startDay;
        conditions.toDate = endDay;
        conditions.actions = action;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
        recyclerView.setAdapter(adapter_history);
        String text = txt_select_id.getText().toString();
        action[0] = text.toUpperCase();
        Record record = new Record();
        record.setAllTrue();
        historyArrayList.clear();
        module.setResponseCredentialsHistory(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                List<RecordRes> recordResList = response.getRecords();
                if (response.getRecords() != null) {
                    for (RecordRes recordRes : recordResList) {
                        String relying_party = recordRes.relyingParty;
                        String action = recordRes.action;
                        String state = recordRes.responseMessage;
                        String time = recordRes.createDt;

                        try {
                            date = input.parse(time);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        String time_action = output.format(date);

                        history = new History();
                        history.setTv_1(relying_party);
                        history.setTv_2(action);
                        history.setTv_3(state);
                        history.setTv_4(time_action);
                        historyArrayList.add(history);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                                adapter_history = new Adapter_History(Activity_Manage_Certificate_GoHistory.this, historyArrayList);
                                recyclerView.setAdapter(adapter_history);
                                recyclerView.getRecycledViewPool().clear();
                                adapter_history.notifyDataSetChanged();
                            }
                        });
                    }
                }


            }
        }).credentialsHistory(credentialId, 1, 20, conditions, record);

        search.setOnClickListener(view -> {
            start();
            historyArrayList.clear();
            conditions.fromDate = startDay;
            conditions.toDate = endDay;
            conditions.actions = action;
            action[0] = txt_select_id.getText().toString().toUpperCase();
            module.setResponseCredentialsHistory(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    List<RecordRes> records = response.getRecords();
                    if (response.getRecords() != null) {
                        for (RecordRes record : records) {
                            String relying_party = record.relyingParty;
                            String action = record.action;
                            String state = record.responseMessage;
                            String time = record.createDt;

                            try {
                                date = input.parse(time);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }

                            String time_action = output.format(date);
                            history = new History();
                            history.setTv_1(relying_party);
                            history.setTv_2(action);
                            history.setTv_3(state);
                            history.setTv_4(time_action);
                            historyArrayList.add(history);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    stop();
                                    adapter_history = new Adapter_History(Activity_Manage_Certificate_GoHistory.this, historyArrayList);
                                    recyclerView.setAdapter(adapter_history);
                                    recyclerView.getRecycledViewPool().clear();
                                    adapter_history.notifyDataSetChanged();

                                }
                            });

                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                                historyArrayList.clear();
                                recyclerView.getRecycledViewPool().clear();
                                adapter_history.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }).credentialsHistory(credentialId, 1, 100, conditions, record);

        });


    }


    private void prepareStartDay(TextView textView, boolean start) {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        if (start) {
            startDay = dateFormat.format(new Date(now.getYear(), now.getMonth(), now.getDate(), 0, 0, 0));
            textView.setText(getDateNow(now));
        } else {
            endDay = dateFormat.format(new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds()));
            textView.setText(getDateNow(now));
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Date now = new Date();
//                        Calendar calendar = Calendar.getInstance();
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//                        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//                        dateFormat.setCalendar(calendar);
                        textView.setText((new DecimalFormat("00")).format(dayOfMonth)
                                + "/" + ((new DecimalFormat("00")).format(monthOfYear + 1))
                                + "/" + year);
                        Date dateEndResult = new Date(year - 1900, monthOfYear, dayOfMonth, 23, 59, 59);
                        if (start) {
                            if (isItToDay(dateEndResult, now)) {
                                startDay = dateFormat.format(new Date(now.getYear(), now.getMonth(), now.getDate(), 0, 0, 0));
                                textView.setText(getDateNow(now));
                            } else {
                                startDay = dateFormat.format(new Date(year - 1900, monthOfYear, dayOfMonth, 0, 0, 0));
                            }
                        } else {
                            if (isItToDay(dateEndResult, now)) {
                                endDay = dateFormat.format(new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds()));
                                textView.setText(getDateNow(now));
                            } else {
                                endDay = dateFormat.format(dateEndResult);
                            }
                        }
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Manage_Certificate_GoHistory.this, dateSetListener,
                        (now.getYear() + 1900),
                        now.getMonth(),
                        now.getDate());
                datePickerDialog.show();
            }
        });
    }

    private boolean isItToDay(Date dateEndResult, Date now) {
        if (dateEndResult.getYear() >= now.getYear() && dateEndResult.getMonth() >= now.getMonth() && dateEndResult.getDate() >= now.getDate()) {
            return true;
        }
        return false;
    }

    private String getDateNow(Date now) {
        return (new DecimalFormat("00")).format(now.getDate())
                + "/" + ((new DecimalFormat("00")).format(now.getMonth() + 1))
                + "/" + String.valueOf(now.getYear() + 1900);
    }
}