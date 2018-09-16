package com.example.mac_os.criminalintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;

import java.util.List;

public class CrimeListFragment extends Fragment {

    RecyclerView recycle_list_crime;
    List<Crime> mListCrime;
    CrimeAdapter crimeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        recycle_list_crime = v.findViewById(R.id.recycle_list_crime);
        recycle_list_crime.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

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

        public CrimeHolder(View itemView) {
            super(itemView);
            getItemView(itemView);
        }

        public View getItemView(View ItemView) {
            mTextViewTitle = (TextView) itemView.findViewById(R.id.txt_crime_title);
            mTextViewDate = (TextView) itemView.findViewById(R.id.txt_crime_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "YOU Pressed " + mCrimeholder.getTitle(), Toast.LENGTH_LONG).show();
                }
            });
            return itemView;
        }

        public void Bind(Crime crime) {
            mCrimeholder = crime;
            mTextViewTitle.setText(crime.getTitle());
            mTextViewDate.setText(crime.getDate().toString());
        }

    }

    private class CrimeRequirePolice extends RecyclerView.ViewHolder {
        TextView mTextViewTitle;
        TextView mTextViewDate;
        Button mButtonContactPolice;
        Crime mCrimeholder;

        public CrimeRequirePolice(View itemView) {
            super(itemView);
            getItemView(itemView);
        }


        public View getItemView(View ItemView) {
            mTextViewTitle = (TextView) itemView.findViewById(R.id.txt_crime_title);
            mTextViewDate = (TextView) itemView.findViewById(R.id.txt_crime_date);
            mButtonContactPolice = (Button) itemView.findViewById(R.id.requirePolice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "YOU Pressed " + mCrimeholder.getTitle(), Toast.LENGTH_LONG).show();
                }
            });

            return itemView;
        }

        public void Bind(Crime crime) {
            mCrimeholder = crime;
            mTextViewTitle.setText(crime.getTitle());
            mTextViewDate.setText(crime.getDate().toString());
        }

    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Crime> mCrimes;
        CrimeHolder mCrimeHolder;
        CrimeRequirePolice mCrimeRequirePolice;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequirePolice()) {
                return 1;
            } else {
                return 0;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 1) {
                View v = getLayoutInflater().inflate(R.layout.list_item_crime_police, parent, false);
                return new CrimeRequirePolice(v);
            } else {
                View v = getLayoutInflater().inflate(R.layout.list_item_crime, parent, false);
                return new CrimeHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == 1) {
                mCrimeRequirePolice = new CrimeRequirePolice(holder.itemView);
                mCrimeRequirePolice.Bind(mCrimes.get(position));
            } else {
                mCrimeHolder = new CrimeHolder(holder.itemView);
                mCrimeHolder.Bind(mCrimes.get(position));
            }
        }


        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}
