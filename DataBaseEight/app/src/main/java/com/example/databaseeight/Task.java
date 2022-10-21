package com.example.databaseeight;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class Task {
    String NoteText;
    String Category;
    String NoteDate;
    public static Task taskBuffer;
    public static View rootView;
    public static Date calendarDate;
    public static CategoryClass categoryBuffer;
    public static ArrayList<Task> tasks=new ArrayList<Task>();
    public static ArrayList<CategoryClass> categorys=new ArrayList<CategoryClass>();
    public Task(String noteText,String _Category,Date noteDate){
        NoteText=noteText;
        Category=_Category;
        NoteDate=noteDate.toString();
    }
    public Task(){};

    @Override
    public String toString(){
        return NoteDate + "\n" + NoteText;
    }

    public static void Serialize(){
        FileOutputStream fos;
        File f=new File(rootView.getContext().getFilesDir(),"Tasks.xml");
        f.delete();
        try {
            f.createNewFile();
        }
        catch (Exception e){
            Log.d("ExceptionLog", "Serialize: " + e.getMessage());
        }
        try{
            fos=rootView.getContext().openFileOutput("aaa.xml",Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer= Xml.newSerializer();
            xmlSerializer.setOutput(fos, "UTF-8");
            xmlSerializer.startDocument(null, Boolean.valueOf(true));
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            xmlSerializer.startTag(null, "Tasks");
            for (int j = 0; j < tasks.size(); j++) {
                xmlSerializer.startTag(null, "Task");

                xmlSerializer.startTag(null, "NoteText");
                xmlSerializer.text(tasks.get(j).NoteText);
                xmlSerializer.endTag(null, "NoteText");

                xmlSerializer.startTag(null, "Category");
                xmlSerializer.text(tasks.get(j).Category);
                xmlSerializer.endTag(null, "Category");

                xmlSerializer.startTag(null, "NoteDate");
                xmlSerializer.text(tasks.get(j).NoteDate);
                xmlSerializer.endTag(null, "NoteDate");

                xmlSerializer.endTag(null, "Task");
            }
            xmlSerializer.endTag(null, "Tasks");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            fos.close();
        }
        catch(Exception e){
            Log.d("ExceptionLog", "Serialize: " + e.getMessage());
        }
    }
    public static void Deserialize(){
        FileInputStream fis = null;
        InputStreamReader isr = null;
        try {
            tasks=new ArrayList<>();
            fis = rootView.getContext().openFileInput("aaa.xml");
            isr = new InputStreamReader(fis);
            char[] inputBuffer = new char[fis.available()];
            isr.read(inputBuffer);

            String data;
            data = new String(inputBuffer);
            isr.close();
            fis.close();

            /*
             * Converting the String data to XML format so
             * that the DOM parser understands it as an XML input.
             */
            InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));

            DocumentBuilderFactory dbf;
            DocumentBuilder db;
            NodeList items = null;
            Document dom;

            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            dom = db.parse(is);

            // Normalize the document
            dom.getDocumentElement().normalize();
            items = dom.getElementsByTagName("Task");
            for (int i = 0; i < items.getLength(); i++) {
                Log.d("ExceptionLog", "Deserialize: " + String.valueOf(i));
                Task taska = new Task();
                Node item = items.item(i);
                NodeList parametres = item.getChildNodes();
                for (int j = 0; j < parametres.getLength(); j++) {
                    Node parametr = parametres.item(j);
                    if (parametr.getNodeName().equals("NoteText"))
                        taska.NoteText = parametr.getFirstChild().getNodeValue();
                    if (parametr.getNodeName().equals("Category"))
                        taska.Category = parametr.getFirstChild().getNodeValue();
                    if (parametr.getNodeName().equals("NoteDate"))
                        taska.NoteDate = parametr.getFirstChild().getNodeValue();
                }
                tasks.add(taska);
            }
        }
        catch (Exception e) {
            Log.d("ExceptionLog", "Deserialize: " + e.getMessage());
        }
    }
    public static void JsonSerialzie(){
        Gson gson=new GsonBuilder().create();
        File f=new File(rootView.getContext().getFilesDir(),"category.json");
        try{
            BufferedWriter bw=new BufferedWriter(new FileWriter(f,false));
            bw.write(gson.toJson(categorys));
            bw.close();
        }
        catch(Exception e){
            Log.d("ExceptionLog", "JsonSerialzie: " + e.getMessage());
        }
    }
    public static void JsonDeserialize(){
        File f=new File(rootView.getContext().getFilesDir(),"category.json");
        if(!f.exists()){
            try {
                f.createNewFile();
            }
            catch(Exception e){
                Log.d("ExceptionLog", "JsonDeserialize: " + e.getMessage());
            }
        }
        if(f.length()!=0){
            try {
                BufferedReader br=new BufferedReader(new FileReader(f));
                Gson gson = new GsonBuilder().create();
                categorys = gson.fromJson(br.readLine(),new TypeToken<ArrayList<CategoryClass>>(){}.getType());
            }
            catch(Exception e){
                Log.d("ExceptionLog", "JsonDeserialize: " + e.getMessage());
            }
        }
        
    }
}
