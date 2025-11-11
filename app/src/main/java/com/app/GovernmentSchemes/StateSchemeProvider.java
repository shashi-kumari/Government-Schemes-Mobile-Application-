package com.app.GovernmentSchemes;

import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class StateSchemeProvider {
    
    private static final Map<String, String> AGRICULTURE_URLS = new HashMap<>();
    private static final Map<String, String> BANKING_URLS = new HashMap<>();
    private static final Map<String, String> BUSINESS_URLS = new HashMap<>();
    private static final Map<String, String> EDUCATION_URLS = new HashMap<>();
    private static final Map<String, String> HEALTH_URLS = new HashMap<>();
    private static final Map<String, String> HOUSING_URLS = new HashMap<>();
    
    static {
        // Agriculture URLs
        AGRICULTURE_URLS.put("Andhra Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/ANDHRA%20PRADESH");
        AGRICULTURE_URLS.put("Arunachal Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/ARUNACHAL%20PRADESH");
        AGRICULTURE_URLS.put("Assam", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/ASSAM");
        AGRICULTURE_URLS.put("Bihar", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/BIHAR");
        AGRICULTURE_URLS.put("Chhattisgarh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/CHHATTISGARH");
        AGRICULTURE_URLS.put("Goa", "https://agri.goa.gov.in/wicket/page?4");
        AGRICULTURE_URLS.put("Gujarat", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/GUJARAT");
        AGRICULTURE_URLS.put("Haryana", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/HARYANA");
        AGRICULTURE_URLS.put("Himachal Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/HIMACHAL%20PRADESH");
        AGRICULTURE_URLS.put("Jharkhand", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/JHARKHAND");
        AGRICULTURE_URLS.put("Karnataka", "https://raitamitra.karnataka.gov.in/49/policy/en");
        AGRICULTURE_URLS.put("Kerala", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/KERALA");
        AGRICULTURE_URLS.put("Madhya Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MADHYA%20PRADESH");
        AGRICULTURE_URLS.put("Maharashtra", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MAHARASHTRA");
        AGRICULTURE_URLS.put("Manipur", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MANIPUR");
        AGRICULTURE_URLS.put("Meghalaya", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MEGHALAYA");
        AGRICULTURE_URLS.put("Mizoram", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/MIZORAM");
        AGRICULTURE_URLS.put("Nagaland", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/NAGALAND");
        AGRICULTURE_URLS.put("Odisha", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/ODISHA");
        AGRICULTURE_URLS.put("Punjab", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/PUNJAB");
        AGRICULTURE_URLS.put("Rajasthan", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/RAJASTHAN");
        AGRICULTURE_URLS.put("Sikkim", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/SIKKIM");
        AGRICULTURE_URLS.put("Tamil Nadu", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/TAMIL%20NADU");
        AGRICULTURE_URLS.put("Telangana", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/TELANGANA");
        AGRICULTURE_URLS.put("Tripura", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/TRIPURA");
        AGRICULTURE_URLS.put("Uttar Pradesh", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/UTTAR%20PRADESH");
        AGRICULTURE_URLS.put("Uttarakhand", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/UTTARAKHAND");
        AGRICULTURE_URLS.put("West Bengal", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/WEST%20BENGAL");
        AGRICULTURE_URLS.put("Andaman and Nicobar Islands", "https://ddd.gov.in/agriculture-department/");
        AGRICULTURE_URLS.put("Chandigarh", "https://chandigarhdistrict.nic.in/agriculture/");
        AGRICULTURE_URLS.put("Dadra and Nagar Haveli and Daman and Diu", "https://ddd.gov.in/agriculture-department/");
        AGRICULTURE_URLS.put("Delhi", "https://development.delhi.gov.in/development/agriculture-unit");
        AGRICULTURE_URLS.put("Jammu and Kashmir", "https://agriwelfare.gov.in/en/AgricultureContigencyPlan/JAMMU%20AND%20KASHMIR");
        AGRICULTURE_URLS.put("Ladakh", "https://ladakh.gov.in/agriculture/");
        AGRICULTURE_URLS.put("Lakshadweep", "https://lakshadweep.gov.in/departments/agriculture/");
        AGRICULTURE_URLS.put("Puducherry", "https://pdmc.da.gov.in/files/annual-action-plan/AAP202324/Puducherry%20Revised%20AAP%202023-24.pdf");
        
        // Banking URLs - placeholder URLs for demonstration
        BANKING_URLS.put("Andhra Pradesh", "https://www.india.gov.in/state-government-portal");
        BANKING_URLS.put("Karnataka", "https://www.india.gov.in/state-government-portal");
        BANKING_URLS.put("Maharashtra", "https://www.india.gov.in/state-government-portal");
        BANKING_URLS.put("Tamil Nadu", "https://www.india.gov.in/state-government-portal");
        BANKING_URLS.put("Uttar Pradesh", "https://www.india.gov.in/state-government-portal");
        
        // Business URLs - placeholder URLs for demonstration
        BUSINESS_URLS.put("Andhra Pradesh", "https://www.india.gov.in/state-government-portal");
        BUSINESS_URLS.put("Karnataka", "https://www.india.gov.in/state-government-portal");
        BUSINESS_URLS.put("Maharashtra", "https://www.india.gov.in/state-government-portal");
        BUSINESS_URLS.put("Tamil Nadu", "https://www.india.gov.in/state-government-portal");
        BUSINESS_URLS.put("Uttar Pradesh", "https://www.india.gov.in/state-government-portal");
        
        // Education URLs - placeholder URLs for demonstration
        EDUCATION_URLS.put("Andhra Pradesh", "https://www.india.gov.in/state-government-portal");
        EDUCATION_URLS.put("Karnataka", "https://www.india.gov.in/state-government-portal");
        EDUCATION_URLS.put("Maharashtra", "https://www.india.gov.in/state-government-portal");
        EDUCATION_URLS.put("Tamil Nadu", "https://www.india.gov.in/state-government-portal");
        EDUCATION_URLS.put("Uttar Pradesh", "https://www.india.gov.in/state-government-portal");
        
        // Health URLs - placeholder URLs for demonstration
        HEALTH_URLS.put("Andhra Pradesh", "https://www.india.gov.in/state-government-portal");
        HEALTH_URLS.put("Karnataka", "https://www.india.gov.in/state-government-portal");
        HEALTH_URLS.put("Maharashtra", "https://www.india.gov.in/state-government-portal");
        HEALTH_URLS.put("Tamil Nadu", "https://www.india.gov.in/state-government-portal");
        HEALTH_URLS.put("Uttar Pradesh", "https://www.india.gov.in/state-government-portal");
        
        // Housing URLs - placeholder URLs for demonstration
        HOUSING_URLS.put("Andhra Pradesh", "https://www.india.gov.in/state-government-portal");
        HOUSING_URLS.put("Karnataka", "https://www.india.gov.in/state-government-portal");
        HOUSING_URLS.put("Maharashtra", "https://www.india.gov.in/state-government-portal");
        HOUSING_URLS.put("Tamil Nadu", "https://www.india.gov.in/state-government-portal");
        HOUSING_URLS.put("Uttar Pradesh", "https://www.india.gov.in/state-government-portal");
    }
    
    @Nullable
    public static String getStateUrl(SchemeSector sector, String stateName) {
        Map<String, String> urls = getUrlMapForSector(sector);
        return urls.get(stateName);
    }
    
    private static Map<String, String> getUrlMapForSector(SchemeSector sector) {
        switch (sector) {
            case AGRICULTURE:
                return AGRICULTURE_URLS;
            case BANKING:
                return BANKING_URLS;
            case BUSINESS:
                return BUSINESS_URLS;
            case EDUCATION:
                return EDUCATION_URLS;
            case HEALTH:
                return HEALTH_URLS;
            case HOUSING:
                return HOUSING_URLS;
            default:
                return new HashMap<>();
        }
    }
}
