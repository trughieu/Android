package com.example.sic.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Adapter.Adapter_item;
import com.example.sic.R;
import com.example.sic.modle.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.requestinfo.RequestInfoModule;
import vn.mobileid.tse.model.connector.plugin.Requests;
import vn.mobileid.tse.model.connector.plugin.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PendingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rc_pending;
    ArrayList<Message> messageArrayList = new ArrayList<>();
    Adapter_item adapter_item;
    RequestInfoModule module;
    Date date;
    Message message;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PendingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PendingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PendingFragment newInstance(String param1, String param2) {
        PendingFragment fragment = new PendingFragment();
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
        return inflater.inflate(R.layout.fragment_pending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rc_pending = view.findViewById(R.id.rc_pending);

        module = RequestInfoModule.createModule(getActivity());
        SimpleDateFormat input = new SimpleDateFormat("yyyyMMddHHmmss");
        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        module.setResponseGetTransactionsList(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                List<Requests> requests = response.getRequests();

                if (response.getRequests() != null) {
                    for (Requests request : requests) {
                        String messageCaption = request.messageCaption;
                        String scaIdentity = request.scaIdentity;
                        String createdDt = request.createdDt;
                        String transactionID = request.transactionID;
                        try {
                            date = input.parse(createdDt);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        String time_out = output.format(date);
                        message = new Message();
                        message.setMessageCaption(messageCaption);
                        message.setScaIdentity(scaIdentity);
                        message.setCreatedDt(time_out);
                        message.setTransactionId(transactionID);
                        messageArrayList.add(message);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter_item = new Adapter_item(messageArrayList, getContext());
                                rc_pending.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                rc_pending.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
                                rc_pending.setAdapter(adapter_item);
                                rc_pending.getRecycledViewPool().clear();
                                adapter_item.notifyDataSetChanged();
                            }
                        });
                    }
                }

            }
        }).transactionsList();


    }
}