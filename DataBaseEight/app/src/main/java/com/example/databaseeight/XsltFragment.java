package com.example.databaseeight;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Date;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XsltFragment extends Fragment {

    public XsltFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Task.rootView= inflater.inflate(R.layout.fragment_xslt, container, false);
        Button button=Task.rootView.findViewById(R.id.xsltButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XsltCreator();
            }
        });
        CalendarView calendar=Task.rootView.findViewById(R.id.XsltCalendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Task.calendarDate = new Date(year-1900, month, day);
                Log.d("ExceptionLog", "onSelectedDayChange: " + Task.calendarDate.toString());
            }
        });
        return Task.rootView;
    }
    public void XsltCreator(){
        try{
            String xslt = "<?xml version=\"1.0\"?>\n" +
                    "<xsl:stylesheet version=\"1.0\" exclude-result-prefixes=\"xsl\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                    "\n" +
                    "<xsl:key name=\"group\" match=\"Task\" use=\"Category\"/>\n" +
                    "\n" +
                    "<xsl:template match=\"Tasks\">\n" +
                    "    <Tasks> \n" +
                    "        <xsl:apply-templates select=\"Task[generate-id(.) = generate-id(key('group',Category))]\" />\n" +
                    "    </Tasks>\n" +
                    "</xsl:template>\n" +
                    "\n" +
                    "<xsl:template match=\"Task\">\n" +
                    "    <Category><xsl:value-of select = \"Category\"/>\n" +
                    "        <xsl:for-each select=\"key('group',Category)\">\n" +
                    "            <xsl:if test=\"NoteDate='"+ Task.calendarDate + "'\">\n" +
                    "            <NoteText><xsl:value-of select=\"NoteText\"/></NoteText>\n" +
                    "            <NoteDate><xsl:value-of select=\"NoteDate\"/></NoteDate>\n" +
                    "</xsl:if>" +
                    "        </xsl:for-each>\n" +
                    "    </Category>\n" +
                    "</xsl:template>\n" +
                    "\n" +
                    "</xsl:stylesheet>";
            File f = new File(Task.rootView.getContext().getFilesDir(), "lab.xslt");
            FileWriter fw = new FileWriter(f, false);
            fw.write(xslt);
            fw.close();
            FileInputStream xmlf = Task.rootView.getContext().openFileInput("aaa.xml");
            FileInputStream xslf = Task.rootView.getContext().openFileInput("lab.xslt");
            FileOutputStream txt = Task.rootView.getContext().openFileOutput("res.xml", MODE_PRIVATE);
            TransformerFactory tf = TransformerFactory.newInstance();
            Source xsltsrc = new StreamSource(xslf);
            Source xmlsrc = new StreamSource(xmlf);
            Transformer t = tf.newTransformer(xsltsrc);
            t.transform(xmlsrc, new StreamResult(txt));
            Toast.makeText(Task.rootView.getContext(), "TXT создан!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Log.d("ExceptionLog", ex.getMessage());
        }
    }

}