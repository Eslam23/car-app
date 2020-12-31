package com.example.carapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private MyDataBase db;
    private rv_adapter adapter;
    public static final int ADD_REQUEST= 1;
    public static final String CAR_KEY= "car_key";
    public static final int EDIT_REQUEST= 1;
     public static final  int PERMISSION_REQUEST_OK =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE },PERMISSION_REQUEST_OK);
        }
        toolbar =findViewById(R.id.main_toolbar);
        //setSupportActionBar(toolbar);
        rv =findViewById(R.id.rv_main);
        fab =findViewById(R.id.main_fab);
       // Cars c =new Cars("2019","red",null,"i love this car",16.0);
      //  Cars cc =new Cars("2020","green",null,"i love this car",16.0);


        db =new MyDataBase(this);

        ArrayList<Cars>allcars=db.getallcars();

        //db.insertRow(c);
      //  db.insertRow(cc);
      //  db.close();
        adapter =new rv_adapter(allcars, new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int Id) {
               Intent i =new Intent(MainActivity.this,ViewCarActivity.class);
               i.putExtra(CAR_KEY,Id);
               startActivityForResult(i,EDIT_REQUEST);
            }
        });
      rv.setAdapter(adapter);
      RecyclerView.LayoutManager lm= new GridLayoutManager(this,2);
      rv.setLayoutManager(lm);
      rv.setHasFixedSize(true);
      fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent =new Intent(MainActivity.this,ViewCarActivity.class);
              intent.putExtra(CAR_KEY,-1);
              startActivityForResult(intent,ADD_REQUEST);
          }
      });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Cars>cars = db.search(query);
                adapter.setAll_car(cars);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Cars>cars = db.search(newText);
                adapter.setAll_car(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ArrayList<Cars>cars = db.getallcars();
                adapter.setAll_car(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_REQUEST )
        {
            ArrayList<Cars>allcars=db.getallcars();
            adapter.setAll_car(allcars);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_REQUEST_OK:
                if(grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    //تم الحصول علي الpermission
                }
        }
    }
}
