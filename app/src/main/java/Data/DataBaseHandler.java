package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Utils.Utils;

/**
 * Created by UjjwalNUtsav on 26-01-2018.
 */

public class DataBaseHandler extends SQLiteOpenHelper {
    public DataBaseHandler(Context context) {
        super(context,Utils.DATABASE_NAME,null,Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     String CREATE_CONTACT_TABLE="CREATE TABLE"+Utils.DATABASE_TABLE+"("+Utils.KEY_ID+"INTEGER PRIMARY KEY,"+Utils.KEY_NAME+"TEXT,"
             +Utils.KEY_PHONENUMBER+"TEXT"+")";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("DROP TABLE IF EXISTS"+Utils.DATABASE_TABLE);
        onCreate(db);
    }
    public void addcontact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.KEY_NAME,contact.getName());
        values.put(Utils.KEY_PHONENUMBER,contact.getPhonenumber());
        db.insert(Utils.DATABASE_TABLE,null,values);
        db.close();
    }
    public  Contact getcontact(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.query(Utils.DATABASE_TABLE, new String[]{Utils.KEY_ID,Utils.KEY_NAME,Utils.KEY_PHONENUMBER},Utils.KEY_ID
        +"=?",new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor!=null)
        cursor.moveToFirst();

        Contact contact = new Contact (Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        return contact;
    }
    public List<Contact> getallcontacts()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();
        String SELECTALL="SELECT * FROM"+Utils.DATABASE_TABLE;
        Cursor cursor = db.rawQuery(SELECTALL,null);
        if(cursor.moveToFirst()) {
            do { Contact contact = new Contact();
                 contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getColumnName(1));
                contact.setPhonenumber(cursor.getColumnName(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public int updatecontact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.KEY_NAME,contact.getName());
        values.put(Utils.KEY_PHONENUMBER,contact.getPhonenumber());

        return db.update(Utils.DATABASE_TABLE,values,Utils.KEY_ID+"=?", new String[]{String.valueOf(contact.getId())});

    }
    public void deletecontact(Contact contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Utils.DATABASE_TABLE,Utils.KEY_ID+"=?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    public int getcontactcount(){
      String countquery ="SELECT * FROM"+Utils.DATABASE_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countquery,null);
        return cursor.getCount();
       // cursor.close();

    }
}
