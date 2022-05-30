package com.example.mercuriy_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        listView = findViewById(R.id.list);

        String [] deviceList = new String[3];

        //массив для демонстрации

        deviceList[0] = "г. Смоленск, ул. Ленина, д.1. \n ООО Первая энергокомпания \n Адрес IP: 212.3.141.67 \n Сетевой адрес: 90";
        deviceList[1] = "г. Смоленск, ул. Попова, д.40/2. \n ООО Первая энергокомпания \n Адрес IP: 212.3.141.68 \n Сетевой адрес: 95";
        deviceList[2] = "г. Смоленск, ул. Рыленкова, д.15. \n ООО Первая энергокомпания \n Адрес IP: 212.3.141.70 \n Сетевой адрес: 100";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, deviceList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =  new Intent(MainActivity.this, DeviceActivity.class);
                startActivity(intent);
            }
        });





    }
}