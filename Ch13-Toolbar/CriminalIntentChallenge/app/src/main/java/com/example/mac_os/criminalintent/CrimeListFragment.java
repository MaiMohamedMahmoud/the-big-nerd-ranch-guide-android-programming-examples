package com.example.mac_os.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeListFragment extends Fragment {

    RecyclerView recycle_list_crime;
    LinearLayout linear_no_crime;
    Button btn_newCrime;
    List<Crime> mListCrime;
    CrimeAdapter crimeAdapter;
    public static String mCrimeID = "crimeID";
    int mRequestCode = 0;
    CrimeLab crimeLab;
    boolean subtitleVisible;
    public static String save_Subtitle = "subtitle";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        recycle_list_crime = v.findViewById(R.id.recycle_list_crime);
        linear_no_crime = v.findViewById(R.id.view_no_crime);
        recycle_list_crime.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_newCrime = (Button) v.findViewById(R.id.new_crime);
        crimeLab = CrimeLab.getInstance(getContext());
        if (savedInstanceState != null) {
            subtitleVisible = savedInstanceState.getBoolean(save_Subtitle);
        }
        updateUI();
        /**
         * you have to call sethasoptionMenu beacause in the fragment case the fragment manger is the responsible for calling
         * setHasOptionsMenu when the Os call onCreateOptionsMenu on the activity you must tell he fragment manager expilicty that
         * you want it to call onCreateOptionsMenu
         */
        setHasOptionsMenu(true);
        btn_newCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCrime();
            }
        });
        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(save_Subtitle, subtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem newCrimeItem = menu.findItem(R.id.new_crime);
        newCrimeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                addCrime();
                return true;
            }
        });
        MenuItem Show_title = menu.findItem(R.id.show_subtitle);
        Show_title.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                subtitleVisible = !subtitleVisible;
                if (subtitleVisible) {
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    item.setTitle(R.string.show_subtitle);
                }
                updateSubtitle();

                return true;
            }
        });
    }

    public void addCrime() {
        Crime crime = new Crime();
        crimeLab.addCrime(crime);
        Intent i = new CrimePagerActivity().newIntent(getContext(), crime.getId());
        startActivity(i);
    }

    public void updateSubtitle() {
        int CountOfCrime = crimeLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtiltle_plural, CountOfCrime, CountOfCrime);
        Toast.makeText(getContext(), "d" + subtitleVisible, Toast.LENGTH_LONG).show();
        if (!subtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    /**
     * updateUI is function only used to getInstance's the array from the abstract class (CrimeLab)
     * and create instance of the adapter to use it in recycle view set adapter.
     */
    private void updateUI() {
        mListCrime = crimeLab.getCrimes();
        if (mListCrime.size() == 0) {
            linear_no_crime.setVisibility(View.VISIBLE);
        } else {
            linear_no_crime.setVisibility(View.INVISIBLE);
        }
        if (crimeAdapter == null) {
            crimeAdapter = new CrimeAdapter(mListCrime);
            recycle_list_crime.setAdapter(crimeAdapter);
        } else {
            crimeAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void changeItem(Intent data) {
        UUID item = (UUID) data.getSerializableExtra(mCrimeID);
        Crime crime = crimeLab.getCrime(item);
        crimeAdapter.notifyItemChanged(mListCrime.indexOf(crime));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != getActivity().RESULT_OK) {
            return;
        }

        if (requestCode == mRequestCode) {
            if (data == null) {
                return;
            }
            changeItem(data);
        }


    }

    private class CrimeHolder extends RecyclerView.ViewHolder {

        TextView mTextViewTitle;
        TextView mTextViewDate;
        Crime mCrimeholder;
        ImageView isSolvedImageView;

        public CrimeHolder(View itemView) {
            super(itemView);
            getItemView(itemView);
        }

        /**
         * @param ItemView that we getInstance from OnCreateViewHolder function in the Adapter
         * @return the View for each row
         */

        public View getItemView(View ItemView) {
            mTextViewTitle = (TextView) itemView.findViewById(R.id.txt_crime_title);
            mTextViewDate = (TextView) itemView.findViewById(R.id.txt_crime_date);
            isSolvedImageView = (ImageView) itemView.findViewById(R.id.imageView_isSolved);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new CrimePagerActivity().newIntent(getActivity(), mCrimeholder.getId());
                    startActivityForResult(i, mRequestCode);
                }
            });
            return itemView;
        }

        /**
         * Created By Mai Mohamed
         *
         * @param d is the date that is passed from the crime.getDate
         * @return String of date formatted that will appear in the text view
         * The Challenge of Chapter 9 is to make date format Like what this function do
         */

        public String getDateByUserFormat(Date d) {
            SimpleDateFormat formatter
                    = new SimpleDateFormat("EEEE,MMM dd,yyyy 'at' hh:mm:ss a");
            //DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL);
            return formatter.format(d);
        }

        /**
         * @param crime this is every crime in the array ..
         *              we use this function to bind the data to each row in the recycle view to view all the array of crime we had.
         */
        public void Bind(Crime crime) {
            mCrimeholder = crime;
            mTextViewTitle.setText(crime.getTitle());
            mTextViewDate.setText(getDateByUserFormat(crime.getDate()));
            if (!crime.isSolved()) {
                isSolvedImageView.setVisibility(View.INVISIBLE);
            } else {
                isSolvedImageView.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * In each Adapter we call three function (Override)
     * 1- getItemCount      ---- return the number of array crime in this example (so we know how many time onBindViewHolder will be called)
     * 2- onCreateViewHolder --- return the view holder
     * 3- onBindViewHolder ---- use the view holder to bind the data for each row
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        /**
         * @param parent
         * @param viewType
         * @return viewholder that the adapter create
         * So the adapter is responsible for creating ViewHolder that contain the itemview for each row
         * adapter is controller that lay between the recycle view and the data
         * that's because the recycle view couldn't create viewholder by itself so it ask the adapter to
         * create it.
         */
        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(v);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            holder.Bind(mCrimes.get(position));

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}
