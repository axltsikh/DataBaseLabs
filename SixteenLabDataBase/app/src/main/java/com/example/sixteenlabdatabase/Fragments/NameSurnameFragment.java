package com.example.sixteenlabdatabase.Fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sixteenlabdatabase.Contact;
import com.example.sixteenlabdatabase.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameSurnameFragment extends Fragment {
    private static final String TAG = "ExceptionLog";
    ArrayList<Contact> contacts=new ArrayList<>();
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_name_surname, container, false);
        contacts=new ArrayList<>();
        Filler();
        return rootView;
    }
    private void Filler() {
        ListView contactsListView=rootView.findViewById(R.id.ContactsList);
        try {
            Cursor contactsCursor = getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (contactsCursor != null) {
                while (contactsCursor.moveToNext()) {
                    Contact contact = new Contact();
                    String contactId = contactsCursor
                            .getString(contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    contact.ID = contactId;

                    String contactName = contactsCursor
                            .getString(contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    contact.Name = contactName;

                    int hasContactPhone = Integer.parseInt(contactsCursor
                            .getString(contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                    if (hasContactPhone > 0) {
                        Cursor phoneCursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);

                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(phoneCursor
                                    .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contact.Phone = phoneNumber;
                        }
                        phoneCursor.close();
                    }
                    contacts.add(contact);
                }
                contactsCursor.close();
                Log.d(TAG, "Filler: " + contacts.get(contacts.size()-1).Name);
            } else {
                Log.d(TAG, "Filler: " + "?");
                return;
            }
            ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(getContext(),
                    android.R.layout.simple_list_item_1, contacts);
            contactsListView.setAdapter(adapter);
        }
        catch (Exception ex){
            Log.d(TAG, "Filler: " + ex.getMessage());
        }
        for(Contact a: contacts){
            Log.d(TAG, "Filler: " + a.Name);
        }
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment detailsFragment=new ContactDetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("contact",contacts.get(i));
                detailsFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,detailsFragment).addToBackStack(null).commit();

            }
        });
        EditText nameText=rootView.findViewById(R.id.findByNameSurnameEditText);
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<Contact> buffer=new ArrayList<>();
                Pattern pattern= Pattern.compile(charSequence.toString().toLowerCase());
                for(Contact a : contacts){
                    if(pattern.matcher(a.Name.toLowerCase()).find() || pattern.matcher(a.Phone.toLowerCase()).find()){
                        buffer.add(a);
                    }
                }
                ArrayAdapter<Contact> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,buffer);
                contactsListView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}