package id.dekpas.modul4;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {
    private DBPegawai MyDatabase;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList NIKList;
    private ArrayList NamaList;
    private ArrayList UsiaList;
    private ArrayList JKList;
    private ArrayList PekerjaanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        NIKList = new ArrayList<>();
        NamaList = new ArrayList<>();
        UsiaList = new ArrayList<>();
        JKList = new ArrayList<>();
        PekerjaanList = new ArrayList<>();

        MyDatabase = new DBPegawai(getBaseContext());
        recyclerView = findViewById(R.id.recycler);
        getData();

        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(NIKList, NamaList, UsiaList, JKList, PekerjaanList);

        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);

        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    //Berisi Statement-Statement Untuk Mengambi Data dari Database
    @SuppressLint("Recycle")
    protected void getData(){
        //Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM "+ DBPegawai.MyColumns.NamaTabel,null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for(int count=0; count < cursor.getCount(); count++){

            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir

            NIKList.add(cursor.getString(0));
            NamaList.add(cursor.getString(1));
            UsiaList.add(cursor.getString(2));
            JKList.add(cursor.getString(3));
            PekerjaanList.add(cursor.getString(4));
        }
    }
}

