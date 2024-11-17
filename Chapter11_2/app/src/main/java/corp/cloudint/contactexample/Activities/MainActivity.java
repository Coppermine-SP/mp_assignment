/*
    MainActivity - ContactExample
    Copyright (C) 2024-2025 Coppermine-SP.
 */
package corp.cloudint.contactexample.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.List;
import java.util.Optional;

import corp.cloudint.contactexample.Contexts.DatabaseContext;
import corp.cloudint.contactexample.Models.Contact;
import corp.cloudint.contactexample.R;

class ContactAdapter extends BaseAdapter{
    List<Contact> list;
    Context context;

    public ContactAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    @SuppressLint("ViewHolder")
    public View getView(int i, View view, ViewGroup viewGroup) {
        //ViewHolder 패턴이 권장 되는 것은 충분히 알고 있습니다.
        //그러나 그것은 매우 귀찮습니다.
         View v = LayoutInflater.from(context).inflate(R.layout.contact, viewGroup, false);

         Contact model = list.get(i);
         ((TextView)v.findViewById(R.id.name)).setText(model.Name);
         ((TextView)v.findViewById(R.id.tel)).setText(model.Tel);

        return v;
    }

    public void setList(List<Contact> list) {
        //this method is not thread-safe.
        this.list = list;
        this.notifyDataSetChanged();
    }
}

public class MainActivity extends AppCompatActivity {
    private DatabaseContext dbContext;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbContext = new DatabaseContext(this);
        adapter = new ContactAdapter(this);
        Button searchBtn = findViewById(R.id.searchBtn);
        Button addBtn = findViewById(R.id.addBtn);
        ListView listView = findViewById(R.id.listView);

        searchBtn.setOnClickListener(v -> search());
        addBtn.setOnClickListener(v -> add());

        adapter.setList(dbContext.getContacts(java.util.Optional.empty()));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((p, v, pos, id) -> edit(pos));
    }

    private void search() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_search, null, false);
        AppCompatEditText name = view.findViewById(R.id.searchName);

        new AlertDialog.Builder(this)
                .setTitle("연락처 검색")
                .setView(view)
                .setPositiveButton("검색", (d, w) -> {

                    if(name.getText().toString().isEmpty()) {
                        adapter.setList(dbContext.getContacts(Optional.empty()));
                    }
                    else{
                        adapter.setList(dbContext.getContacts(Optional.of(name.getText().toString())));
                    }
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void add() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null, false);
        AppCompatEditText name = view.findViewById(R.id.addName);
        AppCompatEditText tel = view.findViewById(R.id.addTel);

        new AlertDialog.Builder(this)
                .setTitle("연락처 추가")
                .setView(view)
                .setPositiveButton("추가", (d, w) -> {
                    Contact model = new Contact();
                    model.Tel = tel.getText().toString();
                    model.Name = name.getText().toString();
                    dbContext.addContact(model);
                    adapter.setList(dbContext.getContacts(java.util.Optional.empty()));
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private boolean edit(int pos){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null, false);
        AppCompatEditText name = view.findViewById(R.id.addName);
        AppCompatEditText tel = view.findViewById(R.id.addTel);
        Contact model = (Contact) adapter.getItem(pos);

        name.setText(model.Name);
        tel.setText(model.Tel);

        new AlertDialog.Builder(this)
                .setTitle("연락처 수정 / 삭제")
                .setView(view)
                .setPositiveButton("수정", (d, w) -> {
                    model.Name = name.getText().toString();
                    model.Tel = tel.getText().toString();
                    dbContext.updateContact(model);
                    adapter.setList(dbContext.getContacts(java.util.Optional.empty()));
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("삭제", (d, w) -> {
                    dbContext.deleteContact(model.Id.get());
                    adapter.setList(dbContext.getContacts(java.util.Optional.empty()));
                    adapter.notifyDataSetChanged();
                })
                .setNeutralButton("취소", null)
                .show();
        return false;
    }
}
