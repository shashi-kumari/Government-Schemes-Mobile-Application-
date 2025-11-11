package com.app.GovernmentSchemes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Agriculture_activity extends BaseActivity {
    TextView lblXmlData;
    TextView header_title;
    android.widget.Button govtSchemesButton;
    int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agriculture);
        
        // Setup common navigation elements
        setupCommonViews();
        
        header_title = findViewById(R.id.header_title);
        header_title.setText("Agriculture Sector");
        lblXmlData = findViewById(R.id.lbl_xml_data);
        govtSchemesButton = findViewById(R.id.govt_schemes_button);
        govtSchemesButton.setOnClickListener(this);
        mode = getIntent().getIntExtra("mode", 0);
        parseXmlDocument();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        
        if (view.equals(govtSchemesButton)) {
            Intent govtSchemes = new Intent(Agriculture_activity.this, GovernmentSchemesActivity.class);
            govtSchemes.putExtra(GovernmentSchemesActivity.EXTRA_SECTOR, SchemeSector.AGRICULTURE.name());
            startActivity(govtSchemes);
        }
    }

    public String parseXmlDocument() {
        try {
            InputStream is = getAssets().open("agridata.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("ROW");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    // Retrieve the values of the "SCHEME" and "DESC" fields
                    String scheme = getValue("SCHEME", element2);
                    String desc = getValue("DESC", element2);

                    // Format the scheme string to appear bold
                    String formattedScheme = "<b>" + scheme + "</b>";
                    String schemeHead = "<b><u>" + "SCHEME :" + "</b><u>";
                    String descHead = "<b><u>" + "DESCRIPTION :" + "</b><u>";
                    // Print the values with formatting
                    lblXmlData.append(Html.fromHtml(schemeHead + formattedScheme + "<br>"));
                    lblXmlData.append(Html.fromHtml(descHead + ""));
                    lblXmlData.append(desc+"\n\n");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList =
                element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node =
                nodeList.item(0);
        return node.getNodeValue();
    }
}