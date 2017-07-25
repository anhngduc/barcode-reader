package com.google.android.gms.samples.vision.barcodereader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<ProductPack> {
    public MyAdapter(Context context, ArrayList<ProductPack> productList) {
        super(context, 0, productList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ProductPack product = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }
        // Lookup view for data population
        TextView tvInv = (TextView) convertView.findViewById(R.id.Invoiceid);
        TextView tvPc = (TextView) convertView.findViewById(R.id.productCode);
        TextView tvDate = (TextView) convertView.findViewById(R.id.datetv);
        TextView tvPl = (TextView) convertView.findViewById(R.id.productLot);
        TextView tvQ= (TextView) convertView.findViewById(R.id.qty);
        // Populate the data into the template view using the data object
        tvInv.setText(product.invoiceid);
        tvPc.setText(product.ProductCode);
        tvDate.setText(product.Date);
        tvPl.setText(product.ProductLot);
        tvQ.setText(product.Quantity);

        // Return the completed view to render on screen
        return convertView;
    }
}