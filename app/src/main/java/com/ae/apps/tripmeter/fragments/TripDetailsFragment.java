package com.ae.apps.tripmeter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ae.apps.tripmeter.R;
import com.ae.apps.tripmeter.listeners.ExpensesInteractionListener;
import com.ae.apps.tripmeter.managers.ExpenseManager;
import com.ae.apps.tripmeter.models.Trip;
import com.ae.apps.tripmeter.models.TripExpense;
import com.ae.apps.tripmeter.utils.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Activities that contain this fragment must implement the
 * {@link ExpensesInteractionListener} interface
 * to handle interaction events.
 */
public class TripDetailsFragment extends Fragment
        implements AddExpenseDialogFragment.AddExpenseDialogListener {

    private long mTripId;
    private Trip mTrip;
    private ExpenseManager mExpenseManager;
    private LinearLayout mTripMembersContainer;
    private ExpensesInteractionListener mListener;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment TripDetailsFragment.
     */
    public static TripDetailsFragment newInstance() {
        return new TripDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_trip_details, container, false);

        if (null == getArguments() && null == savedInstanceState) {
            throw new IllegalArgumentException("TripId is required");
        }

        if (null != getArguments()) {
            mTripId = getArguments().getLong(AppConstants.KEY_TRIP_ID);
        }

        if (null != savedInstanceState) {
            mTripId = savedInstanceState.getLong(AppConstants.KEY_TRIP_ID);
        }

        initViews(inflatedView);

        return inflatedView;
    }

    private void initViews(View inflatedView) {
        mExpenseManager = ExpenseManager.newInstance(getContext());
        mTrip = mExpenseManager.getTripByTripId(String.valueOf(mTripId));

        // Update the trip with the ContactVos from member ids
        mTrip.getMembers().addAll(mExpenseManager.getContactsFromIds(mTrip.getMemberIds()));

        TextView tripName = (TextView) inflatedView.findViewById(R.id.txtTripName);
        TextView tripDate = (TextView) inflatedView.findViewById(R.id.txtTripDate);
        mTripMembersContainer = (LinearLayout) inflatedView.findViewById(R.id.tripMembersContainer);

        tripName.setText(mTrip.getName());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mTrip.getStartDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.TRIP_DATE_FORMAT, Locale.getDefault());
        tripDate.setText(simpleDateFormat.format(calendar.getTime()));

        FloatingActionButton floatingActionButton = (FloatingActionButton) inflatedView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddExpenseDialog();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(AppConstants.KEY_TRIP_ID, mTripId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExpensesInteractionListener) {
            mListener = (ExpensesInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ExpensesInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * See https://guides.codepath.com/android/Using-DialogFragment#passing-data-to-parent-fragment
     */
    private void showAddExpenseDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        AddExpenseDialogFragment dialogFragment = AddExpenseDialogFragment.newInstance(mTrip);
        dialogFragment.setTargetFragment(TripDetailsFragment.this, 300);
        dialogFragment.show(fragmentManager, "fragment_add_expense");
    }

    @Override
    public void onExpenseAdded(TripExpense tripExpense) {
        mExpenseManager.addExpense(tripExpense);
    }
}
