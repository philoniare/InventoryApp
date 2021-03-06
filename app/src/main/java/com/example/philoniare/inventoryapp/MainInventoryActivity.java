package com.example.philoniare.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.philoniare.inventoryapp.model.Product;
import com.example.philoniare.inventoryapp.model.Supplier;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainInventoryActivity extends AppCompatActivity {
    public List<Product> productList;
    private Realm realm;
    private RecyclerView productRecyclerView;
    private TextView productEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inventory);

        // Bind views
        productRecyclerView = (RecyclerView) findViewById(R.id.product_recycler_view);
        productEmptyView = (TextView) findViewById(R.id.product_empty_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addProductIntent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(addProductIntent);
            }
        });

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();

        // Create dummy unmanaged objects to seed the app with
        // if you're interested, suppliers from https://www.apple.com/supplier-responsibility/pdf/Suppliers.pdf
        // Delete previously saved data
        final RealmResults<Supplier> storedSuppliers = realm.where(Supplier.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                storedSuppliers.deleteAllFromRealm();
            }
        });

        final RealmResults<Product> storedProducts = realm.where(Product.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                storedProducts.deleteAllFromRealm();
            }
        });
        ArrayList<Supplier> suppliers = new ArrayList<>();
        suppliers.add(new Supplier(1, "Alps Electric Co. Ltd."));
        suppliers.add(new Supplier(2, "Analog Devices Inc."));
        suppliers.add(new Supplier(3, "Asahi Glass Co., Ltd."));
        suppliers.add(new Supplier(4, "AAC Technologies Holdings Inc."));

        productList = new ArrayList<>();
        productList.add(new Product("iPhone", 20, 100.50, 1,
                "http://store.storeimages.cdn-apple.com/4973/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone6/plus/iphone6-plus-box-silver-2014_GEO_US?wid=478&hei=595&fmt=jpeg&qlt=95&op_sharpen=0&resMode=bicub&op_usm=0.5,0.5,0,0&iccEmbed=0&layer=comp&.v=IqGln0"));
        productList.add(new Product("iPad", 30, 300.50, 2,
                "http://store.storeimages.cdn-apple.com/4973/as-images.apple.com/is/image/AppleInc/aos/published/images/i/pa/ipad/air/ipad-air-witb-gray-cel-201410_GEO_US?wid=366&hei=519&fmt=jpeg&qlt=95&op_sharpen=0&resMode=bicub&op_usm=0.5,0.5,0,0&iccEmbed=0&layer=comp&.v=Fzsl_0"));
        productList.add(new Product("Apple Watch", 20, 200.50, 3,
                "http://www.imore.com/sites/imore.com/files/styles/large/public/topic_images/2015/topic-apple-watch-all.png?itok=OUtlCphV"));
        productList.add(new Product("MacBook", 50, 500.50, 2,
                "http://zapp4.staticworld.net/reviews/graphics/products/uploaded/117913_g7.jpg"));
        productList.add(new Product("Mac Pro", 60, 150.50, 1,
                "http://store.storeimages.cdn-apple.com/4973/as-images.apple.com/is/image/AppleInc/aos/published/images/M/F8/MF858/MF858?wid=572&hei=572&fmt=jpeg&qlt=95&op_sharpen=0&resMode=bicub&op_usm=0.5,0.5,0,0&iccEmbed=0&layer=comp&.v=ronlz2"));
        productList.add(new Product("iMac", 70, 160.50, 4));
        productList.add(new Product("Apple TV", 80, 170.50, 1));
        productList.add(new Product("iPod", 90, 250.50, 2));

        // Persist them in Realm
        realm.beginTransaction();
        realm.copyToRealm(suppliers);
        realm.copyToRealm(productList);
        realm.commitTransaction();

        ProductAdapter productAdapter = new ProductAdapter(MainInventoryActivity.this, productList,
                new Utils.BtnClickListener() {
                    @Override
                    public void onBtnClick(View view, int position) {
                        Product product = productList.get(position);
                        Product storedProduct = realm.where(Product.class)
                                .equalTo("name", product.getName())
                                .findFirst();
                        realm.beginTransaction();
                        storedProduct.setQuantity(storedProduct.getQuantity() - 1);
                        realm.commitTransaction();

                        updateProductListFromDB();
                    }
                });
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerView.setAdapter(productAdapter);
        updateProductListFromDB();
    }

    public void updateProductListFromDB() {
        productList.clear();
        RealmResults<Product> storedProducts = realm.where(Product.class).findAll();
        for(Product storedProduct: storedProducts) {
            productList.add(storedProduct);
        }
        if(productList.isEmpty()) {
            productRecyclerView.setVisibility(View.GONE);
            productEmptyView.setVisibility(View.VISIBLE);
        } else {
            productRecyclerView.setVisibility(View.VISIBLE);
            productEmptyView.setVisibility(View.GONE);
        }
        productRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProductListFromDB();
    }
}
