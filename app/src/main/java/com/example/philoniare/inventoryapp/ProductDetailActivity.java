package com.example.philoniare.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.philoniare.inventoryapp.model.Product;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class ProductDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.product_quantity) TextView productQuantityTV;
    @BindView(R.id.product_price) TextView productPriceTV;
    private Realm realm;
    private Product managedCurrentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get passed bundle data
        Bundle arguments = getIntent().getExtras();
        String productName = arguments.getString("productName");
        int productQuantity = arguments.getInt("productQuantity");
        Double productPrice = arguments.getDouble("productPrice");

        getSupportActionBar().setTitle(productName);
        productQuantityTV.setText(String.format(Locale.ENGLISH,
                getString(R.string.product_quantity_formatter), productQuantity));
        productPriceTV.setText(String.format(Locale.ENGLISH,
                getString(R.string.product_price_formatter), productPrice));

        realm = Realm.getDefaultInstance();
        managedCurrentProduct = realm.where(Product.class).equalTo("name", productName)
                .findFirst();
    }

    @OnClick(R.id.increase_quantity)
    public void increateQuantity(View view) {
        realm.beginTransaction();
        managedCurrentProduct.setQuantity(managedCurrentProduct.getQuantity() + 1);
        realm.commitTransaction();
    }

    @OnClick(R.id.decrease_quantity)
    public void decreaseQuantity(View view) {
        realm.beginTransaction();
        managedCurrentProduct.setQuantity(managedCurrentProduct.getQuantity() - 1);
        realm.commitTransaction();
    }

    @OnClick(R.id.product_delete)
    public void deleteProduct(View view) {
        // Ask for confirmation first
        new AlertDialog.Builder(this)
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

    @OnClick(R.id.product_order_more)
    public void productOrderMore(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[0]);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                String.format(Locale.ENGLISH, getString(R.string.email_order_formatter),
                        managedCurrentProduct.getName()));
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
}
