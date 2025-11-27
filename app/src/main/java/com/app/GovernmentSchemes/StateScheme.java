package com.app.GovernmentSchemes;

import androidx.annotation.Nullable;

public enum StateScheme {
    ANDHRA_PRADESH("Andhra Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/ANDHRA%20PRADESH"),
//    ARUNACHAL_PRADESH("Arunachal Pradesh", "https://arunachalipr.gov.in/index.php/new-agriculture-policy-2025-30/"),
    ARUNACHAL_PRADESH("Arunachal Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/ARUNACHAL%20PRADESH"),
    ASSAM("Assam", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/ASSAM"),
    BIHAR("Bihar", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/BIHAR"),
    CHHATTISGARH("Chhattisgarh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/CHHATTISGARH"),
    GOA("Goa", "https://agri.goa.gov.in/wicket/page?4"),
    GUJARAT("Gujarat", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/GUJARAT"),
    HARYANA("Haryana", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/HARYANA"),
    HIMACHAL_PRADESH("Himachal Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/HIMACHAL%20PRADESH"),
    JHARKHAND("Jharkhand", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/JHARKHAND"),
    KARNATAKA("Karnataka", "https://raitamitra.karnataka.gov.in/49/policy/en"),
    KERALA("Kerala", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/KERALA"),
    MADHYA_PRADESH("Madhya Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MADHYA%20PRADESH"),
    MAHARASHTRA("Maharashtra", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MAHARASHTRA"),
    MANIPUR("Manipur", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MANIPUR"),
    MEGHALAYA("Meghalaya", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MEGHALAYA"),
    MIZORAM("Mizoram", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MIZORAM"),
    NAGALAND("Nagaland", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/NAGALAND"),
    ODISHA("Odisha", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/ODISHA"),
    PUNJAB("Punjab", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/PUNJAB"),
    RAJASTHAN("Rajasthan", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/RAJASTHAN"),
    SIKKIM("Sikkim", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/SIKKIM"),
    TAMIL_NADU("Tamil Nadu", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/TAMIL%20NADU"),
    TELANGANA("Telangana", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/TELANGANA"),
    TRIPURA("Tripura", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/TRIPURA"),
    UTTAR_PRADESH("Uttar Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/UTTAR%20PRADESH"),
    UTTARAKHAND("Uttarakhand", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/UTTARAKHAND"),
    WEST_BENGAL("West Bengal", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/WEST%20BENGAL"),
    ANDAMAN_AND_NICOBAR_ISLANDS("Andaman and Nicobar Islands", "https://ddd.gov.in/agriculture-department/"),
    CHANDIGARH("Chandigarh", "https://chandigarhdistrict.nic.in/agriculture/"),
    DADRA_AND_NAGAR_HAVELI_AND_DAMAN_AND_DIU("Dadra and Nagar Haveli and Daman and Diu", "https://ddd.gov.in/agriculture-department/"),
    DELHI("Delhi", "https://development.delhi.gov.in/development/agriculture-unit"),
    JAMMU_AND_KASHMIR("Jammu and Kashmir", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/JAMMU%20AND%20KASHMIR"),
    LADAKH("Ladakh", "https://ladakh.gov.in/agriculture/"),
    LAKSHADWEEP("Lakshadweep", "https://lakshadweep.gov.in/departments/agriculture/"),
    PUDUCHERRY("Puducherry", "https://pdmc.da.gov.in/files/annual-action-plan/AAP202324/Puducherry%20Revised%20AAP%202023-24.pdf"),
    PONDICHERRY("Pondicherry", "https://agri.py.gov.in/Announcements/Postingannounce");


    private final String stateName;
    private final String url;

    StateScheme(String stateName, String url) {
        this.stateName = stateName;
        this.url = url;
    }

    public String getStateName() {
        return stateName;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public static StateScheme fromStateName(String stateName) {
        for (StateScheme scheme : values()) {
            if (scheme.getStateName().equalsIgnoreCase(stateName)) {
                return scheme;
            }
        }
        return null;
    }
}
