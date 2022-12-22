package com.example.databaseseventeen;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.example.databaseseventeen.Fragments.ListFragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class DataBase {
    private final String host = "8.210.33.51";
    private final String database = "tichonDatabase";
    private final int port = 5432;
    private final String user = "root";
    private final String pass = "password";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private Connection connection;
    private boolean status;
    public DataBase(Context context)
    {
        this.url = String.format(this.url, this.host, this.port, this.database);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);
        connect();
        //this.disconnect();
        Toast.makeText(context,String.valueOf(status),Toast.LENGTH_LONG).show();
    }
    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                } catch (Exception e) {
                    status = false;
                    Log.d("ExceptionLog","run:" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }
    public ArrayList<Person> getAll(){
        ArrayList<Person> persons=new ArrayList<>();
        try {
            ResultSet set = connection.createStatement().executeQuery("select * from Persons inner join ContactData on persons.ID=ContactData.PersonID");
            while(set.next()){
                persons.add(new Person(set.getInt("id"),set.getString("firstname"),set.getString("secondname"),
                        set.getInt("age"),new PersonData(set.getInt("personid"),set.getString("phone"),set.getString("email"))));
            }
            return persons;
        }
        catch(Exception e){
            Log.d("ExceptionLog", "getAll: " + e.getMessage());
        }
        return persons;
    }
    public ArrayList<Person> getAllPreparedStatement(int minAge){
        ArrayList<Person> persons=new ArrayList<>();
        String sql = "SELECT * FROM Persons where age > ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, minAge);
            ResultSet set=preparedStatement.executeQuery();
            while(set.next()){
                persons.add(new Person(set.getInt("id"),set.getString("firstname"),set.getString("secondname"),
                        set.getInt("age"),new PersonData(set.getInt("personid"),set.getString("phone"),set.getString("email"))));
            }
            return persons;
        }
        catch(Exception e){
            Log.d("ExceptionLog", "getAll: " + e.getMessage());
        }
        return persons;
    }
    public void BatchInsert(List<Person> persons){
        String personinsert="INSERT INTO persons(firstname,secondname,age) values(?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(personinsert);
            connection.setAutoCommit(false);
            for(Person person:persons) {
                statement.setString(1, person.FirstName);
                statement.setString(2, person.SecondName);
                statement.setInt(3, person.age);
                statement.addBatch();
            }
            Log.d(TAG, "BatchInsert: " + statement);
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch(Exception e){
            Log.d(TAG, "BatchInsert: " + e.getMessage());
        }
//        String personDatainsert="INSERT INTO ContactData(PersonID,Phone,email) values(" + 10 + "," +
//                person.personData.Phone + "," + person.personData.Email + ")";
//        try {
//            Statement statement = connection.createStatement();
//            statement.addBatch(personinsert);
////            statement.addBatch(personDatainsert);
//            statement.executeBatch();
//            connection.commit();
//        }
//        catch(Exception e){
//            Log.d(TAG, "BatchInsert: " + e.getMessage());
//        }
    }
    public void Delete(Person person){
        String personinsert="DELETE FROM ContactData WHERE PersonID = ?";
        String persondel="DELETE FROM persons WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(personinsert);
            statement.setInt(1,person.ID);
            statement.execute();
            statement = connection.prepareStatement(persondel);
            statement.setInt(1,person.ID);
            statement.execute();
        }
        catch(Exception e){
            Log.d(TAG, "BatchInsert: " + e.getMessage());
        }
    }
    public void Update(Person person) {
        String personinsert="UPDATE ContactData SET Phone = ?,Email = ? where PersonID=?";
        String persondel="UPDATE persons SET firstname = ?,secondname = ?,age=? where ID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(personinsert);
            statement.setString(1,person.personData.Phone);
            statement.setString(2,person.personData.Email);
            statement.setInt(3,person.ID);
            statement.execute();
            statement = connection.prepareStatement(persondel);
            statement.setString(1,person.FirstName);
            statement.setString(2,person.SecondName);
            statement.setInt(3,person.age);
            statement.setInt(4,person.ID);
            statement.execute();
        }
        catch(Exception e){
            Log.d(TAG, "BatchInsert: " + e.getMessage());
        }
    }

    public void UpdateProcedure(Person person){
        String personinsert="CALL updateProcedure(?,?,?,?,?,?)";
        Log.d(TAG, "UpdateProcedure: " + person.FirstName);
        Log.d(TAG, "UpdateProcedure: " + person.SecondName);
        Log.d(TAG, "UpdateProcedure: " + person.age);
        try {
            PreparedStatement statement = connection.prepareStatement(personinsert);
            statement.setInt(1,person.ID);
            statement.setString(2,person.FirstName);
            statement.setString(3,person.SecondName);
            statement.setInt(4,person.age);
            statement.setString(5,person.personData.Phone);
            statement.setString(6,person.personData.Email);
            statement.execute();
        }
        catch(Exception e){
            Log.d(TAG, "BatchInsert: " + e.getMessage());
        }
    }
    public int callFunction(){
        try {
            String functionCall = "select getCount()";
            PreparedStatement statement = connection.prepareStatement(functionCall);
            ResultSet set=statement.executeQuery();
            set.next();
            return set.getInt(1);
        }
        catch(Exception e){
            Log.d(TAG, "callFunction: " + e.getMessage());
        }
        return 0;
    }
}
