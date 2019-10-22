package com.example.huynhtrongphung_16061361;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText id, name, address,email;
    Button button_save, button_select, button_update, button_delete, button_exit;
    GridView gv_display;
    ArrayAdapter<String> adapter;
    Dialog dialog;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_author:
                showDialog();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void anhXa() {

        id = (EditText) dialog.findViewById(R.id.txtid);
        name = (EditText) dialog.findViewById(R.id.txtname);
        address = (EditText) dialog.findViewById(R.id.txtaddress);
        email = (EditText) dialog.findViewById(R.id.txtemail);
        gv_display = (GridView) dialog.findViewById(R.id.gridView_listItem);
        dbHelper = new DBHelper(this);
        button_save = (Button) dialog.findViewById(R.id.buttonSave);
        button_select = (Button) dialog.findViewById(R.id.buttonSelect);
        button_delete = (Button) dialog.findViewById(R.id.buttonDelete);
        button_update = (Button) dialog.findViewById(R.id.buttonUpdate);
        button_exit = (Button) dialog.findViewById(R.id.btnexit);
    }

    private void clear() {
        id.setText("");
        name.setText("");
        address.setText("");
        email.setText("");
        id.requestFocus();

    }



    private void evenClickUpdate() {
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = id.getText().toString();
                String name1 = name.getText().toString();
                String address1 = address.getText().toString();
                String email1 = email.getText().toString();
                if (isEmpty(id1, name1, address1,email1)) {
                    int id11 = Integer.parseInt(id.getText().toString());
                    boolean isUpdate = dbHelper.updateAuthor(id11, name1, address1,email1);
                    if (isUpdate) {
                        notifyList();
                        clear();
                        Toast.makeText(getApplicationContext(), "Update thanh cong !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Loi.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nhap day du du lieu !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void evenClickDelete() {
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = id.getText().toString();
                if (!id1.isEmpty()) {
                    int idkq = Integer.parseInt(id1);
                    boolean del = dbHelper.deleteAuthor(idkq);
                    if (del) {
                        adapter.notifyDataSetChanged();
                        notifyList();
                        clear();
                        Toast.makeText(getApplicationContext(), "Xoa thanh cong !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Loi.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Xin nhap ID!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void evenClickSave() {
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Author author = new Author();
                String id1 = id.getText().toString();
                String name1 = name.getText().toString();
                String address1 = address.getText().toString();
                String email1 = email.getText().toString();
                if (isEmpty(id1, name1, address1,email1)) {
                    author.setId_author(Integer.parseInt(id.getText().toString()));
                    author.setName(name1);
                    author.setAddress(address1);
                    author.setEmail(email1);
                    if (dbHelper.insertAuthor(author)) {
                        notifyList();
                        clear();
                        Toast.makeText(getApplicationContext(), "Luu thanh cong !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Loi.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Xin nhap day du du lieu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void evenClickSelect() {
        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                ArrayList<Author> authorslist = new ArrayList<>();


                String id1 = id.getText().toString();
                if (!id1.isEmpty()) {
                    list.add("ID");
                    list.add("Name");
                    list.add("Address");
                    list.add("Email");
                    int idkq = Integer.parseInt(id1);
                    Author author = dbHelper.getAuthor(idkq);
                    list.add(author.getId_author() + "");
                    list.add(author.getName());
                    list.add(author.getAddress());
                    list.add(author.getEmail());
                } else {
                    authorslist = dbHelper.getALlAuthor();
                    list.add("ID");
                    list.add("Name");
                    list.add("Address");
                    list.add("Email");
                    for (Author a : authorslist) {
                        list.add(a.getId_author() + "");
                        list.add(a.getName());
                        list.add(a.getAddress());
                        list.add(a.getEmail());
                    }
                }
                adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                gv_display.setAdapter(adapter);
            }
        });
    }

    private void notifyList() {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Author> authorslist = new ArrayList<>();
        authorslist = dbHelper.getALlAuthor();
        list.add("ID");
        list.add("Name");
        list.add("Address");
        list.add("Email");
        for (Author a : authorslist) {
            list.add(a.getId_author() + "");
            list.add(a.getName());
            list.add(a.getAddress());
            list.add(a.getEmail());
        }
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
        gv_display.setAdapter(adapter);
    }

    private boolean isEmpty(String id, String name, String address,String email) {
        if (id.isEmpty() || name.isEmpty() || address.isEmpty()|| email.isEmpty()) {
            return false;
        }
        return true;
    }

    private void showDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Thông tin tác giả");
        dialog.setContentView(R.layout.dialog);
        anhXa();
        dialog.show();
        Window window = dialog.getWindow();

        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        evenClickSelect();
        evenClickSave();
        evenClickDelete();
        evenClickUpdate();
        eventClickExit();
    }
    private void eventClickExit() {
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
    }
}
