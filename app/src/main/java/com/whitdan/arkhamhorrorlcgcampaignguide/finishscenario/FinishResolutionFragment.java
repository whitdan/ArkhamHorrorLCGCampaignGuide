package com.whitdan.arkhamhorrorlcgcampaignguide.finishscenario;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.ContinueOnClickListener;
import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup.ScenarioInvestigatorsFragment;
import com.whitdan.arkhamhorrorlcgcampaignguide.standalone.StandaloneOnClickListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Allows the user to select the relevant resolution and any additional necessary information to resolve a scenario
 * (including the value of the victory display)
 */

public class FinishResolutionFragment extends Fragment {

    private GlobalVariables globalVariables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_finish_resolution, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();

        /*
         Setup resolution choice spinner
        */
        Spinner resolutionSpinner = (Spinner) v.findViewById(R.id.resolution_selection);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.resolutions_three, android.R.layout.simple_spinner_item);

        // Setup the right number of resolutions in the adapter
        switch (globalVariables.getCurrentCampaign()) {
            // Night of the Zealot
            case 1:
                switch (globalVariables.getCurrentScenario()) {
                    case 1:
                    case 3:
                        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                                R.array.resolutions_three, android.R.layout.simple_spinner_item);
                        break;
                    case 2:
                        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                                R.array.resolutions_two, android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            // The Dunwich Legacy
            case 2:
                switch (globalVariables.getCurrentScenario()) {
                    case 1:
                    case 2:
                        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                                R.array.resolutions_four, android.R.layout.simple_spinner_item);
                        break;
                    case 4:
                    case 5:
                        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                                R.array.resolutions_two, android.R.layout.simple_spinner_item);
                        break;
                }
                break;
        }
        // Side stories
        if (globalVariables.getCurrentScenario() > 100) {
            switch (globalVariables.getCurrentScenario()) {
                case 101:
                    adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.resolutions_three, android.R.layout.simple_spinner_item);
                    break;
                case 102:
                    adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.resolutions_two, android.R.layout.simple_spinner_item);
                    break;

            }
        }
        // Set the layout, adapter and OnItemSelectedListener to the spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resolutionSpinner.setAdapter(adapter);
        resolutionSpinner.setOnItemSelectedListener(new ResolutionSpinnerListener());

        /*
         Setup victory display view
        */
        // Set value to the current victory display amount
        final TextView victoryDisplay = (TextView) v.findViewById(R.id.victory_display);
        victoryDisplay.setText(String.valueOf(globalVariables.getVictoryDisplay()));
        // Setup decrement button to reduce the victory display value and display the new amount
        Button victoryDecrement = (Button) v.findViewById(R.id.victory_decrement);
        victoryDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = globalVariables.getVictoryDisplay();
                if (current > 0) {
                    globalVariables.setVictoryDisplay(current - 1);
                    victoryDisplay.setText(String.valueOf(globalVariables.getVictoryDisplay()));
                }
            }
        });
        // Setup increment button to increase the victory display value and display the new amount
        Button victoryIncrement = (Button) v.findViewById(R.id.victory_increment);
        victoryIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = globalVariables.getVictoryDisplay();
                if (current < 99) {
                    globalVariables.setVictoryDisplay(current + 1);
                    victoryDisplay.setText(String.valueOf(globalVariables.getVictoryDisplay()));
                }
            }
        });

        // If on first campaign, second scenario, set cultists view to visible
        if (globalVariables.getCurrentCampaign() == 1 && globalVariables.getCurrentScenario() == 2) {
            LinearLayout cultists = (LinearLayout) v.findViewById(R.id.cultists_interrogated);
            cultists.setVisibility(VISIBLE);
        }

        // If on first campaign, second or third scenario and Ghoul Priest is alive, set ghoul priest view to visible
        if (globalVariables.getCurrentCampaign() == 1 && globalVariables.getCurrentScenario() > 1 && globalVariables
                .getGhoulPriestAlive() == 1 && globalVariables.getCurrentScenario() < 100) {
            CheckBox ghoulPriest = (CheckBox) v.findViewById(R.id.additional_checkbox_one);
            ghoulPriest.setVisibility(VISIBLE);
        }

        // If on second campaign, second scenario, set cheated view to visible
        if (globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 2) {
            CheckBox cheated = (CheckBox) v.findViewById(R.id.additional_checkbox_two);
            final TextView cheatedText = (TextView) v.findViewById(R.id.cheated_text);
            cheated.setVisibility(VISIBLE);
            cheated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cheatedText.setVisibility(VISIBLE);
                    }
                    if (!isChecked) {
                        cheatedText.setVisibility(GONE);
                    }
                }
            });
        }

        // If on second campaign, third scenario, set additional checkboxes to visible
        if (globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 4) {
            CheckBox lynchwalsted = (CheckBox) v.findViewById(R.id.additional_checkbox_one);
            globalVariables.setAdamLynchHaroldWalsted(0);
            lynchwalsted.setVisibility(VISIBLE);
            lynchwalsted.setText(R.string.adam_lynch_harold_walsted);
            lynchwalsted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        globalVariables.setAdamLynchHaroldWalsted(1);
                    } else {
                        globalVariables.setAdamLynchHaroldWalsted(0);
                    }
                }
            });
        }

        // If on second campaign, fourth scenario, set engine car view to visible
        if (globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 5) {
            final CheckBox engine = (CheckBox) v.findViewById(R.id.engine_car_checkbox);
            final LinearLayout engineLayout = (LinearLayout) v.findViewById(R.id.engine_car_view);
            final CheckBox engineDodge = (CheckBox) v.findViewById(R.id.engine_car_dodge);
            final CheckBox engineEndure = (CheckBox) v.findViewById(R.id.engine_car_endure);
            engine.setVisibility(VISIBLE);
            globalVariables.setEngineCar(0);
            engine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        engineLayout.setVisibility(VISIBLE);
                        Spinner engineInvestigator = (Spinner) v.findViewById(R.id.engine_car_investigator);

                        // Setup spinner
                        ArrayAdapter<String> investigatorAdapter = new ArrayAdapter<>(getContext(), android.R.layout
                                .simple_spinner_item, ScenarioInvestigatorsFragment.investigatorNames);
                        engineInvestigator.setAdapter(investigatorAdapter);
                        investigatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        engineInvestigator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                globalVariables.setEngineInvestigator(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        // Setup checkboxes
                        engineDodge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    engineEndure.setChecked(false);
                                    globalVariables.setEngineCar(1);
                                }
                                if (!isChecked) {
                                    globalVariables.setEngineCar(0);
                                }
                            }
                        });
                        engineEndure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    engineDodge.setChecked(false);
                                    globalVariables.setEngineCar(2);
                                }
                                if (!isChecked) {
                                    globalVariables.setEngineCar(0);
                                }
                            }
                        });
                    }
                    if (!isChecked) {
                        engineLayout.setVisibility(GONE);
                        engineDodge.setChecked(false);
                        engineEndure.setChecked(false);
                        globalVariables.setEngineCar(0);
                    }
                }
            });
        }

        // If on second campaign, fourth scenario, set sacrificed view to visible
        if (globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 6) {
            LinearLayout sacrificed = (LinearLayout) v.findViewById(R.id.sacrificed_yog);
            sacrificed.setVisibility(VISIBLE);
        }

        // If on Carnevale of Horrors, set rewards display to visible and setup rewards display
        if (globalVariables.getCurrentScenario() == 102) {
            final LinearLayout rewards = (LinearLayout) v.findViewById(R.id.rewards_view);
            rewards.setVisibility(VISIBLE);
            Spinner rewardsSpinner = (Spinner) v.findViewById(R.id.rewards_selection);
            ArrayAdapter<CharSequence> rewardsAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                    R.array.carnevale_rewards, android.R.layout.simple_spinner_item);
            rewardsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            rewardsSpinner.setAdapter(rewardsAdapter);
            rewardsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView rewardsText = (TextView) v.findViewById(R.id.rewards_text);
                    switch (position) {
                        case 0:
                            rewardsText.setText(R.string.carnevale_no_rewards);
                            globalVariables.setCarnevaleReward(0);
                            break;
                        case 1:
                            rewardsText.setText(R.string.carnevale_reward_sacrifice);
                            globalVariables.setCarnevaleReward(1);
                            break;
                        case 2:
                            rewardsText.setText(R.string.carnevale_reward_abbess);
                            globalVariables.setCarnevaleReward(2);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        // Standalone scenario
        if (globalVariables.getCurrentCampaign() == 999) {
            // Hide victory display
            LinearLayout victory = (LinearLayout) v.findViewById(R.id.victory_layout);
            victory.setVisibility(GONE);
            // Set click listener on continue button
            TextView button = (TextView) v.findViewById(R.id.continue_button);
            button.setOnClickListener(new StandaloneOnClickListener(this.getActivity()));
        } else {
            // Set continue button click listener
            TextView button = (TextView) v.findViewById(R.id.continue_button);
            button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity(), this
                    .getActivity()));
        }

        return v;
    }


    /*
     OnItemSelectedListener for the Resolution Spinner - Applies relevant resolution text and sets the resolution value
      */
    private class ResolutionSpinnerListener extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            TextView resolutionText = (TextView) view.getRootView().findViewById(R.id.resolution_text);
            switch (globalVariables.getCurrentCampaign()) {
                // Night of the Zealot
                case 1:
                    // Scenario One - The Gathering
                    if (globalVariables.getCurrentScenario() == 1) {
                        switch (pos) {
                            // No resolution
                            case 0:
                                resolutionText.setText(R.string.gathering_no_resolution);
                                globalVariables.setResolution(0);
                                break;
                            // Resolution one
                            case 1:
                                resolutionText.setText(R.string.gathering_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            // Resolution two
                            case 2:
                                resolutionText.setText(R.string.gathering_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                            // Resolution three
                            case 3:
                                resolutionText.setText(R.string.gathering_resolution_three);
                                globalVariables.setResolution(3);
                                break;
                        }
                    }
                    // Scenario Two - The Midnight Masks
                    else if (globalVariables.getCurrentScenario() == 2) {
                        switch (pos) {
                            // No resolution
                            case 0:
                                resolutionText.setText(R.string.midnight_resolution_one);
                                globalVariables.setResolution(0);
                                break;
                            case 1:
                                resolutionText.setText(R.string.midnight_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            case 2:
                                resolutionText.setText(R.string.midnight_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                        }
                    }
                    // Scenario Three - The Devourer Below
                    else if (globalVariables.getCurrentScenario() == 3) {
                        switch (pos) {
                            // No resolution
                            case 0:
                                resolutionText.setText(R.string.devourer_no_resolution);
                                globalVariables.setResolution(0);
                                break;
                            // Resolution one
                            case 1:
                                resolutionText.setText(R.string.devourer_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            // Resolution two
                            case 2:
                                resolutionText.setText(R.string.devourer_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                            // Resolution three
                            case 3:
                                resolutionText.setText(R.string.devourer_resolution_three);
                                globalVariables.setResolution(3);
                                break;
                        }
                    }
                    break;
                // The Dunwich Legacy
                case 2:
                    switch (globalVariables.getCurrentScenario()) {
                        // Scenario 1-A: Extracurricular Activity
                        case 1:
                            switch (pos) {
                                // No resolution
                                case 0:
                                    resolutionText.setText(R.string.extracurricular_no_resolution);
                                    globalVariables.setResolution(0);
                                    break;
                                // Resolution One
                                case 1:
                                    resolutionText.setText(R.string.extracurricular_resolution_one);
                                    globalVariables.setResolution(1);
                                    break;
                                // Resolution Two
                                case 2:
                                    resolutionText.setText(R.string.extracurricular_resolution_two);
                                    globalVariables.setResolution(2);
                                    break;
                                // Resolution Three
                                case 3:
                                    resolutionText.setText(R.string.extracurricular_resolution_three);
                                    globalVariables.setResolution(3);
                                    break;
                                // Resolution Four
                                case 4:
                                    resolutionText.setText(R.string.extracurricular_resolution_four);
                                    globalVariables.setResolution(4);
                                    break;
                            }
                            break;
                        // Scenario 1-B: The House Always Wins
                        case 2:
                            switch (pos) {
                                // No resolution
                                case 0:
                                    resolutionText.setText(R.string.house_resolution_one);
                                    globalVariables.setResolution(1);
                                    break;
                                // Resolution One
                                case 1:
                                    resolutionText.setText(R.string.house_resolution_one);
                                    globalVariables.setResolution(1);
                                    break;
                                // Resolution Two
                                case 2:
                                    resolutionText.setText(R.string.house_resolution_two);
                                    globalVariables.setResolution(2);
                                    break;
                                // Resolution Three
                                case 3:
                                    resolutionText.setText(R.string.house_resolution_three);
                                    globalVariables.setResolution(3);
                                    break;
                                // Resolution Four
                                case 4:
                                    resolutionText.setText(R.string.house_resolution_four);
                                    globalVariables.setResolution(4);
                                    break;
                            }
                            break;
                        // Scenario 2: The Miskatonic Museum
                        case 4:
                            switch (pos) {
                                // No resolution
                                case 0:
                                    resolutionText.setText(R.string.miskatonic_no_resolution);
                                    globalVariables.setResolution(0);
                                    break;
                                // Resolution One
                                case 1:
                                    resolutionText.setText(R.string.miskatonic_resolution_one);
                                    globalVariables.setResolution(1);
                                    break;
                                // Resolution Two
                                case 2:
                                    resolutionText.setText(R.string.miskatonic_resolution_two);
                                    globalVariables.setResolution(2);
                                    break;
                            }
                            break;
                        // Scenario 3: The Essex County Express
                        case 5:
                            CheckBox necronomicon = (CheckBox) view.getRootView().findViewById(R.id
                                    .necronomicon_defeated);
                            final TextView removeNecronomicon = (TextView) view.getRootView().findViewById(R.id
                                    .remove_necronomicon);
                            CheckBox morgan = (CheckBox) view.getRootView().findViewById(R.id
                                    .morgan_defeated);
                            final TextView removeMorgan = (TextView) view.getRootView().findViewById(R.id
                                    .remove_morgan);
                            CheckBox armitage = (CheckBox) view.getRootView().findViewById(R.id
                                    .armitage_defeated);
                            final TextView removeArmitage = (TextView) view.getRootView().findViewById(R.id
                                    .remove_armitage);
                            CheckBox rice = (CheckBox) view.getRootView().findViewById(R.id
                                    .rice_defeated);
                            final TextView removeRice = (TextView) view.getRootView().findViewById(R.id
                                    .remove_rice);
                            switch (pos) {
                                // Resolution One
                                case 1:
                                    int investigatorDefeated = 0;
                                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                                        if (globalVariables.investigators.get(i).getTempStatus() > 1) {
                                            investigatorDefeated = 1;
                                        }
                                    }
                                    if (investigatorDefeated == 1) {
                                        resolutionText.setText(R.string.essex_resolution_one_defeated);
                                        removeNecronomicon.setVisibility(GONE);
                                        removeMorgan.setVisibility(GONE);
                                        removeArmitage.setVisibility(GONE);
                                        removeRice.setVisibility(GONE);
                                        if (globalVariables.getNecronomicon() == 2) {
                                            necronomicon.setVisibility(VISIBLE);
                                            necronomicon.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean
                                                        isChecked) {
                                                    if (isChecked) {
                                                        removeNecronomicon.setVisibility(VISIBLE);
                                                    } else {
                                                        removeNecronomicon.setVisibility(GONE);
                                                    }
                                                }
                                            });
                                        }
                                        if (globalVariables.getFrancisMorgan() == 1) {
                                            morgan.setVisibility(VISIBLE);
                                            morgan.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean
                                                        isChecked) {
                                                    if (isChecked) {
                                                        removeMorgan.setVisibility(VISIBLE);
                                                    } else {
                                                        removeMorgan.setVisibility(GONE);
                                                    }
                                                }
                                            });
                                        }
                                        if (globalVariables.getHenryArmitage() == 1) {
                                            armitage.setVisibility(VISIBLE);
                                            armitage.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean
                                                        isChecked) {
                                                    if (isChecked) {
                                                        removeArmitage.setVisibility(VISIBLE);
                                                    } else {
                                                        removeArmitage.setVisibility(GONE);
                                                    }
                                                }
                                            });
                                        }
                                        if (globalVariables.getWarrenRice() == 1) {
                                            rice.setVisibility(VISIBLE);
                                            rice.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean
                                                        isChecked) {
                                                    if (isChecked) {
                                                        removeRice.setVisibility(VISIBLE);
                                                    } else {
                                                        removeRice.setVisibility(GONE);
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        resolutionText.setText(R.string.essex_resolution_one);
                                        necronomicon.setVisibility(GONE);
                                        morgan.setVisibility(GONE);
                                        armitage.setVisibility(GONE);
                                        rice.setVisibility(GONE);
                                        removeNecronomicon.setVisibility(GONE);
                                        removeMorgan.setVisibility(GONE);
                                        removeArmitage.setVisibility(GONE);
                                        removeRice.setVisibility(GONE);
                                    }
                                    globalVariables.setResolution(1);
                                    break;
                                // No resolution or Resolution Two
                                case 0:
                                case 2:
                                    resolutionText.setText(R.string.essex_resolution_two);
                                    globalVariables.setResolution(2);
                                    necronomicon.setVisibility(GONE);
                                    morgan.setVisibility(GONE);
                                    armitage.setVisibility(GONE);
                                    rice.setVisibility(GONE);
                                    if (globalVariables.getNecronomicon() == 2) {
                                        removeNecronomicon.setVisibility(VISIBLE);
                                    } else {
                                        removeNecronomicon.setVisibility(GONE);
                                    }
                                    if (globalVariables.getFrancisMorgan() == 1) {
                                        removeMorgan.setVisibility(VISIBLE);
                                    } else {
                                        removeMorgan.setVisibility(GONE);
                                    }
                                    if (globalVariables.getHenryArmitage() == 1) {
                                        removeArmitage.setVisibility(VISIBLE);
                                    } else {
                                        removeArmitage.setVisibility(GONE);
                                    }
                                    if (globalVariables.getWarrenRice() == 1) {
                                        removeRice.setVisibility(VISIBLE);
                                    } else {
                                        removeRice.setVisibility(GONE);
                                    }
                                    break;
                            }
                            break;
                        // Scenario 4: Blood on the Altar
                        case 6:
                            if (pos != 2) {
                                CheckBox necronomiconBox = (CheckBox) view.getRootView().findViewById(R.id
                                        .necronomicon_defeated);
                                final TextView removeNecronomiconBox = (TextView) view.getRootView().findViewById(R.id
                                        .remove_necronomicon);
                                int investigatorDefeated = 0;
                                for (int i = 0; i < globalVariables.investigators.size(); i++) {
                                    if (globalVariables.investigators.get(i).getTempStatus() > 1) {
                                        investigatorDefeated = 1;
                                    }
                                }
                                if (investigatorDefeated == 1) {
                                    if (globalVariables.getNecronomicon() == 2) {
                                        necronomiconBox.setVisibility(VISIBLE);
                                        necronomiconBox.setOnCheckedChangeListener(new CompoundButton
                                                .OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView, boolean
                                                    isChecked) {
                                                if (isChecked) {
                                                    removeNecronomiconBox.setVisibility(VISIBLE);
                                                } else {
                                                    removeNecronomiconBox.setVisibility(GONE);
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                            switch (pos) {
                                case 0:
                                    resolutionText.setText(R.string.blood_no_resolution);
                                    globalVariables.setResolution(0);
                                    break;
                                case 1:
                                    resolutionText.setText(R.string.blood_resolution_one);
                                    globalVariables.setResolution(1);
                                    break;
                                case 2:
                                    resolutionText.setText(R.string.blood_resolution_two);
                                    globalVariables.setResolution(2);
                                    break;
                                case 3:
                                    resolutionText.setText(R.string.blood_resolution_three);
                                    globalVariables.setResolution(3);
                                    break;
                            }
                            break;
                    }
                    break;
            }
            // Side stories
            if (globalVariables.getCurrentScenario() > 100) {
                switch (globalVariables.getCurrentScenario()) {
                    case 101:
                        switch (pos) {
                            case 0:
                            case 1:
                                resolutionText.setText(R.string.rougarou_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            case 2:
                                resolutionText.setText(R.string.rougarou_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                            case 3:
                                resolutionText.setText(R.string.rougarou_resolution_three);
                                globalVariables.setResolution(3);
                                break;
                        }
                        break;
                    case 102:
                        switch (pos) {
                            case 0:
                                resolutionText.setText(R.string.carnevale_no_resolution);
                                globalVariables.setResolution(0);
                                break;
                            case 1:
                                resolutionText.setText(R.string.carnevale_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            case 2:
                                resolutionText.setText(R.string.carnevale_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                        }
                }
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}