package com.example.mac_os.criminalintent;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class CrimeFragment extends Fragment {

    Crime mCrime;
    EditText mTitle;
    CheckBox mSolved;
    Button mDate;
    Button mDelete;
    Button mChooseSuspect;
    Button mSendReport;
    Button mCallSuspect;
    public static String mCrimeID = "crimeID";
    public int Request_Date = 0;
    public int Request_Contact = 1;
    UUID crimeID;
    CrimeLab crimeLab;
    public static String dateIntent = "Date";
    String contact_Phone = null;
    String contact_Name = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * when we use getActivity.getIntent to getInstance the crimeID we made this fragment depend on the activity it host
         * but we don't want this we want the fragment to be independent so we gonna use the uncommented code
         *
         * crimeID = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.mCrimeID);
         */

        crimeID = (UUID) getArguments().getSerializable(mCrimeID);
        Log.i("UUID CrimeFragment", crimeID + "");
        crimeLab = CrimeLab.getInstance(getActivity());
        mCrime = crimeLab.getCrime(crimeID);

    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(mCrimeID, crimeId);
        Log.i("UUID CrimeFragment", crimeId + "");
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        crimeLab.updateCrime(mCrime);
    }

    public void getChangedCrimeID(UUID crime_ID) {
        Intent data = new Intent();
        data.putExtra(mCrimeID, crime_ID);
        getActivity().setResult(RESULT_OK, data);
    }

    public String getCrimeReport() {
        String solved;
        String suspect;
        String date;
        String report;

        if (mCrime.isSolved()) {
            solved = getString(R.string.crime_report_solved);
        } else {
            solved = getString(R.string.crime_report_unsolved);
        }

        if (mCrime.getSuspect() != null) {
            suspect = getString(R.string.crime_report_suspect, mCrime.getSuspect());
        } else {
            suspect = getString(R.string.crime_report_no_suspect);
        }
        String _format = "EEE,MMM dd";
        date = DateFormat.format(_format, mCrime.getDate()).toString();

        report = getString(R.string.crime_report, mCrime.getTitle(), date, solved, suspect);
        return report;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitle = v.findViewById(R.id.title_edit_text);
        mSolved = v.findViewById(R.id.solved_checked);
        mDate = v.findViewById(R.id.date_button);
        mDelete = v.findViewById(R.id.delete);
        mChooseSuspect = v.findViewById(R.id.btn_suspect_choose);
        mSendReport = v.findViewById(R.id.btn_crime_report);
        mCallSuspect = v.findViewById(R.id.btn_call_suspect);
        mTitle.setText(mCrime.getTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                getChangedCrimeID(mCrime.getId());
            }
        });


        updateDate();
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mCrime.getDate());
                datePickerFragment.setTargetFragment(CrimeFragment.this, Request_Date);
                datePickerFragment.show(fm, "Dialog Date");
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crimeLab.deleteCrime(mCrime);
                getActivity().finish();
            }
        });
        mSolved.setChecked(mCrime.isSolved());
        mSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
                getChangedCrimeID(mCrime.getId());
            }
        });

        mChooseSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                if (checkRespondingActivity(pickContactIntent)) {
                    startActivityForResult(pickContactIntent, Request_Contact);
                } else {
                    mChooseSuspect.setEnabled(false);
                }
            }
        });
        mSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * this code work fine.. and make user choose what is the app he want to use in reporting
                 Intent sendReportIntent = new Intent(Intent.ACTION_SEND);
                 sendReportIntent.setType("text/plain");
                 sendReportIntent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                 sendReportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                 Intent.createChooser(sendReportIntent, getString(R.string.crime_report_subject));
                 startActivity(sendReportIntent);
                 */
                /**
                 * Another way of doing the same thing the previous code made
                 */
                Intent sendReportIntent = ShareCompat.IntentBuilder.
                        from(getActivity()).
                        setType("text/plain").
                        setText(getCrimeReport()).
                        setSubject(getString(R.string.crime_report_subject)).
                        setChooserTitle(R.string.crime_report_subject).
                        createChooserIntent();
                if (checkRespondingActivity(sendReportIntent)) {
                    startActivity(sendReportIntent);
                } else {
                    mSendReport.setEnabled(false);
                }
            }
        });
        mCallSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call_suspect_Intent = new Intent(Intent.ACTION_DIAL);
                Uri number = Uri.parse("tel:" + contact_Phone + '\"');
                call_suspect_Intent.setData(number);
                startActivity(call_suspect_Intent);
            }
        });

        return v;
    }

    private boolean checkRespondingActivity(Intent pickContactIntent) {
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContactIntent, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            Toast.makeText(getActivity(), "There is no Activity available to handle what you have requested.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CONTACTS}, Request_Contact);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Request_Date) {
            Date date = (Date) data.getSerializableExtra(dateIntent);
            mCrime.setDate(date);
            updateDate();
        }
        if (requestCode == Request_Contact) {
            Uri uri_contact = data.getData();
            getContactInformation(uri_contact);
        }
    }

    private void getContactInformation(Uri uri_contact) {
        Cursor cursor_Uri_Contact;
        //specify what is the filed you want your query to return;
        String[] query_filed_contact_name = new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cursor_Uri_Contact = getActivity().getContentResolver().query(uri_contact, query_filed_contact_name, null, null);
        } else {
            cursor_Uri_Contact = new CursorLoader(
                    getActivity(),
                    uri_contact,
                    query_filed_contact_name,
                    null,
                    null,
                    null
            ).loadInBackground();
        }
        try {
            contact_Name = getContactName(cursor_Uri_Contact);
            contact_Phone = getContactPhone(cursor_Uri_Contact);
            String suspect_Name = contact_Name + "Phone " + contact_Phone;

            mCrime.setSuspect(suspect_Name);
            mChooseSuspect.setText(suspect_Name);
            if (contact_Phone != null) {
                mCallSuspect.setEnabled(true);
            } else {
                mCallSuspect.setEnabled(false);
            }
        } finally {
            cursor_Uri_Contact.close();
        }

    }

    private String getContactName(Cursor cursor_Uri_Contact) {
        String contact_Phone;
        if (cursor_Uri_Contact.getCount() != 0) {
            cursor_Uri_Contact.moveToFirst();
            contact_Phone = cursor_Uri_Contact.getString(0);
            return contact_Phone;
        } else {
            return null;
        }
    }

    private String getContactPhone(Cursor cursor_Uri_Contact) {
        String contact_Id;
        String contact_Phone;
        Cursor cursor_Phone;
        if (cursor_Uri_Contact.getCount() != 0) {
            cursor_Uri_Contact.moveToFirst();
            contact_Id = cursor_Uri_Contact.getString(1);
            contact_Phone = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cursor_Phone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?" + " AND " + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?", new String[]{contact_Id, ContactsContract.CommonDataKinds.Phone.TYPE_HOME + ""}, null);
            } else {
                cursor_Phone = new CursorLoader(getActivity(), ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?" + " AND " + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?", new String[]{contact_Id, ContactsContract.CommonDataKinds.Phone.TYPE_HOME + ""}, null).loadInBackground();
            }
            try {
                if (cursor_Phone.getCount() != 0) {
                    cursor_Phone.moveToFirst();
                    contact_Phone = cursor_Phone.getString(cursor_Phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
            } finally {
                cursor_Phone.close();
            }
            return contact_Phone;
        } else {
            return null;
        }

    }


    private void updateDate() {
        mDate.setText(mCrime.getDate().toString());
    }
}
