package ir.shariaty.sendsms4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText etPhone , etMessage;
    Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = findViewById(R.id.et_message);
        etPhone = findViewById(R.id.et_phone);
        btSend = findViewById(R.id.bt_send);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this , Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
                    sendMessage();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this , new String[]{Manifest.permission.SEND_SMS} , 100);
                }
            }
        });
    }

    private void sendMessage() {
        String sPhone = etPhone.getText().toString().trim();
        String sMessage = etMessage.getText().toString().trim();

        if(!sPhone.equals("") && !sMessage.equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sPhone , null , sMessage, null , null );
            Toast.makeText(getApplicationContext() , "SMS Sent Successfully!" , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext() , "Enter Value First!" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendMessage();
        } else {
            Toast.makeText(getApplicationContext() , "شما دسترسی ندارید!" , Toast.LENGTH_SHORT).show();
        }

    }
}