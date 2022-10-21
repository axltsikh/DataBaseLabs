package com.example.databaseeight;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class Xpath extends Fragment {

    private static final String TAG = "ExceptionLog";

    public Xpath() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Task.rootView=inflater.inflate(R.layout.fragment_xpath, container, false);
        Filler();
        return Task.rootView;
    }
    public void Filler(){
        ArrayAdapter<CategoryClass> adapter=new ArrayAdapter<>(Task.rootView.getContext(),android.R.layout.simple_spinner_dropdown_item,Task.categorys);
        Spinner spinner=Task.rootView.findViewById(R.id.CategoryXpath);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String a=spinner.getSelectedItem().toString();
                Finder(a);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void Finder(String a){
        try {
            File f = new File(Task.rootView.getContext().getFilesDir(), "aaa.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            XPath x = XPathFactory.newInstance().newXPath();
            String expression = "/root/Task[Category='" +a+ "']";
            NodeList nodeList = (NodeList) x.compile(expression).evaluate(doc, XPathConstants.NODESET);
            ArrayList<Task> buffer=new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Log.d(TAG, "Finder: " + node.getNodeName());
                Element taskBuffer=(Element)node;
                String text= taskBuffer.getElementsByTagName("NoteText").item(0).getTextContent();
                String Date=taskBuffer.getElementsByTagName("NoteDate").item(0).getTextContent();
                String Category=taskBuffer.getElementsByTagName("Category").item(0).getTextContent();
                Task task=new Task();
                task.NoteText=text;
                task.NoteDate=Date;
                task.Category=Category;
                buffer.add(task);
            }
            ListView listView=Task.rootView.findViewById(R.id.XpathListView);
            ArrayAdapter<Task> adapter=new ArrayAdapter<Task>(Task.rootView.getContext(), android.R.layout.simple_expandable_list_item_1,buffer);
            listView.setAdapter(adapter);
        }
        catch(Exception e){
            Log.d(TAG, "Finder: " + e.getMessage());
        }
    }

}