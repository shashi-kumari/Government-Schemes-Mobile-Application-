package com.app.GovernmentSchemes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
public class business_activity extends AppCompatActivity {
    TextView lblXmlData, lblJsonData;
    TextView header_title;
    int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        header_title = findViewById(R.id.header_title);

        header_title.setText("Business Sector");
        lblXmlData = (TextView) findViewById(R.id.lbl_xml_data);
        mode = getIntent().getIntExtra("mode", 0);
        parseXmlDocument();
    }

    public String parseXmlDocument() {
        try {
            InputStream is = getAssets().open("businessdata.xml");
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

                    // Print the values
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