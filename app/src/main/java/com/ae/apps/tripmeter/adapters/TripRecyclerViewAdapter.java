package com.ae.apps.tripmeter.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ae.apps.tripmeter.R;
import com.ae.apps.tripmeter.fragments.TripsListFragment;
import com.ae.apps.tripmeter.models.Trip;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trip} and makes a call to the
 * specified {@link com.ae.apps.tripmeter.fragments.TripsListFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TripRecyclerViewAdapter extends RecyclerView.Adapter<TripRecyclerViewAdapter.ViewHolder> {

    private final List<Trip> mValues;
    private final TripsListFragment.OnListFragmentInteractionListener mListener;

    public TripRecyclerViewAdapter(List<Trip> items, TripsListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTripName.setText(mValues.get(position).getName());
        holder.mTripDate.setText(mValues.get(position).getStartDate());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTripName;
        public final TextView mTripDate;
        public Trip mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTripName = (TextView) view.findViewById(R.id.tripName);
            mTripDate = (TextView) view.findViewById(R.id.tripDate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTripDate.getText() + "'";
        }
    }
}