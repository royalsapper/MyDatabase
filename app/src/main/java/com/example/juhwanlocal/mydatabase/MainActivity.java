package com.example.juhwanlocal.mydatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    EditText editText2;

    TextView textView;

    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;

    String databaseName;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        textView = (TextView) findViewById(R.id.textView);

        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDataBase();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTable();
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRecord();
            }
        });

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecord();
            }
        });

    }

    public void openDataBase() {
        try {
            databaseName = editText.getText().toString();
            database = openOrCreateDatabase(databaseName, Activity.MODE_PRIVATE, null);

            println("데이터베이스를 오픈했습니다 : " + databaseName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        String tableName = editText2.getText().toString();

        String sql = "create table " + tableName + "("
                + "_id integer PRIMARY KEY autoincrement, "
                + "name text, "
                + "age integer, "
                + "mobile text)";

        try {
            if (database != null) {
                database.execSQL(sql);
                println("테이블을 추가했습니다 : " + tableName);
            } else {
                println("데이터베이스를 먼저 오픈하세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void insertRecord() {
        String name = editText3.getText().toString();
        String ageStr = editText4.getText().toString();
        String mobile = editText5.getText().toString();

        String tableName = editText2.getText().toString();

        String sql = "insert into " + tableName + "(name, age, mobile) values (?,?,?)";

        Object[] params = {name, ageStr, mobile};

        try {
            if (database != null) {
                database.execSQL(sql, params);
                println("데이터를 추가했습니다.");
            } else {
                println("데이터베이스를 먼저 오픈하세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertRecord2() {
        String name = editText3.getText().toString();
        String ageStr = editText4.getText().toString();
        String mobile = editText5.getText().toString();

        String tableName = editText2.getText().toString();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", ageStr);
        values.put("mobile", mobile);

        try {
            if (database != null) {
                long position = database.insert(tableName, null, values);
                println("데이터를 추가했습니다 : " + position);
            } else {
                println("데이터베이스를 먼저 오픈하세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteRecord() {
        String tableName = editText2.getText().toString();

        String nameParam = editText6.getText().toString();
        String[] params = {nameParam};

        try {
            if (database != null) {
                int rowsAffected = database.delete(tableName, "name = ?", params);
                println("데이터를 삭제했습니다 : " + rowsAffected);
            } else {
                println("데이터베이스를 먼저 오픈하세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void println(String data) {
        textView.append(data + "\n");
    }

}
