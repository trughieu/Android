package com.example.sic.Activity.Setting_Help.Setting_Detail;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Adapter.Adapter_History;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.modle.History;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Record;
import vn.mobileid.tse.model.connector.plugin.RecordRes;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.connector.plugin.SearchConditions;
import vn.mobileid.tse.model.database.ActivationData;

public class Activity_Action_History extends DefaultActivity {
    final Calendar myCalendar = Calendar.getInstance();
    TextView date_from;
    TextView date_to;
    TextView search;
    TextView txt_select_id, Login, ChangePassword, ForgotPassword, ChangeEmail, All;
    CertificateProfilesModule module;
    String[] action = new String[1];
    Date date_f, date_t, date_new, date;
    FrameLayout btnBack;
    Adapter_History adapter_history;
    History history;
    ArrayList<History> historyArrayList = new ArrayList<>();

    RecyclerView recyclerView;
    String startDay, endDay;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_history);
        start();
        date_from = findViewById(R.id.date_from);
        date_to = findViewById(R.id.date_to);
        search = findViewById(R.id.search);
        btnBack = findViewById(R.id.btnBack);
        txt_select_id = findViewById(R.id.txt_select_id);
        recyclerView = findViewById(R.id.rcHitory);
        prepareStartDay(date_from, true);
        prepareStartDay(date_to, false);

        SimpleDateFormat input = new SimpleDateFormat("yyyyMMddHHmmss");
        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


        btnBack.setOnClickListener(view -> {
            finish();
        });
        txt_select_id.setOnClickListener(view -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Action_History.this, R.style.BottomSheetDialogTheme);

            View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(R.layout.bottom_action_history, findViewById(R.id.bottom_layout_action_history));
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
            Login = bottomSheetView.findViewById(R.id.login);
            ChangePassword = bottomSheetView.findViewById(R.id.change_password);
            ChangeEmail = bottomSheetView.findViewById(R.id.change_email);
            ForgotPassword = bottomSheetView.findViewById(R.id.forgot_password);
            All = bottomSheetView.findViewById(R.id.all);

            Login.setOnClickListener(view1 -> {
                s = Login.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();
            });

            ChangePassword.setOnClickListener(view1 -> {
                s = ChangePassword.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();

            });
            ChangeEmail.setOnClickListener(view1 -> {
                s = ChangeEmail.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();

            });
            ForgotPassword.setOnClickListener(view1 -> {
                s = ForgotPassword.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();

            });
            All.setOnClickListener(view1 -> {
                s = All.getText().toString();
                txt_select_id.setText(s);
                bottomSheetDialog.dismiss();

            });
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        module = CertificateProfilesModule.createModule(this);


        SearchConditions conditions = new SearchConditions();
        conditions.fromDate = startDay;
        conditions.toDate = endDay;
        conditions.actions = action;
        String text = txt_select_id.getText().toString();
        action[0] = text.toUpperCase();
        Record record = new Record();
        record.setAllTrue();
        historyArrayList.clear();
        module.setResponseOwnerHistory(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                    List<RecordRes> records = response.getRecords();
                    if (response.getRecords() != null) {
                        for (RecordRes recordRes : records) {
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
                                    adapter_history = new Adapter_History(Activity_Action_History.this, historyArrayList);
                                    recyclerView.setAdapter(adapter_history);
                                    recyclerView.getRecycledViewPool().clear();
                                    adapter_history.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                    if(response.getRecords().size()==0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                            }
                        });
                    }
            }
        }).ownerHistory(ActivationData.getUsername(this), 1, 15, conditions, record);


        search.setOnClickListener(view -> {
            start();
            historyArrayList.clear();
            adapter_history.notifyDataSetChanged();
            conditions.fromDate = startDay;
            conditions.toDate = endDay;
            conditions.actions = action;
            action[0] = txt_select_id.getText().toString().toUpperCase();
            module.setResponseOwnerHistory(new HttpRequest.AsyncResponse() {
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
                                    adapter_history = new Adapter_History(Activity_Action_History.this, historyArrayList);
                                    recyclerView.setAdapter(adapter_history);
                                    recyclerView.getRecycledViewPool().clear();
                                    adapter_history.notifyDataSetChanged();

                                }
                            });

                        }
                    }
                    else {
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
            }).ownerHistory(ActivationData.getUsername(this), 1, 15, conditions, record);

        });

    }

    private void prepareStartDay(TextView textView, boolean start) {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Action_History.this, dateSetListener,
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