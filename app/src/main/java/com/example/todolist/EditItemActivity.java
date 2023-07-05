package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolist.CategoryPackage.Category;
import com.example.todolist.CategoryPackage.CategoryViewModel;
import com.example.todolist.ItemPackage.Item;
import com.example.todolist.ItemPackage.ItemViewModel;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EditItemActivity extends AppCompatActivity {

    Spinner spinner;
    EditText textTitle;
    EditText textDescription;
    TimePicker timePicker;
    DatePicker datePicker;
    CheckBox boxStatus;
    CheckBox boxNotify;
    Button buttonSave;
    Button buttonCancel;
    private CategoryViewModel categoryViewModel;
    Item item;

    ListView listView;
    ArrayAdapter<String> attachmentsAdapter;
    List<String> attachmentPaths = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Bundle b = getIntent().getExtras();
        item = null; // or other values
        if(b != null){
            Gson gson = new Gson();
            String s = b.getString("cat");
            item = gson.fromJson(s, Item.class);
            //category = b.getInt("key");
        }

        spinner = findViewById(R.id.spinnerCategoriesAdd);
        textTitle = findViewById(R.id.editTextTitle);
        textDescription = findViewById(R.id.editTextDescription);
        timePicker = findViewById(R.id.timePicker1);
        datePicker = findViewById(R.id.datePicker1);
        boxStatus = findViewById(R.id.checkBoxStatus);
        boxNotify = findViewById(R.id.checkBoxNotification);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);

        listView = findViewById(R.id.list_view_attachments2);
        attachmentPaths.clear();
        if(item != null)
            attachmentPaths = item.getAttachmentPaths();

        List<String> attachmentNames = new ArrayList<>();
        for (String attachmentPath : attachmentPaths) {
            File file = new File(attachmentPath);
            String attachmentName = file.getName();
            attachmentNames.add(attachmentName);
        }

        attachmentsAdapter = new ArrayAdapter<>(this, R.layout.list_item_attachment, R.id.text1, attachmentNames);
        listView.setAdapter(attachmentsAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            String filePath = attachmentPaths.get(position);
            openFile(filePath);
        });

        Date data = item.getEndDate();
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(data);   // assigns calendar to given date
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        int minute = calendar.get(Calendar.MINUTE);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        timePicker.setHour(hour);
        timePicker.setMinute(minute);
        datePicker.init(year, month, day, null);

        textTitle.setText(item.getTitle());
        textDescription.setText(item.getDescription());
        boxStatus.setChecked(item.getFinished());
        boxNotify.setChecked(item.getNotify());
        initspinnerfooter();
    }

    public void onClickCancel(View v){
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }

    public void onClickSave(View v){
        //Dodac tutaj logike zapisu do bazy

            ItemViewModel itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ItemViewModel.class);


            String title = textTitle.getText().toString();
            String description = textDescription.getText().toString();
            String category = spinner.getSelectedItem().toString();
            //String category = "Other";
            Boolean isFinished = boxStatus.isChecked();
            Boolean notify = boxNotify.isChecked();
            //dopisac ataczmenty
            Boolean hasAddons = false;

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minute, 0);

            Date endDate = calendar.getTime();

            item.setTitle(title);
            item.setDescription(description);
            item.setCategoryId(category);
            item.setFinished(isFinished);
            item.setNotify(notify);
            item.setHasAddons(hasAddons);
            item.setEndDate(endDate);


            //itemViewModel.insertStudent(item);
            itemViewModel.updateItem(item);

            Intent myIntent = new Intent(this, MainActivity.class);
            Bundle b = new Bundle();
            b.putString("cat", "change"); //Your id
            myIntent.putExtras(b);
            this.startActivity(myIntent);

    }

    public void onClickDelete(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to remove this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ItemViewModel itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ItemViewModel.class);

                itemViewModel.deleteItem(item);
                deleteTaskFiles(item);

                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle b = new Bundle();
                b.putString("cat", "change"); //Your id
                myIntent.putExtras(b);
                startActivity(myIntent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void openFile(String filePath) {
        File file = new File(filePath);
        Uri fileUri = FileProvider.getUriForFile(this, "com.example.todolist.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, getMimeType(filePath));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No app found to open this file", Toast.LENGTH_SHORT).show();
        }
    }

    private String getMimeType(String filePath) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (mimeType == null) {
            mimeType = "*/*";
        }
        return mimeType;
    }

    private void deleteTaskFiles(Item item) {
        List<String> attachmentPaths = item.getAttachmentPaths();
        for (String attachmentPath : attachmentPaths) {
            File file = new File(attachmentPath);
            if (file.exists()) {
                file.delete();
            }
        }

        String taskFolderPath = getTaskFolderPath(item.getId());
        File taskFolder = new File(taskFolderPath);
        if (taskFolder.exists()) {
            deleteFolder(taskFolder);
        }
    }

    private void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        folder.delete();
    }

    private String getTaskFolderPath(long taskId) {
        File folder = new File(getFilesDir(), "task_" + taskId);
        return folder.getAbsolutePath();
    }

    private void initspinnerfooter() {
        List<String> items = new ArrayList<>();

        categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);

        categoryViewModel.getAllStudentsFromVm().observe(this, students ->
        {
            if (students != null && !students.isEmpty()) {
                for(int i=0;i<students.size();i++){
                    items.add(students.get(i).getName());
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
            spinner.setAdapter(adapter);
            spinner.setSelection(adapter.getPosition(item.getCategory()));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
        });
    }
}