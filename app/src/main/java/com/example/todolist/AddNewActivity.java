package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
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

import com.example.todolist.CategoryPackage.CategoryViewModel;
import com.example.todolist.ItemPackage.Item;
import com.example.todolist.ItemPackage.ItemViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewActivity extends AppCompatActivity {

    Spinner spinner;
    EditText textTitle;
    EditText textDescription;
    TimePicker timePicker;
    DatePicker datePicker;
    CheckBox boxStatus;
    CheckBox boxNotify;
    Button buttonSave;
    Button buttonCancel;
    Button buttonAddAttachment;
    private CategoryViewModel categoryViewModel;
    private List<Uri> selectedUris = new ArrayList<>();;
    private List<String> attachmentPaths = new ArrayList<>();;
    private ArrayAdapter<String> attachmentAdapter;
    ListView listViewAttachments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        spinner = findViewById(R.id.spinnerCategoriesAdd);
        textTitle = findViewById(R.id.editTextTitle);
        textDescription = findViewById(R.id.editTextDescription);
        timePicker = findViewById(R.id.timePicker1);
        datePicker = findViewById(R.id.datePicker1);
        boxStatus = findViewById(R.id.checkBoxStatus);
        boxNotify = findViewById(R.id.checkBoxNotification);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonAddAttachment = findViewById(R.id.buttonAddAttachment);
        listViewAttachments = findViewById(R.id.list_view_attachments);

        attachmentAdapter = new ArrayAdapter<>(this, R.layout.list_item_attachment, R.id.text1, attachmentPaths);
        listViewAttachments.setAdapter(attachmentAdapter);

        buttonAddAttachment.setOnClickListener(view -> openFilePicker());
        initspinnerfooter();
    }


    public void onClickCancel(View v){
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }

    public void onClickSave(View v){
        //Dodac tutaj logike zapisu do bazy

        String title = textTitle.getText().toString();
        String description = textDescription.getText().toString();
        String category = spinner.getSelectedItem().toString();
        //String category = "Other";
        Date createDate = new Date();
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

        Item item = new Item(category, title, description, createDate, endDate, isFinished, notify, hasAddons);

        ItemViewModel itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ItemViewModel.class);

        itemViewModel.insertItem2(item).observe(this, insertedItemId -> {
            if (insertedItemId != null && insertedItemId > 0) {
                attachmentPaths.clear();
                for (Uri uri : selectedUris) {
                    String attachmentPath = copyFileToFolder(uri, insertedItemId);
                    attachmentPaths.add(attachmentPath);
                }

                int id = Math.toIntExact(insertedItemId);

                itemViewModel.getItemById(id).observe(this, insertedTask -> {
                    if (insertedTask != null) {
                        insertedTask.setAttachmentPaths(attachmentPaths);
                        itemViewModel.updateItem(insertedTask);

                    } else {
                        Log.v("MyTag", "Error retrieving inserted task");
                    }
                });
                Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT).show();
            } else {
                Log.v("MyTag", "Error");
            }
        });;



        Intent myIntent = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("cat", "change"); //Your id
        myIntent.putExtras(b);
        this.startActivity(myIntent);
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

    private String copyFileToFolder(Uri uri, long taskId) {
        String path = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            String fileName = getFileName(uri);
            File folder = new File(getFilesDir(), String.valueOf(taskId));
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File dest = new File(folder, fileName);
            path = dest.getAbsolutePath();
            FileOutputStream outputStream = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    selectedUris.add(uri);
                    Log.v("MyTag", "uri=" + uri);
                    attachmentPaths.add(getFileName(uri));
                }
            } else if (data.getData() != null) {
                Uri uri = data.getData();
                selectedUris.add(uri);
                Log.v("MyTag", "uri=" + uri);
                attachmentPaths.add(getFileName(uri));
            }
            attachmentAdapter.notifyDataSetChanged();
        }
    }

    private String getFileName(Uri uri) {
        String fileName = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME);
            fileName = cursor.getString(nameIndex);
            cursor.close();
        }
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }
        return fileName;
    }
}