package com.example.recycler.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import Data.DataBaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHandler db = new DataBaseHandler(this);
        db.addcontact(new Contact("ujjwal","8527964269"));
        db.addcontact(new Contact("huhuhu","7865242"));

        List<Contact> contactlist = db.getallcontacts();

        Contact onecontact = db.getcontact(1);
        int newcontact = db.updatecontact(onecontact);



    }
}
