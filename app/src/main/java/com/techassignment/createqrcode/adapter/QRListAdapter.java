package com.techassignment.createqrcode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techassignment.createqrcode.R;
import com.techassignment.createqrcode.model.QRCode;

import java.util.List;

/**
 * Created by creson on 1/30/15.
 */
public class QRListAdapter extends RecyclerView.Adapter<QRListAdapter.ViewHolder> {

    private Context context;
    private List<QRCode> qrCodes;

//    private ImageLoader imageLoader;

    public QRListAdapter(Context context, List<QRCode> qrCodes) {
        this.context = context;
        this.qrCodes = qrCodes;

        /*
        * For Universal ImageLoader
        * */
//        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.qr_list_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        QRCode qrCode = qrCodes.get(i);

        viewHolder.qrData.setText(qrCode.getData());

        /*
        * Used Universal ImageLoader at the beginning, but whenever
         * a new data is added to the list, the image of all items in the list changed
         * and also the QRCode didn't match the data. So, I decided to use Picasso at the moment.
        * */
//        imageLoader.displayImage(qrCode.getUrl(), viewHolder.qrImage);
        Picasso.with(context).load(qrCode.getUrl()).into(viewHolder.qrImage);
    }

    public void refreshList(List<QRCode> qrCodes) {
        this.qrCodes = qrCodes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return qrCodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView qrImage;
        TextView qrData;

        public ViewHolder(View itemView) {
            super(itemView);
            qrImage = (ImageView) itemView.findViewById(R.id.qr_img);
            qrData = (TextView) itemView.findViewById(R.id.qr_data);
        }
    }
}
