package com.example.philoniare.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.philoniare.inventoryapp.model.Product;

import java.util.Locale;

import io.realm.Realm;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView productQuantityTV;
    private TextView productPriceTV;
    private Realm realm;
    private Product managedCurrentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        productQuantityTV = (TextView) findViewById(R.id.product_quantity);
        productPriceTV = (TextView) findViewById(R.id.product_price);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get passed bundle data
        Bundle arguments = getIntent().getExtras();
        String productName = arguments.getString("productName");
        int productQuantity = arguments.getInt("productQuantity");
        Double productPrice = arguments.getDouble("productPrice");
        getSupportActionBar().setTitle(productName);
        updateQuantity(productQuantity);
        productPriceTV.setText(String.format(Locale.ENGLISH,
                getString(R.string.product_price_formatter), productPrice));

        // Get the current product from the DB
        realm = Realm.getDefaultInstance();
        managedCurrentProduct = realm.where(Product.class).equalTo("name", productName)
                .findFirst();

        Button increaseQuantityBtn = (Button) findViewById(R.id.increase_quantity);
        Button decreaseQuantityBtn = (Button) findViewById(R.id.decrease_quantity);
        Button orderMoreBtn = (Button) findViewById(R.id.product_order_more);
        Button deleteBtn = (Button) findViewById(R.id.product_delete);
        increaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = managedCurrentProduct.getQuantity() + 1;
                realm.beginTransaction();
                managedCurrentProduct.setQuantity(newQuantity);
                realm.commitTransaction();
                // Apply changes to the UI
                updateQuantity(newQuantity);
            }
        });
        decreaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = managedCurrentProduct.getQuantity() - 1;
                if(newQuantity >= 0) {
                    realm.beginTransaction();
                    managedCurrentProduct.setQuantity(newQuantity);
                    realm.commitTransaction();
                    // Apply changes to the UI
                    updateQuantity(newQuantity);
                }
            }
        });
        orderMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "tel:3334444";
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(phoneIntent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ask for confirmation first
                new AlertDialog.Builder(getApplicationContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.delete_dialog_title))
                        .setMessage(String.format(Locale.ENGLISH, getString(R.string.delete_product_formatter),
                                managedCurrentProduct.getName()))
                        .setPositiveButton(getString(R.string.dialog_answer_yes), new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                realm.beginTransaction();
                                managedCurrentProduct.deleteFromRealm();
                                realm.commitTransaction();
                                finish();
                            }

                        })
                        .setNegativeButton(getString(R.string.dialog_answer_no), null)
                        .show();
            }
        });
    }

    private void updateQuantity(int productQuantity) {
        productQuantityTV.setText(String.format(Locale.ENGLISH,
                getString(R.string.product_quantity_formatter), productQuantity));
    }
}
