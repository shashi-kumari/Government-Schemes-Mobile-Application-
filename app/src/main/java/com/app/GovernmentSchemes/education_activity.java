package com.app.GovernmentSchemes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class education_activity extends BaseActivity {
    TextView lblXmlData;
    TextView header_title;
    android.widget.Button govtSchemesButton;
    int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        
        // Setup common navigation elements
        setupCommonViews();
        
        header_title = findViewById(R.id.header_title);
        header_title.setText("Education Sector");
        lblXmlData = findViewById(R.id.lbl_xml_data);
        lblXmlData.setMovementMethod(LinkMovementMethod.getInstance());
        govtSchemesButton = findViewById(R.id.govt_schemes_button);
        govtSchemesButton.setOnClickListener(this);
        mode = getIntent().getIntExtra("mode", 0);
        loadSchemesFromDatabase();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        
        if (view.equals(govtSchemesButton)) {
            Intent govtSchemes = new Intent(education_activity.this, GovernmentSchemesActivity.class);
            govtSchemes.putExtra(GovernmentSchemesActivity.EXTRA_SECTOR, SchemeSector.EDUCATION.name());
            startActivity(govtSchemes);
        }
    }

    private void loadSchemesFromDatabase() {
        lblXmlData.setText("Loading schemes...");
        
        SchemeDataProvider.getSchemesForSector(SchemeSector.EDUCATION, new SchemeDataProvider.SchemeListCallback() {
            @Override
            public void onSchemesLoaded(List<SchemeData> schemes) {
                displaySchemes(schemes);
            }

            @Override
            public void onError(String errorMessage) {
                lblXmlData.setText("Error loading schemes: " + errorMessage);
            }
        });
    }

    private void displaySchemes(List<SchemeData> schemes) {
        if (schemes.isEmpty()) {
            lblXmlData.setText("No schemes available.");
            return;
        }

        lblXmlData.setText("");
        
        for (SchemeData schemeData : schemes) {
            String schemeName = schemeData.getScheme() != null ? schemeData.getScheme() : "";
            String description = schemeData.getDescription() != null ? schemeData.getDescription() : "";
            String notificationDate = schemeData.getNotificationDate() != null ? schemeData.getNotificationDate() : "";
            
            // Format the scheme display
            String schemeHead = "<b><u>SCHEME :</u></b>";
            String formattedScheme = "<b>" + schemeName + "</b>";
            String descHead = "<b><u>DESCRIPTION :</u></b>";
            String dateHead = "<b><u>NOTIFICATION DATE :</u></b>";
            
            lblXmlData.append(Html.fromHtml(schemeHead + formattedScheme + "<br>"));
            lblXmlData.append(Html.fromHtml(descHead + " "));
            lblXmlData.append(description + "\n");
            
            if (!notificationDate.isEmpty()) {
                lblXmlData.append(Html.fromHtml(dateHead + " "));
                lblXmlData.append(notificationDate + "\n");
            }
            
            // Add clickable link if URL is valid
            if (schemeData.hasValidUrl()) {
                SpannableString linkText = new SpannableString("View Details");
                linkText.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        openUrlInBrowser(schemeData.getUrl());
                    }
                }, 0, linkText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                lblXmlData.append(linkText);
            }
            
            lblXmlData.append("\n\n");
        }
    }

    private void openUrlInBrowser(String url) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open URL", Toast.LENGTH_SHORT).show();
        }
    }
}