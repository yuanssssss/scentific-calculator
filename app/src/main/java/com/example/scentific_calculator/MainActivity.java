package com.example.scentific_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.GridAdapter;
import database.HistoryDB;

public class MainActivity extends AppCompatActivity {
    private List<String>names;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private SQLiteDatabase sqLiteDatabase;
    private HistoryDB historyDB;
    private  int count;
    private String expression;
    private String result;
    private TextView expTv;
    private TextView resTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count=0;
        names=new ArrayList<>();
        expTv=(TextView) findViewById(R.id.expression);
        resTv=(TextView) findViewById(R.id.result);
        expTv.setText("");
        resTv.setText("");
        expression="";
        result="";
        DBInit();
        GridViewInit();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(R.id.history==item.getItemId())
        {
            Intent intent=new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void GridViewInit()
    {
        gridView=(GridView) findViewById(R.id.keyboard);
        KeyboardInit();
        gridAdapter=new GridAdapter(this,names);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                if (expTv.getText()==null) {
                    expTv.setText("");
                }
                if (resTv.getText()==null) {
                    resTv.setText("");
                }
                */
                if(i==34) {
                    expression=expTv.getText().toString();
                    result=resTv.getText().toString();
                    AddRecord(count++,expression,result);
                }
                else if(i==3) {
                    expression="";
                }
                else if (i==4) {
                    if(expression.length()>1) expression=expression.substring(0,expression.length()-1);
                    else expression="";
                }
                else if (i%5==0||i==6||i==7) {
                    expression+=names.get(i);
                    expression+="(";
                }
                else if (i==31) {
                    
                }
                else {
                    expression+=names.get(i);
                }
                expTv.setText(expression);
            }
        });
    }

    private void DBInit()
    {
        historyDB=HistoryDB.getInstance(this);
        sqLiteDatabase=historyDB.getWritableDatabase();
    }
    private void KeyboardInit()
    {
        names.add("sin");   names.add("\u03c0"); names.add("e");  names.add("C"); names.add("del");
        names.add("cos");   names.add("1/x");    names.add("|x|");names.add("%"); names.add("mod");
        names.add("tan");   names.add("(");      names.add(")");  names.add("n!");names.add("/");
        names.add("log");   names.add("7");      names.add("8");  names.add("9"); names.add("X");
        names.add("ln");    names.add("4");      names.add("5");  names.add("6"); names.add("+");
        names.add("^");     names.add("1");      names.add("2");  names.add("3"); names.add("-");
        names.add("\u221a");names.add("+/-");    names.add("0");  names.add("."); names.add("=");
    }
    public void AddRecord(int id,String expression,String result)
    {
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("expression",expression);
        values.put("result",result);
        sqLiteDatabase.insert("historylist",null,values);
    }



}