package com.ae.apps.tripmeter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ae.apps.tripmeter.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final String FORMAT = "%.02f";
    private EditText txtTripDistance;
    private EditText txtFuelPrice;
    private EditText txtMileage;
    private TextView lblTotalCost;
    private TextView lblFuelNeeded;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_main, container, false);

        // Find text inputs and labels to show result
        txtTripDistance = (EditText) inflatedView.findViewById(R.id.txtDistance);
        txtFuelPrice = (EditText) inflatedView.findViewById(R.id.txtFuelPrice);
        txtMileage = (EditText) inflatedView.findViewById(R.id.txtMileage);
        lblFuelNeeded = (TextView) inflatedView.findViewById(R.id.lblFuelNeeded);
        lblTotalCost = (TextView) inflatedView.findViewById(R.id.lblTotalCost);

        // Find and handle Calculate button clicks
        Button calculate = (Button) inflatedView.findViewById(R.id.btnCalculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndCalculateTripCosts();
            }
        });

        // TODO Read last saved mileage and fuel price

        return inflatedView;
    }

    private void validateAndCalculateTripCosts() {
        if(!TextUtils.isEmpty(txtTripDistance.getText().toString())
                && !TextUtils.isEmpty(txtFuelPrice.getText().toString())
                && !TextUtils.isEmpty(txtMileage.getText().toString())){

            // Process data and make the calculations
            float tripDistance = Float.parseFloat(txtTripDistance.getText().toString());
            float fuelPrice = Float.parseFloat(txtFuelPrice.getText().toString());
            float mileage = Float.parseFloat(txtMileage.getText().toString());

            // Check for mileage greater than 0
            if(mileage > 0){
                float fuelNeeded =  tripDistance / mileage;
                float totalFuelPrice = fuelPrice * fuelNeeded;

                // Update labels in result card view
                lblFuelNeeded.setText( getResources().getString(
                        R.string.str_total_fuel_needed, String.format(FORMAT, fuelNeeded)));
                lblTotalCost.setText(getResources().getString(
                        R.string.str_total_fuel_price, String.format(FORMAT, totalFuelPrice)));

                // TODO Store Mileage and Current Fuel Price
            } else {
                Toast.makeText(getActivity().getBaseContext(),
                        R.string.str_error_no_mileage, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity().getBaseContext(),
                    R.string.str_error_empty_inputs, Toast.LENGTH_LONG).show();
        }
    }
}