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
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.techassignment.createqrcode.R;
import com.techassignment.createqrcode.adapter.QRListAdapter;
import com.techassignment.createqrcode.listener.ItemClickSupport;
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

    private Handler handler = new Handler();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityContext = (ActionBarActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        setDatas(savedInstanceState);
        setAdapter();
        setListeners();

        if (savedInstanceState == null) {
            handler.postDelayed(backgroundRunnable, 10000);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("QRList", (java.io.Serializable) qrCodeList);
    }

    public void initializeLayout() {
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        qrRecyclerView = (RecyclerView) rootView.findViewById(R.id.qr_recycler_view);
        qrRecyclerView.addItemDecoration(new DividerItemDecoration(activityContext, null));
        qrRecyclerView.setLayoutManager(new LinearLayoutManager(activityContext));
        qrRecyclerView.setItemAnimator(new DefaultItemAnimator());

        floatingActionButton.attachToRecyclerView(qrRecyclerView);
    }

    public void setDatas(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            qrCodeList = QRCode.getInitialData();
        } else {
            qrCodeList = (List<QRCode>) savedInstanceState.getSerializable("QRList");
        }
    }

    public void setAdapter() {
        qrListAdapter = new QRListAdapter(activityContext, qrCodeList);
        qrRecyclerView.setAdapter(qrListAdapter);
    }

    public void setListeners() {

        final ItemClickSupport qrRecyclerViewItemClickSupport = ItemClickSupport.addTo(qrRecyclerView);
        qrRecyclerViewItemClickSupport.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View view, int position, long id) {
                return true;
            }
        });
        qrRecyclerViewItemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, final int position, long id) {

                final QRCode selectedQRCode = qrCodeList.get(position);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activityContext);

                LinearLayout layout = new LinearLayout(activityContext);
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(60, 20, 50, 20);

                final EditText qrEditText = new EditText(alertBuilder.getContext());
                qrEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                qrEditText.setHint("Enter Text");
                qrEditText.setText(selectedQRCode.getData());

                layout.addView(qrEditText, params);

                alertBuilder
                        .setView(layout)
                        .setTitle("Delete/Update QR Code")
                        .setPositiveButton("Update", new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateQRCode(position, qrEditText.getText().toString());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Delete", new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteQRCode(position);
                                dialog.dismiss();
                            }
                        });

                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });


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
                qrEditText.setInputType(InputType.TYPE_CLASS_TEXT);
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

        Toast.makeText(activityContext, "New QR Code has been added", Toast.LENGTH_SHORT).show();
    }

    public void updateQRCode(int position, String data) {
        QRCode updatedQRCode = new QRCode(data, QRCode.QR_CODE_URL + data);
        qrCodeList.set(position, updatedQRCode);
        qrListAdapter.refreshList(qrCodeList);

        Toast.makeText(activityContext, "Selected QR Code has been updated", Toast.LENGTH_SHORT).show();
    }

    public void deleteQRCode(int position) {
        qrCodeList.remove(position);
        qrListAdapter.refreshList(qrCodeList);

        Toast.makeText(activityContext, "Selected QR Code has been removed", Toast.LENGTH_SHORT).show();
    }

}
