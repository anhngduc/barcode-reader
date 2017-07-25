package com.google.android.gms.samples.vision.barcodereader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nguyen on 2017-07-24.
 */

public class ProductPack {
    public String ProductCode;
    public String Barcode;
    public String ProductLot;
    public String ProductName;
    public String Date;
    public String Quantity;
    public String invoiceid;

    public ProductPack(String invoiceid,String Date)    {
        this.invoiceid= invoiceid;
        this.Date = Date;
    }
    public ProductPack(JSONObject object){
        try {
            this.ProductCode = object.getString("ProductCode");
            this.Barcode = object.getString("Barcode");
            this.ProductLot = object.getString("ProductLot");
            this.ProductName = object.getString("ProductName");
            this.Date = object.getString("Date");
            this.Quantity = object.getString("Quantity");
            this.invoiceid = object.getString("invoiceid");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // User.fromJson(jsonArray);
    public static ArrayList<ProductPack> fromJson(JSONArray jsonObjects) {
        ArrayList<ProductPack> productlist = new ArrayList<ProductPack>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                productlist.add(new ProductPack(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return productlist;
    }

    /*
    map.put("id",  String.valueOf(i));
                map.put("ProductCode", "ProductCode:" + e.getString("ProductCode"));
                map.put("Barcode", "Barcode:" + e.getString("Barcode"));
                map.put("ProductLot", "ProductLot:" + e.getString("ProductLot"));
                map.put("ProductName", "ProductName:" + e.getString("ProductName"));
                map.put("Date", "Date:" + e.getString("Date"));
                map.put("Quantity", "Quantity:" + e.getString("Quantity"));
                map.put("invoiceid", "invoiceid:" + e.getString("invoiceid"));
                mylist.add(map);
                */
}
