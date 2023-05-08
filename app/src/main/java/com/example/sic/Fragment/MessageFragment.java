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
import com.example.sic.model.Message;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rc_message;
    ArrayList<Message> arrayList;
    Adapter_item adapter_item;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rc_message = view.findViewById(R.id.rc_message);
        arrayList = init();

        adapter_item = new Adapter_item(arrayList, getContext());
        rc_message.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rc_message.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        rc_message.setAdapter(adapter_item);

    }


    public ArrayList<Message> init() {
        ArrayList<Message> tmp = new ArrayList<>();
        tmp.add(new Message("Signing Tester", "Submitted from Signing Tester", "21/11/2022"));
        tmp.add(new Message("Signing Tester", "Submitted from Signing Tester", "21/11/2022"));
        tmp.add(new Message("Signing Tester", "Submitted from Signing Tester", "21/11/2022"));
        tmp.add(new Message("Signing Tester", "Submitted from Signing Tester", "21/11/2022"));
        tmp.add(new Message("Signing Tester", "Submitted from Signing Tester", "21/11/2022"));
        tmp.add(new Message("Signing Tester", "Submitted from Signing Tester", "21/11/2022"));
        return tmp;
    }


}