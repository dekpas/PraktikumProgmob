package id.dekpas.modul4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    TextView nik, nama, usia, jenisKelamin, pekerjaan, seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intentPegawai = getIntent();
        String messageNIK = intentPegawai.getStringExtra(MainActivity.NIK_Pegawai);
        String messageNama = intentPegawai.getStringExtra(MainActivity.NAMA_Pegawai);
        String messageUsia = intentPegawai.getStringExtra(MainActivity.USIA_Pegawai);
        String messageRadio = intentPegawai.getStringExtra(MainActivity.JK_Pegawai);
        String messagePekerjaan = intentPegawai.getStringExtra(MainActivity.PEKERJAAN_Pegawai);
        String messageSeekbar = intentPegawai.getStringExtra(MainActivity.SEEKBAR);

        nik = (TextView) findViewById(R.id.scndNIKView);
        nama = (TextView) findViewById(R.id.scndNamaView);
        usia = (TextView) findViewById(R.id.scndUsiaView);
        jenisKelamin = (TextView) findViewById(R.id.scndJKView);
        pekerjaan = (TextView) findViewById(R.id.scndPekerjaanView);
        seekbar = (TextView) findViewById(R.id.scndSeekbarView);

        nik.setText(messageNIK);
        nama.setText(messageNama);
        usia.setText(messageUsia + " Tahun");
        jenisKelamin.setText(messageRadio);
        pekerjaan.setText(messagePekerjaan);
        switch (messageSeekbar) {
            case "0":
                seekbar.setText("Sulit");
                break;
            case "1":
                seekbar.setText("Mudah");
                break;
            case "2":
                seekbar.setText("Sangat Mudah");
                break;
        }

    }

    public void launchMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        Toast messageText = Toast.makeText(getApplicationContext(), "Selamat Tinggal !", Toast.LENGTH_SHORT);
        messageText.show();
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast messageText = Toast.makeText(getApplicationContext(), "Data ditambahkan !", Toast.LENGTH_SHORT);
        messageText.show();
    }

    @Override
    protected void onStop() {
        Toast messageText = Toast.makeText(getApplicationContext(), "Menutup Activity..", Toast.LENGTH_SHORT);
        messageText.show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Toast messageText = Toast.makeText(getApplicationContext(), "Selamat Tinggal !", Toast.LENGTH_SHORT);
        messageText.show();
        super.onDestroy();
    }

    protected void onRestart() {
        Toast messageText = Toast.makeText(getApplicationContext(), "Selamat Datang Kembali !", Toast.LENGTH_SHORT);
        messageText.show();
        super.onRestart();
    }
}