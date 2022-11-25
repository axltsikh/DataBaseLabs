package com.example.sixteenlabdatabase.Fragments;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sixteenlabdatabase.Contact;
import com.example.sixteenlabdatabase.R;
import com.example.sixteenlabdatabase.databinding.FragmentAddBinding;

import java.util.ArrayList;


public class AddFragment extends Fragment {
    private String TAG="ExceptionLog";
    private View rootView;
    Contact contact;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAddBinding binding=FragmentAddBinding.inflate(inflater,container,false);
        rootView=binding.getRoot();
        contact=new Contact();
        binding.setContact(contact);
        Filler();
        return rootView;
    }
    private void Filler(){
        Button addButton=rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList <ContentProviderOperation> ops = new ArrayList<>();
                ops.add(ContentProviderOperation
                        .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());
                ops.add(ContentProviderOperation
                        .newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.Name)
                        .build());
                ops.add(ContentProviderOperation
                        .newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.Phone)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
                try {
                    getContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                    Log.d(TAG, "onClick: Succes?");
                }
                catch (Exception ex) {
                    Log.d(TAG, "onClick: " + ex.getMessage());
                }

            }
        });
    }
}