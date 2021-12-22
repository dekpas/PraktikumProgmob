package id.dekpas.modul4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    private DBPegawai MyDatabase;
    private TextView NIK;
    private EditText NewNama, NewUsia, NewPekerjaan;
    private String getNewNama, getNewUsia, getNewPekerjaan;
    private Button Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        MyDatabase = new DBPegawai(getBaseContext());

        NIK = findViewById(R.id.nikUpdateTv);
        NewNama = findViewById(R.id.namaUpdateEdit);
        NewUsia = findViewById(R.id.usiaUpdateEdit);
        NewPekerjaan = findViewById(R.id.pekerjaanUpdateEdit);


        //Menerima Data Nama dan NIK yang telah dipilih Oleh User untuk diproses
        NewNama.setText(getIntent().getExtras().getString("SendNama"));
        NIK.setText(getIntent().getExtras().getString("SendNIK"));
        NewPekerjaan.setText(getIntent().getExtras().getString("SendPekerjaan"));


        Update = findViewById(R.id.btnUpdate);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpdateData();
                startActivity(new Intent(UpdateActivity.this, ViewData.class));
                finish();
            }
        });
    }

    private void setUpdateData(){
        getNewNama = NewNama.getText().toString();
        getNewUsia = NewUsia.getText().toString();
        getNewPekerjaan = NewPekerjaan.getText().toString();
        Intent intentPegawai = getIntent();
        String NIK = intentPegawai.getExtras().getString("SendNIK");

        SQLiteDatabase database = MyDatabase.getReadableDatabase();

        if (getNewNama.length() == 0 || getNewUsia.length() == 0){
            Toast.makeText(UpdateActivity.this,"Update Data Gagal",Toast.LENGTH_SHORT).show();
            Toast.makeText(UpdateActivity.this,"Data Tidak Boleh Kosong!",Toast.LENGTH_SHORT).show();
        }else{
            //Memasukan Data baru pada 2 kolom
            ContentValues values = new ContentValues();
            values.put(DBPegawai.MyColumns.Nama, getNewNama);
            values.put(DBPegawai.MyColumns.Usia, getNewUsia);
            values.put(DBPegawai.MyColumns.Pekerjaan, getNewPekerjaan);

            //Untuk Menentukan Data/Item yang ingin diubah berdasarkan NIK
            String selection = DBPegawai.MyColumns.NIK + " LIKE ?";
            String[] selectionArgs = {NIK};
            database.update(DBPegawai.MyColumns.NamaTabel, values, selection, selectionArgs);
            Toast.makeText(getApplicationContext(), "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
        }

    }
}