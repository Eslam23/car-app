package com.example.carapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.net.URI;

public class ViewCarActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private TextInputEditText model ,color,des,dbl;
    private ImageView iv;
    private int carId =-1;
    private MyDataBase db;
    public static final int IMAGE_PICK_REQ_CODE=1;
    public static final int ADD_CAR_RESULT_CODE=1;
    public static final int EDIT_CAR_RESULT_CODE=1;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);
        toolbar =findViewById(R.id.details_toolbar);
//        setSupportActionBar(toolbar);
        model =findViewById(R.id.et_datails_models);
        color =findViewById(R.id.et_datails_color);
        des= findViewById(R.id.et_datails_description);
        dbl =findViewById(R.id.et_datails_dbl);
        iv=findViewById(R.id.iv_details);
        db=new MyDataBase(this);
        Intent intent =getIntent();
        carId=intent.getIntExtra(MainActivity.CAR_KEY,-1);
        if(carId==-1){
            //ADD CAR
            enabledfields();
            clearfields();

        }
        else{
            //code show
            disabledfields();
            Cars car= db.getcars(carId);
            if(car!=null){
                fillfields(car);
            }
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in,IMAGE_PICK_REQ_CODE);
            }
        });
    }
    private void fillfields(Cars c){
        if(c.getImage() !=null && !c.getImage().equals("")) {
            iv.setImageURI(Uri.parse(c.getImage()));
        }
        else
        {
           // iv.setImageResource(R.drawable.car2);
        }
            model.setText(c.getModel());
            color.setText(c.getColor());
            des.setText(c.getDescription());
            dbl.setText( c.getDbl()+"");


    }
    private void disabledfields(){
        iv.setEnabled(false);
        model.setEnabled(false);
        color.setEnabled(false);
        des.setEnabled(false);
        dbl.setEnabled(false);
    }
    private void enabledfields(){
        iv.setEnabled(true);
        model.setEnabled(true);
        color.setEnabled(true);
        des.setEnabled(true);
        dbl.setEnabled(true);
    }
    private void clearfields(){
        iv.setImageURI(null);
        model.setText("");
        color.setText("");
        des.setText("");
        dbl.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        MenuItem save = menu.findItem(R.id.details_menu_save);
        MenuItem edit = menu.findItem(R.id.details_menu_edit);
        MenuItem delete = menu.findItem(R.id.details_menu_delete);
        if(carId==-1){
            //ADD CAR(press floating action button)
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        }
        else{
           // EDIT CAR (press cardview)
            save.setVisible(true);
            edit.setVisible(true);
            delete.setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.details_menu_save:
                   String model2 ,color2 ,image2=null ,description2 ;
                   double dpl2 ;
                   model2 = model.getText().toString();
                   color2 =color.getText().toString();
                  if(imageUri!=null)
                  {
                     image2 = imageUri.toString();
                  }

                  description2= des.getText().toString();
                 dpl2 = Double.parseDouble(dbl.getText().toString());
                 Cars car =new Cars(carId,model2,color2,image2,description2,dpl2);
                 if(carId==-1)
                 {
                    db.insertRow(car);
                    setResult(ADD_CAR_RESULT_CODE,null);
                    finish();
                }
                else
                {
                    db.updateRow(car);
                    setResult(EDIT_CAR_RESULT_CODE,null);
                    finish();
                }

                return true;
            case R.id.details_menu_edit:
                enabledfields();
                //MenuItem save = toolbar.getMenu().findItem(R.id.details_menu_save);
               // MenuItem edit = toolbar.getMenu().findItem(R.id.details_menu_edit);
              //  MenuItem delete = toolbar.getMenu().findItem(R.id.details_menu_delete);
              //  save.setVisible(true);
              //  edit.setVisible(false);
             //   delete.setVisible(false);
                return true;
            case R.id.details_menu_delete:
                 car =new Cars(carId,null,null,null,null,0);
                 db.deleteRow(carId);
                 setResult(EDIT_CAR_RESULT_CODE,null);
                 finish();

                return true;
        }
        return false;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_PICK_REQ_CODE && resultCode==RESULT_OK)
        {
            if(data !=null)
            {

                imageUri = data.getData();
                iv.setImageURI(imageUri);
            }
        }
    }
}
