package com.example.mac_os.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CrimeListFragment extends Fragment {

    RecyclerView recycle_list_crime;
    List<Crime> mListCrime;
    CrimeAdapter crimeAdapter;
    int mRequestCode = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        recycle_list_crime = v.findViewById(R.id.recycle_list_crime);
        recycle_list_crime.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    /**
     * updateUI is function only used to get's the array from the abstract class (CrimeLab)
     * and create instance of the adapter to use it in recycle view set adapter.
     */
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getContext());
        mListCrime = crimeLab.getCrimes();
        crimeAdapter = new CrimeAdapter(mListCrime);
        recycle_list_crime.setAdapter(crimeAdapter);
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
         *
         * @param ItemView that we get from OnCreateViewHolder function in the Adapter
         * @return the View for each row
         */

        public View getItemView(View ItemView) {
            mTextViewTitle = (TextView) itemView.findViewById(R.id.txt_crime_title);
            mTextViewDate = (TextView) itemView.findViewById(R.id.txt_crime_date);
            isSolvedImageView = (ImageView) itemView.findViewById(R.id.imageView_isSolved);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext(), "YOU Pressed " + mCrimeholder.getTitle(), Toast.LENGTH_LONG).show();
//
//                    Toast.makeText(getActivity(),"Crime id is "+mCrimeholder.getId(),Toast.LENGTH_LONG).show();

                    Intent i = new CrimeActivity().NewIntent(getActivity(),mCrimeholder.getId());
                    startActivityForResult(i,mRequestCode);
                }
            });
            return itemView;
        }

        /**
         * Created By Mai Mohamed
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
         *
         * @param crime this is every crime in the array ..
         *   we use this function to bind the data to each row in the recycle view to view all the array of crime we had.
         */
        public void Bind(Crime crime) {
            mCrimeholder = crime;
            mTextViewTitle.setText(crime.getTitle());
            mTextViewDate.setText(getDateByUserFormat(crime.getDate()));
            if (!crime.isSolved()) {
                isSolvedImageView.setVisibility(View.INVISIBLE);
            }
        }

    }

    /**
     * In each Adapter we call three function (Override)
     *  1- getItemCount      ---- return the number of array crime in this example (so we know how many time onBindViewHolder will be called)
     *  2- onCreateViewHolder --- return the view holder
     *  3- onBindViewHolder ---- use the view holder to bind the data for each row
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        /**
         *
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
