package com.example.philoniare.inventoryapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.philoniare.inventoryapp.model.Product;
import com.example.philoniare.inventoryapp.model.Supplier;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class AddProductActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.input_product_name) EditText inputProductName;
    @BindView(R.id.input_product_quantity) EditText inputProductQuantity;
    @BindView(R.id.input_product_price) EditText inputProductPrice;
    @BindView(R.id.input_product_image) EditText inputProductImage;

    @BindView(R.id.product_suppliers) Spinner supplierSpinner;
    private Realm realm;
    private String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.activity_name_add_product));

        // Get suppliers from DB and populate the spinner
        ArrayList<String> supplierList = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        RealmResults<Supplier> storedSuppliers = realm.where(Supplier.class).findAll();
        for(Supplier supplier: storedSuppliers) {
            supplierList.add(supplier.getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, supplierList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supplierSpinner.setAdapter(spinnerAdapter);
    }

    @OnClick(R.id.product_add)
    public void productAdd(View view) {
        // Validate the data first & display errors
        String newProductName = inputProductName.getText().toString();
        int newProductQuantity = Integer.parseInt(inputProductQuantity.getText().toString());
        Double newProductPrice = Double.parseDouble(inputProductPrice.getText().toString());
        String newProductImage = inputProductImage.getText().toString();
        Product newProduct = new Product();
    }

    private boolean validateNewProduct(String name, int quantity, Double price) {
        return true;
    }
}
