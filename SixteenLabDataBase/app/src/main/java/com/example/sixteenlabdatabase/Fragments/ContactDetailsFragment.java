package com.example.sixteenlabdatabase.Fragments;

import static android.content.ContentValues.TAG;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sixteenlabdatabase.Contact;
import com.example.sixteenlabdatabase.R;
import com.example.sixteenlabdatabase.databinding.FragmentContactDetailsBinding;

import java.util.ArrayList;

import kotlinx.coroutines.GlobalScope;


public class ContactDetailsFragment extends Fragment {

    Contact contact=new Contact();
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments()!=null){
            contact=(Contact)getArguments().getSerializable("contact");
        }
        FragmentContactDetailsBinding binding=FragmentContactDetailsBinding.inflate(inflater,container,false);
        rootView=binding.getRoot();
        binding.setContact(contact);
        Filler();
        return rootView;
    }
    private void Filler(){
        Button updateButton=rootView.findViewById(R.id.updateButton);
        Button deleteButton=rootView.findViewById(R.id.deleteButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
                    ContentValues contentValues;
                    ops.add(ContentProviderOperation
                            .newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?", new String[]{contact.ID, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE})
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.Name).build());
                    ops.add(ContentProviderOperation
                            .newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(ContactsContract.Data.CONTACT_ID + "=? AND " +
                            ContactsContract.Data.MIMETYPE + "=?",new String[]{contact.ID,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE})
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,contact.Phone)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build());
                    getContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY,ops);
                }
                catch(Exception e){
                    Log.d(TAG, "onClick: " + e.getMessage());
                }
//                try {
//
//                    ArrayList ops = new ArrayList();
//                    ContentResolver cr = getContext().getContentResolver();
//
//                    ops.add(ContentProviderOperation
//                            .newDelete(ContactsContract.RawContacts.CONTENT_URI)
//                            .withSelection(ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                                    new String[]{ contact.ID })
//                            .build());
//
//                    try {
//                        cr.applyBatch(ContactsContract.AUTHORITY, ops);
//                        ops.clear();
//                    }
//                    catch (OperationApplicationException e) {
//                        e.printStackTrace();
//                    }
//                    catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//
//                    ops.add(ContentProviderOperation
//                            .newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
//                            .build());
//
//                    ops.add(ContentProviderOperation
//                            .newInsert(ContactsContract.Data.CONTENT_URI)
//                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.Name)
//                            .build());
//
//                    ops.add(ContentProviderOperation.
//                            newInsert(ContactsContract.Data.CONTENT_URI)
//                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.Phone)
//                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
//                            .build());
//
//                    rootView.getContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//
//                }
//                catch (Exception ex){
//                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList ops = new ArrayList();
                ContentResolver cr = getActivity().getContentResolver();

                ops.add(ContentProviderOperation
                        .newDelete(ContactsContract.RawContacts.CONTENT_URI)
                        .withSelection(ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{ contact.ID })
                        .build());

                try {
                    cr.applyBatch(ContactsContract.AUTHORITY, ops);
                    ops.clear();
                    Log.d("ExceptionLog", "onClick: " + "Succes?");
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                catch (OperationApplicationException ex) {
                    Log.d("ExceptionLog", "onClick: " + ex.getMessage());
                }
                catch (RemoteException ex) {
                    Log.d("ExceptionLog", "onClick: " + ex.getMessage());
                }
            }
        });
    }
}