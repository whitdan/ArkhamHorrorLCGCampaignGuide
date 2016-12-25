package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

import static android.view.View.VISIBLE;

/**
 * Allows selection of a new investigator when one has died.
 */

public class ScenarioNewInvestigatorFragment extends Fragment {

    GlobalVariables globalVariables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scenario_new_investigator, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();
        String[] investigatorNames = getResources().getStringArray(R.array.investigators);

        // Setup first death statement
        if (globalVariables.investigators.size() > 0) {
            if (globalVariables.investigators.get(0).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(0).getName()] + " has died.";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_one_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
            }
        }

        // Setup second death statement
        if (globalVariables.investigators.size() > 1) {
            if (globalVariables.investigators.get(1).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(1).getName()] + " has died.";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_two_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
            }
        }
        // Setup third death statement
        if (globalVariables.investigators.size() > 2) {
            if (globalVariables.investigators.get(2).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(2).getName()] + " has died.";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_three_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
            }
        }

        // Setup fourth death statement
        if (globalVariables.investigators.size() > 3) {
            if (globalVariables.investigators.get(3).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(3).getName()] + " has died.";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_four_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
            }
        }

        int investigators = 5;

        // Setup CheckBoxes with OnCheckedChangeListeners, as long as the investigator is not in use
        if (globalVariables.investigatorsInUse[globalVariables.ROLAND_BANKS] == 0) {
            CheckBox roland = (CheckBox) v.findViewById(R.id.roland_banks);
            roland.setVisibility(VISIBLE);
            roland.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        }else{investigators--;}
        if (globalVariables.investigatorsInUse[globalVariables.SKIDS_OTOOLE] == 0) {
            CheckBox skids = (CheckBox) v.findViewById(R.id.skids_otoole);
            skids.setVisibility(VISIBLE);
            skids.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        }else{investigators--;}
        if (globalVariables.investigatorsInUse[globalVariables.AGNES_BAKER] == 0) {
            CheckBox agnes = (CheckBox) v.findViewById(R.id.agnes_baker);
            agnes.setVisibility(VISIBLE);
            agnes.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        }else{investigators--;}
        if (globalVariables.investigatorsInUse[globalVariables.DAISY_WALKER] == 0) {
            CheckBox daisy = (CheckBox) v.findViewById(R.id.daisy_walker);
            daisy.setVisibility(VISIBLE);
            daisy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        }else{investigators--;}
        if (globalVariables.investigatorsInUse[globalVariables.WENDY_ADAMS] == 0) {
            CheckBox wendy = (CheckBox) v.findViewById(R.id.wendy_adams);
            wendy.setVisibility(VISIBLE);
            wendy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        }else{investigators--;}

        // Show that the investigators have lost if there are no investigators remaining
        if(investigators == 0){
            TextView lost = (TextView) v.findViewById(R.id.choose_investigators);
            lost.setText(R.string.lost);
        }

        // Set onClickListener on the continue button
        TextView continueButton = (TextView) v.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScenarioSetupActivity) getActivity()).restartScenario(getContext());
            }
        });

        return v;
    }

    // Custom OnCheckedChangeListener
    private class InvestigatorsCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{

        private final GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplication();
        private int investigatorsChosen = 0;

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            for (int i = 0; i < globalVariables.investigators.size(); i++){
                if(globalVariables.investigators.get(i).getStatus() == 1){
                    investigatorsChosen++;
                }
            }

            switch(buttonView.getId()){
                case R.id.roland_banks:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(globalVariables.ROLAND_BANKS);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == globalVariables.ROLAND_BANKS){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.skids_otoole:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(globalVariables.SKIDS_OTOOLE);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == globalVariables.SKIDS_OTOOLE){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.agnes_baker:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(globalVariables.AGNES_BAKER);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == globalVariables.AGNES_BAKER){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.daisy_walker:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(globalVariables.DAISY_WALKER);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == globalVariables.DAISY_WALKER){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.wendy_adams:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(globalVariables.WENDY_ADAMS);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == globalVariables.WENDY_ADAMS){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
            }
        }
    }
}