package com.techassignment.createqrcode.ui.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.melnykov.fab.FloatingActionButton;
import com.techassignment.createqrcode.R;
import com.techassignment.createqrcode.adapter.QRListAdapter;
import com.techassignment.createqrcode.model.QRCode;
import com.techassignment.createqrcode.ui.tools.DividerItemDecoration;

import java.util.List;

/**
 * Created by creson on 1/29/15.
 */
public class QRListFragment extends Fragment {

    private View rootView;
    private RecyclerView qrRecyclerView;
    private FloatingActionButton floatingActionButton;

    private ActionBarActivity activityContext;

    private QRListAdapter qrListAdapter;

    private List<QRCode> qrCodeList;

    private Handler handler;
    private Runnable backgroundRunnable = new Runnable() {
        @Override
        public void run() {
            addQRCode(QRCode.getRandomQRCode());
            handler.postDelayed(backgroundRunnable, 10000);
        }
    };

    public static QRListFragment newInstance() {
        QRListFragment qrListFragment = new QRListFragment();
        Bundle bundle = new Bundle();
        qrListFragment.setArguments(bundle);
        return qrListFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityContext = (ActionBarActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();

        handler.postDelayed(backgroundRunnable, 10000);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.qr_list_fragment, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeLayout();
        setDatas();
        setAdapter();
        setListeners();
    }

    public void initializeLayout() {
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        qrRecyclerView = (RecyclerView) rootView.findViewById(R.id.qr_recycler_view);
        qrRecyclerView.addItemDecoration(new DividerItemDecoration(activityContext, null));
        qrRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        qrRecyclerView.setItemAnimator(new DefaultItemAnimator());

        floatingActionButton.attachToRecyclerView(qrRecyclerView);
    }

    public void setDatas() {
        qrCodeList = QRCode.getInitialData();
    }

    public void setAdapter() {
        qrListAdapter = new QRListAdapter(activityContext, qrCodeList);
        qrRecyclerView.setAdapter(qrListAdapter);
    }

    public void setListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activityContext);

                LinearLayout layout = new LinearLayout(activityContext);
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(60, 20, 50, 20);

                final EditText qrEditText = new EditText(alertBuilder.getContext());
                qrEditText.setHint("Enter Text");

                layout.addView(qrEditText, params);

                alertBuilder
                        .setView(layout)
                        .setTitle("Create new QR Code")
                        .setPositiveButton("Create", new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addQRCode(QRCode.createNewQRCode(qrEditText.getText().toString()));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }

    public void addQRCode(QRCode qrCode) {
        qrCodeList.add(qrCode);
        qrListAdapter.refreshList(qrCodeList);
    }
}
