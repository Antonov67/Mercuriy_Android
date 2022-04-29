package com.example.mercuriy_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mBtnOpen  = null;
    private Button mBtnSend  = null;
    private Button mBtnClose = null;
    private TextView resultText     = null;
    private TextView resultTitle     = null;
    private Connection  mConnect  = null;

    private  String     HOST      = "212.3.141.67";
    private  int        PORT      = 1002;

    private  String     LOG_TAG   = "SOCKET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnOpen  = findViewById(R.id.openButton);
        mBtnSend  = findViewById(R.id.sendButton);
        mBtnClose = findViewById(R.id.closeButton);
        resultText = findViewById(R.id.resultText);
        resultTitle = findViewById(R.id.resultTitle);

        mBtnSend .setEnabled(false);
        mBtnClose.setEnabled(false);

        mBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenClick();
            }
        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendClick();
            }
        });

        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseClick();
            }
        });
    }

    private void onOpenClick()
    {
        // Создание подключения
        mConnect = new Connection(HOST, PORT);
        // Открытие сокета в отдельном потоке
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mConnect.openConnection();
                    // Разблокирование кнопок в UI потоке
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBtnSend.setEnabled(true);
                            mBtnClose.setEnabled(true);
                        }
                    });
                    Log.d(LOG_TAG, "Соединение установлено");
                    Log.d(LOG_TAG, "(mConnect != null) = "
                            + (mConnect != null));
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                    mConnect = null;
                }
            }
        }).start();
    }

    private void onCloseClick()
    {
        // Закрытие соединения
        mConnect.closeConnection();
        // Блокирование кнопок
        mBtnSend .setEnabled(false);
        mBtnClose.setEnabled(false);
        Log.d(LOG_TAG, "Соединение закрыто");
    }

    private void onSendClick()
    {
        if (mConnect == null) {
            Log.d(LOG_TAG, "Соединение не установлено");
        }  else {
            Log.d(LOG_TAG, "Отправка сообщения");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // отправляем сообщения

                        byte[] bytes1 = { 0x5a,0x00,0x3b,0x10 }; //проверка канала связи
                        resultTitle.setText("проверка канала связи");
                        resultText.setText(mConnect.getData(bytes1, 4));

                        Thread.sleep(1000);

                        byte[] bytes2 = {0x5a, 0x01, 0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x32, (byte) 0xa4};//открытие канала связи
                        resultTitle.setText("открытие канала связи");
                        resultText.setText(mConnect.getData(bytes2, 4));

                        Thread.sleep(1000);

                        byte[] bytes3 = {0x5a, 0x08, 0x12, (byte) 0xd6, 0x1e};//чтение времени контроля
                        resultTitle.setText("чтение времени контроля");
                        resultText.setText(mConnect.getData(bytes3, 9));

                        Thread.sleep(1000);

                        byte[] bytes4 = {0x5a, 0x05, 0x00, 0x00, 0x02, (byte) 0xfd};//чтение показаний счетчика
                        resultTitle.setText("чтение показаний счетчика");
                        String value = mConnect.getData(bytes4, 19);
                        resultText.setText(value);

                        Thread.sleep(1000);
                        //преобразуем в кВт
                        resultText.setText(Integer.parseInt(value.substring(6,8) + value.substring(4,6),16) + " кВт");


                    } catch (Exception e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }
                }
            }).start();
        }
    }


}