package com.example.wang_.listviewdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txt_Name, txt_Eat, txt_Drink;
    Button button_Save, button_Show;
    ListView myListView;
    ArrayList<String> myArrayList;

    MyDataBase myDataBase;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_Name = findViewById(R.id.editText_name);
        txt_Eat = findViewById(R.id.editText_eat);
        txt_Drink = findViewById(R.id.editText_drink);

        button_Save = findViewById(R.id.button_save);
        button_Show = findViewById(R.id.button_show);
        myListView = findViewById(R.id.mylistview);

        myArrayList = new ArrayList<>();

        myDataBase = new MyDataBase(MainActivity.this);
        sqLiteDatabase = myDataBase.getWritableDatabase();

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, myArrayList);

        myListView.setAdapter(myArrayAdapter);

        //this.deleteDatabase(MyDataBase.MYTABLENAME);

        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues contentValues = new ContentValues();

                String nm = txt_Name.getText().toString();
                String eat = txt_Eat.getText().toString();
                String drink = txt_Drink.getText().toString();

                contentValues.put(MyDataBase.MYNAME, nm);
                contentValues.put(MyDataBase.MYEAT, eat);
                contentValues.put(MyDataBase.MYDRINK, drink);
                sqLiteDatabase.insert(myDataBase.MYTABLENAME, null, contentValues);

                myArrayList.add(nm+" "+eat+" "+drink);
                myArrayAdapter.notifyDataSetChanged();


            }
        });

        button_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyDataBase.MYTABLENAME, null);
                cursor.moveToFirst();

                //myArrayList = new ArrayList<>();
                myArrayList.clear();
                myArrayAdapter.notifyDataSetChanged();

                do{
                    int id = cursor.getInt(cursor.getColumnIndex(MyDataBase.KEY_ID));
                    String nm = cursor.getString(cursor.getColumnIndex(MyDataBase.MYNAME));
                    String eat = cursor.getString(cursor.getColumnIndex(MyDataBase.MYEAT));
                    String drink = cursor.getString(cursor.getColumnIndex(MyDataBase.MYDRINK));

                    //Toast.makeText(MainActivity.this, ""+cursor.getPosition(), Toast.LENGTH_SHORT).show();
                    myArrayList.add(id+" "+ nm+" "+eat+" "+drink);
                    myArrayAdapter.notifyDataSetChanged();

                }while(cursor.moveToNext());



            }
        });


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!myArrayList.isEmpty()){

                    String s = myArrayList.get(position);
                    String[] s_split = s.split(" ");
                    int myid = Integer.parseInt(s_split[0]);

                    sqLiteDatabase.delete(MyDataBase.MYTABLENAME, MyDataBase.KEY_ID + "=" + myid, null);
                    myArrayList.remove(position);
                    myArrayAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


}
