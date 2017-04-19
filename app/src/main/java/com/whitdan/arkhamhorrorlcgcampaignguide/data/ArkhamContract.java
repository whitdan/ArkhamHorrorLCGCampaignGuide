package com.whitdan.arkhamhorrorlcgcampaignguide.data;

import android.provider.BaseColumns;

/**
 * Contract for the layout of the SQL tables.
 * Currently there are three tables:
 *          campaigns - contains all global variables
 *          investigators - contains a row per investigator, with all relevant variables
 *          night - contains all variables specific to the Night of the Zealot campaign
 */

public class ArkhamContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ArkhamContract() {}

    /* Inner class that defines the table contents */
    public static class CampaignEntry implements BaseColumns {
        public static final String TABLE_NAME = "campaigns";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CAMPAIGN_NAME = "name";
        public static final String COLUMN_CURRENT_CAMPAIGN = "campaign"; // Denotes which campaign
        public static final String COLUMN_CURRENT_SCENARIO = "scenario";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_NIGHT_COMPLETED = "night_completed";
        public static final String COLUMN_DUNWICH_COMPLETED = "dunwich_completed";
        public static final String COLUMN_ROLAND_INUSE = "roland";
        public static final String COLUMN_DAISY_INUSE = "daisy";
        public static final String COLUMN_SKIDS_INUSE = "skids";
        public static final String COLUMN_AGNES_INUSE ="agnes";
        public static final String COLUMN_WENDY_INUSE = "wendy";
        public static final String COLUMN_ZOEY_INUSE = "zoey";
        public static final String COLUMN_REX_INUSE = "rex";
        public static final String COLUMN_JENNY_INUSE = "jenny";
        public static final String COLUMN_JIM_INUSE = "jim";
        public static final String COLUMN_PETE_INUSE = "pete";
        public static final String COLUMN_ROUGAROU_STATUS = "rougarou_status";
        public static final String COLUMN_STRANGE_SOLUTION = "strange_solution";
        public static final String COLUMN_CARNEVALE_STATUS = "carnevale_status";
        public static final String COLUMN_CARNEVALE_REWARDS = "carnevale_rewards";
    }

    public static class InvestigatorEntry implements BaseColumns{
        public static final String TABLE_NAME = "investigators";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String INVESTIGATOR_ID = "investigator_id";
        public static final String COLUMN_INVESTIGATOR_NAME = "name";
        public static final String COLUMN_INVESTIGATOR_STATUS = "status";
        public static final String COLUMN_INVESTIGATOR_DAMAGE = "damage";
        public static final String COLUMN_INVESTIGATOR_HORROR = "horror";
        public static final String COLUMN_INVESTIGATOR_XP = "xp";
        public static final String COLUMN_INVESTIGATOR_PLAYER = "player";
        public static final String COLUMN_INVESTIGATOR_DECKNAME = "deckname";
        public static final String COLUMN_INVESTIGATOR_DECKLIST = "decklist";
    }

    public static class NightEntry implements BaseColumns{
        public static final String TABLE_NAME = "night";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String COLUMN_HOUSE_STANDING = "house_standing";
        public static final String COLUMN_GHOUL_PRIEST = "ghoul_priest";
        public static final String COLUMN_LITA_STATUS = "lita_status";
        public static final String COLUMN_MIDNIGHT_STATUS = "midnight_status";
        public static final String COLUMN_CULTISTS_INTERROGATED = "cultists_interrogated";
        public static final String COLUMN_DREW_INTERROGATED = "drew_interrogated";
        public static final String COLUMN_PETER_INTERROGATED = "peter_interrogated";
        public static final String COLUMN_HERMAN_INTERROGATED = "herman_interrogated";
        public static final String COLUMN_VICTORIA_INTERROGATED = "victoria_interrogated";
        public static final String COLUMN_RUTH_INTERROGATED = "ruth_interrogated";
        public static final String COLUMN_MASKED_INTERROGATED = "masked_interrogated";
        public static final String COLUMN_UMORDHOTH_STATUS = "umordhoth_status";
    }

    public static class DunwichEntry implements BaseColumns{
        public static final String TABLE_NAME = "dunwich";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String COLUMN_FIRST_SCENARIO = "first_scenario";
        public static final String COLUMN_INVESTIGATORS_UNCONSCIOUS = "investigators_unconscious";
        public static final String COLUMN_HENRY_ARMITAGE = "henry_armitage";
        public static final String COLUMN_WARREN_RICE = "warren_rice";
        public static final String COLUMN_STUDENTS = "students";
        public static final String COLUMN_OBANNION_GANG = "obannion_gang";
        public static final String COLUMN_FRANCIS_MORGAN = "francis_morgan";
        public static final String COLUMN_INVESTIGATORS_CHEATED = "investigators_cheated";
        public static final String COLUMN_NECRONOMICON = "necronomicon";
        public static final String COLUMN_ADAM_LYNCH_HAROLD_WALSTED = "adam_lynch_harold_walsted";
        public static final String COLUMN_DELAYED = "delayed";
        public static final String COLUMN_SILAS_BISHOP = "silas_bishop";
        public static final String COLUMN_ZEBULON_WHATELEY = "zebulon_whateley";
        public static final String COLUMN_EARL_SAWYER = "earl_sawyer";
        public static final String COLUMN_ALLY_SACRIFICED = "ally_sacrificed";
    }
}
