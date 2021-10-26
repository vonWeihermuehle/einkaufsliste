package net.mbmedia.einkaufsliste;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ListView listView;

    private List<String> items = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        initFloatingActionButton();
        initListView();
    }

    private void initListView() {
        items.clear();
        items.addAll(db.getAllItems());
        arrayAdapter = new ArrayAdapter<>(this, R.layout.row, R.id.textView, items);
        listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String s = arrayAdapter.getItem(position);
            removeItem(s);
        });
    }

    private void initFloatingActionButton() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showDialog());
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);

        alert.setMessage("bitte den Namen angeben");
        alert.setTitle("Artikel hinzufügen");
        alert.setView(edittext);
        alert.setPositiveButton("OK", (dialog, whichButton) -> addItem(edittext.getText().toString()));
        alert.setNegativeButton("Abbrechen", (dialog, whichButton) -> {
            //nothing
        });
        alert.show();
    }

    private void addItem(String item) {
        items.add(item);
        db.addItem(item);
        arrayAdapter.notifyDataSetChanged();
    }

    private void removeItem(String item) {
        items.remove(item);
        db.delItem(item);
        arrayAdapter.notifyDataSetChanged();
    }
}