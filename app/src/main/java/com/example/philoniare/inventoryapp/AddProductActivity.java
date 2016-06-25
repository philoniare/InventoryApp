package com.example.philoniare.inventoryapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
    @BindView(R.id.input_layout_product_name) TextInputLayout inputLayoutProductName;
    @BindView(R.id.input_layout_product_quantity) TextInputLayout inputLayoutProductQuantity;
    @BindView(R.id.input_layout_product_price) TextInputLayout inputLayoutProductPrice;

    @BindView(R.id.product_suppliers) Spinner supplierSpinner;
    private Realm realm;
    private RealmResults<Supplier> storedSuppliers;

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
        storedSuppliers = realm.where(Supplier.class).findAll();
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
        boolean formValid = true;
        // Validate the data first & display errors
        String newProductName = inputProductName.getText().toString();
        int newProductQuantity = 0;
        try {
            newProductQuantity = Integer.parseInt(inputProductQuantity.getText().toString());
            inputLayoutProductQuantity.setErrorEnabled(false);
        } catch(NumberFormatException ex) {
            // Failed to parse quantity
            inputLayoutProductQuantity.setError(getString(R.string.err_msg_quantity));
            formValid = false;
        }

        Double newProductPrice = 0.0;
        try {
            newProductPrice = Double.parseDouble(inputProductPrice.getText().toString());
            inputLayoutProductPrice.setErrorEnabled(false);
        } catch(NumberFormatException ex) {
            inputLayoutProductPrice.setError(getString(R.string.err_msg_price));
            formValid = false;
        }

        String newProductImage = inputProductImage.getText().toString();
        // Check if product with that name exists in the DB
        Product storedProduct = realm.where(Product.class)
                .equalTo("name", newProductName).findFirst();
        if (newProductName.equals("")) {
            inputLayoutProductName.setError(getString(R.string.err_msg_name_empty));
            formValid = false;
        } else if(storedProduct != null) {
            inputLayoutProductName.setError(getString(R.string.err_msg_name_exists));
            formValid = false;
        } else {
            inputLayoutProductName.setErrorEnabled(false);
        }

        long supplierId = 0;
        String selectedSupplierName = supplierSpinner.getSelectedItem().toString();
        for(Supplier supplier: storedSuppliers) {
            if(supplier.getName().equals(selectedSupplierName)) {
                supplierId = supplier.getId();
                break;
            }
        }

        if(formValid) {
            Product newProduct = new Product(newProductName, newProductQuantity,
                    newProductPrice, supplierId, inputProductImage.getText().toString());
            realm.beginTransaction();
            final Product managedProduct = realm.copyToRealm(newProduct);
            realm.commitTransaction();
            finish();
        }
    }

}
