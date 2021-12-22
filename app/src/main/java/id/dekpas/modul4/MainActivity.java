package id.dekpas.modul4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String NIK_Pegawai = "NIK Pegawai";
    public static final String NAMA_Pegawai = "Nama Pegawai";
    public static final String USIA_Pegawai = "Usia Pegawai";
    public static final String JK_Pegawai = "Jenis Kelamin Pegawai";
    public static final String PEKERJAAN_Pegawai = "Pekerjaan Pegawai";
    public static final String SEEKBAR = "SEEKBAR";


    boolean isAllFieldsChecked = false;
    int seekBarValue;
    TextView Persentase;
    EditText nikPegawai, namaPegawai, usiaPegawai;
    CheckBox pekerjaan1, pekerjaan2, pekerjaan3;
    RadioGroup jenisKelamin;
    RadioButton pria, wanita, radioButton;
    Button btnSubmit, btnReset;
    SeekBar seekBar;
    DBPegawai dbPegawai;
    String sPekerjaan;
    String sNama;
    String sNik;
    String sUsia;
    String sJk;
    String sSeekBar;
    Button cancelDialog;
    Button kirimDialog;
    int status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbPegawai = new DBPegawai(getBaseContext());

        nikPegawai = (EditText) findViewById(R.id.etNIK);
        namaPegawai = (EditText) findViewById(R.id.etNamaPegawai);
        usiaPegawai = (EditText) findViewById(R.id.etUsiaPegawai);

        pekerjaan1 = (CheckBox) findViewById(R.id.pekerjaan1);
        pekerjaan2 = (CheckBox) findViewById(R.id.pekerjaan2);
        pekerjaan3 = (CheckBox) findViewById(R.id.pekerjaan3);

        jenisKelamin = (RadioGroup) findViewById(R.id.radioJK);

        pria = (RadioButton) findViewById(R.id.jkPria);
        wanita = (RadioButton) findViewById(R.id.jkWanita);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnReset = (Button) findViewById(R.id.btnReset);

        seekBar = (SeekBar) findViewById(R.id.parameterKemudahan);
        Persentase = (TextView) findViewById(R.id.tvNilaiSeekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                seekBarValue = progressValue;
                String value = String.valueOf(progressValue);
                if(value.equals("0")){
                    Persentase.setText("Sulit");
                    status = 1;
                }else if (value.equals("1")){
                    Persentase.setText("Mudah");
                    status = 1;
                }else if (value.equals("2")){
                    Persentase.setText("Sangat Mudah");
                    status = 1;
                }else {
                    status = 0;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();
                if(isAllFieldsChecked){
                    sNama = namaPegawai.getText().toString();
                    sNik = nikPegawai.getText().toString();
                    sUsia = usiaPegawai.getText().toString();
                    sSeekBar =  Persentase.getText().toString();

                    int selectedJk = jenisKelamin.getCheckedRadioButtonId();
                    if (selectedJk == pria.getId()){
                        sJk = pria.getText().toString();

                    }else if (selectedJk == wanita.getId()){
                        sJk = wanita.getText().toString();

                    }

                    String sUsia = usiaPegawai.getText().toString();


                    sPekerjaan="";
                    if(pekerjaan1.isChecked()){
                        sPekerjaan = sPekerjaan + "Mahasiswa; ";
                    }
                    if(pekerjaan2.isChecked()){
                        sPekerjaan = sPekerjaan + "Wirausaha; ";
                    }
                    if(pekerjaan3.isChecked()){
                        sPekerjaan = sPekerjaan  + "PNS; ";
                    }

                    showDialog();

                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nikPegawai.setText("");
                namaPegawai.setText("");
                usiaPegawai.setText("");
                pekerjaan1.setChecked(false);
                pekerjaan2.setChecked(false);
                pekerjaan3.setChecked(false);
                pria.setChecked(false);
                wanita.setChecked(false);
                seekBar.setProgress(0);
                Persentase.setText("");
            }
        });

        Button viewData = findViewById(R.id.readData);
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewData.class));
            }
        });
        
    }
    private boolean CheckAllFields(){
        if (nikPegawai.length() == 0) {
            nikPegawai.setError("Data tidak boleh kosong");
            return false;
        }
        if (namaPegawai.length() == 0) {
            namaPegawai.setError("Data tidak boleh kosong");
            return false;
        }
        if (usiaPegawai.length() == 0) {
            usiaPegawai.setError("Data tidak boleh kosong");
            return false;
        }
        if (jenisKelamin.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MainActivity.this,"Harap pilih jenis kelamin",Toast.LENGTH_SHORT).show();
//            wanita.setError("Harap pilih jenis kelamin");
            return false;
        }
        if (!pekerjaan1.isChecked() && !pekerjaan2.isChecked() && !pekerjaan3.isChecked()) {
            Toast.makeText(MainActivity.this,"Harap pilih pekerjaan",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(status == 0){
            Toast.makeText(MainActivity.this,"Harap Berikan Rating",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog);

        TextView alertNama = dialog.findViewById(R.id.tNama);
        TextView alertNik = dialog.findViewById(R.id.tNik);
        TextView alertJK = dialog.findViewById(R.id.tJK);
        TextView alertPekerjaan = dialog.findViewById(R.id.tPekerjaan);
        TextView alertPresentase = dialog.findViewById(R.id.tPresentase);

        cancelDialog = (Button) dialog.findViewById(R.id.btn_cancel);
        kirimDialog = (Button) dialog.findViewById(R.id.btn_kirim);



        alertNama.setText(sNama);
        alertNik.setText(sNik);
        alertJK.setText(sJk);
        alertPekerjaan.setText(sPekerjaan);
        alertPresentase.setText(sSeekBar);

        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        kirimDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase create = dbPegawai.getWritableDatabase();

//                Intent intentData = new Intent(this, acti .class);
                int selectedId = jenisKelamin.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

                String messageNIK = nikPegawai.getText().toString();
                String messageNama = namaPegawai.getText().toString();
                String messageUsia = usiaPegawai.getText().toString();
                String messageRadio = radioButton.getText().toString();
                String messageSeekbar = String.valueOf(seekBarValue);
                String messagePekerjaan = "";
                if(pekerjaan1.isChecked()){
                    messagePekerjaan = messagePekerjaan + "Siswa/Mahasiswa; ";
                }
                if(pekerjaan2.isChecked()){
                    messagePekerjaan = messagePekerjaan + "Wirausaha; ";
                }
                if(pekerjaan3.isChecked()){
                    messagePekerjaan = messagePekerjaan + "ASN/PNS; ";
                }

                Intent intentData = new Intent(MainActivity.this, SecondActivity.class);
                intentData.putExtra(NIK_Pegawai, messageNIK);
                intentData.putExtra(NAMA_Pegawai, messageNama);
                intentData.putExtra(USIA_Pegawai, messageUsia);
                intentData.putExtra(JK_Pegawai, messageRadio);
                intentData.putExtra(SEEKBAR, messageSeekbar);
                intentData.putExtra(PEKERJAAN_Pegawai, messagePekerjaan);

                //Membuat Map Baru, Yang Berisi Nama Kolom dan Data Yang Ingin Dimasukan
                ContentValues values = new ContentValues();
                values.put(DBPegawai.MyColumns.NIK, messageNIK);
                values.put(DBPegawai.MyColumns.Nama, messageNama);
                values.put(DBPegawai.MyColumns.Usia, messageUsia);
                values.put(DBPegawai.MyColumns.JenisKelamin, messageRadio);
                values.put(DBPegawai.MyColumns.Pekerjaan, messagePekerjaan);
                values.put(DBPegawai.MyColumns.Penilaian, messageSeekbar);

                //Menambahkan Baris Baru, Berupa Data Yang Sudah Diinputkan pada Kolom didalam Database
                create.insert(DBPegawai.MyColumns.NamaTabel, null, values);
                startActivity(intentData);

            }
        });
        dialog.show();
    }
}