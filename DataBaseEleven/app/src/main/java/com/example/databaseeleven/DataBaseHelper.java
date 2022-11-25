package com.example.databaseeleven;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    static long BeginDate;
    static long EndDate;
    static Context context;
    String TAG="ExceptionLog";
    public DataBaseHelper(Context context1){
        super(context1,"STUDENTSDB.db",null,1);
        context=context1;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA automatic_index=true");
        sqLiteDatabase.execSQL("CREATE TABLE FACULTY(IDFACULTY INTEGER PRIMARY KEY AUTOINCREMENT,FACULTY TEXT," +
                "DEAN TEXT,OFFICETIMETABLE TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE GROUPS(IDGROUP INTEGER PRIMARY KEY AUTOINCREMENT," +
                " FACULTY TEXT, COURSE INTEGER,NAME TEXT, HEAD INTEGER) ");
//                "FOREIGN KEY(FACULTY) REFERENCES FACULTY(FACULTY) ON DELETE CASCADE ON UPDATE CASCADE)");
        sqLiteDatabase.execSQL("CREATE TABLE STUDENT(IDSTUDENT INTEGER PRIMARY KEY AUTOINCREMENT,IDGROUP INTEGER," +
                "NAME TEXT, BIRTHDATE TEXT, ADDRESS TEXT,FOREIGN KEY(IDGROUP) REFERENCES GROUPS(IDGROUP) ON UPDATE CASCADE ON DELETE CASCADE)");
        sqLiteDatabase.execSQL("CREATE TABLE SUBJECT(IDSUBJECT INTEGER PRIMARY KEY AUTOINCREMENT,SUBJECT TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE PROGRESS(IDSTUDENT INTEGER ,IDSUBJECT INTEGER," +
                "EXAMDATE TEXT,MARK INTEGER,TEACHER TEXT,FOREIGN KEY (IDSUBJECT) REFERENCES SUBJECT(IDSUBJECT) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (IDSTUDENT) REFERENCES STUDENT(IDSTUDENT) ON DELETE CASCADE ON UPDATE CASCADE)");
        try {
            sqLiteDatabase.execSQL("INSERT INTO FACULTY(FACULTY,DEAN,OFFICETIMETABLE)" +
                    "VALUES('ФИТ','Шиман',46),('ИЭФ','Ольферович',46),('ХТиТ','Климош',46),('ЛХФ','Ярмолович',46)," +
                    "('ТОВ','Радченко',46),('ПИМ','Долгова',46)");
            sqLiteDatabase.execSQL("INSERT INTO GROUPS(FACULTY,COURSE,NAME,HEAD)" +
                    "VALUES(1,2,'ИСИТ','СтаростаИСИТ'),(1,3,'ПОИТ','СтаростаПОИТ')," +
                    "(2,2,'ГруппаИЭФ','СтаростаИЭФ'),(3,3,'ГруппаХТиТ','СтаростаХТиТ'),(4,1,'ГруппаЛХФ','СтаростаЛХФ')," +
                    "(5,3,'ГруппаТОВ','СтаростаТОВ'),(6,1,'ГруппаПИМ','СтаростаПИМ')," +
                    "(3,7,'ГруппаХТиТ2','СтаростаХТиТ2'),(4,5,'ГруппаЛХФ2','СтаростаЛХФ2')," +
                    "(5,3,'ГруппаТОВ2','СтаростаТОВ2'),(6,2,'ГруппаПИМ2','СтаростаПИМ2'),(1,4,'ПОИБМС','СтаростаПОИБМС')");
            sqLiteDatabase.execSQL("insert into STUDENT (IDGROUP,NAME, BIRTHDATE,ADDRESS) values (1, 'Силюк Валерия','2002-06-01','Минск')," +
                    "(1, 'Сергель Виолетта Николаевна','2002-02-01','Могилев')," +
                    "(1, 'Добродей Ольга Анатольевна','2003-05-05','Гомель')," +
                    "(1, 'Подоляк Мария Сергеевна',        '2003-07-05','Минск')," +
                    "(1, 'Никитенко Екатерина Дмитриевна', '2003-02-05','Могилев')," +
                    "(2, 'Яцкевич Галина Иосифовна',       '2003-01-06','Брест')," +
                    "(2, 'Осадчая Эла Васильевна',         '2003-04-10','Гомель')," +
                    "(2, 'Акулова Елена Геннадьевна',      '2003-08-09','Брест')," +
                    "(5, 'Плешкун Милана Анатольевна',     '2003-10-08','Минск')," +
                    "(5, 'Буянова Мария Александровна',    '2003-02-01','Минск')," +
                    "(5, 'Харченко Елена Геннадьевна',     '2003-04-04','Могилев')," +
                    "(6, 'Крученок Евгений Александрович', '2003-11-03','Минск')," +
                    "(6, 'Бороховский Виталий Петрович',  '2003-12-12','Гомель')," +
                    "(6, 'Мацкевич Надежда Валерьевна',    '2003-06-11','Минск')," +
                    "(7, 'Логинова Мария Вячеславовна',    '2003-05-04','Могилев')");
            sqLiteDatabase.execSQL("INSERT INTO SUBJECT(SUBJECT)" +
                    "VALUES('Системы управления базами данных')," +
                    "('Базы данных'),('Информационные технологии'),('Основы алгоритмизации и программирования')," +
                    "('Программирование сетевых приложений'),('Проектирование информационных систем')," +
                    "('Компьютерная геометрия'),('Дискретная математика'),('Математическое программирование')," +
                    "('Объектно-ориентированное программирование')");
            sqLiteDatabase.execSQL("INSERT INTO PROGRESS(IDSTUDENT,IDSUBJECT,EXAMDATE,MARK,TEACHER)" +
                    "VALUES(1,2,'2022-06-05',2,'Блинова Е.А.'),(2,2,'2022-06-05',8,'Блинова Е.А.'),(3,2,'2022-06-05',7,'Блинова Е.А.')," +
                    "(4,2,'2022-06-05',2,'Блинова Е.А.'),(5,2,'2022-06-05',5,'Блинова Е.А.'),(6,1,'2022-06-10',6,'Пустовалова Н.Н.'),(7,1,'2022-06-10',4,'Пустовалова Н.Н.')," +
                    "(8,1,'2022-06-10',2,'Пустовалова Н.Н.'),(9,4,'2022-06-07',1,'Жиляк'),(10,4,'2022-06-07',8,'Жиляк'),(11,4,'2022-06-07',8,'Жиляк')," +
                    "(12,4,'2022-06-20',8,'Белодед Н.И.'),(13,4,'2022-06-20',8,'Белодед Н.И.'),(14,4,'2022-06-20',8,'Белодед Н.И.'),(15,8,'2022-06-15',8,'Буснюк Н.Н.')," +
                    "(1,5,'2022-06-11',7,'Шиман Д.В.'),(2,5,'2022-06-11',3,'Шиман Д.В.'),(3,5,'2022-06-11',7,'Шиман Д.В.')," +
                    "(4,5,'2022-06-11',5,'Шиман Д.В.'),(5,5,'2022-06-11',6,'Шиман Д.В.')," +
                    "(6,5,'2022-06-11',4,'Шиман Д.В.'),(7,5,'2022-06-11',9,'Шиман Д.В.'),(8,5,'2022-06-11',7,'Шиман Д.В.')");
            sqLiteDatabase.execSQL("CREATE VIEW LabView AS\n" +
                    "SELECT STUDENT.NAME[STUDENTNAME],GROUPS.NAME[GROUPNAME],GROUPS.IDGROUP,FACULTY.FACULTY,PROGRESS.MARK,PROGRESS.EXAMDATE,SUBJECT.SUBJECT\n" +
                    "FROM FACULTY INNER JOIN GROUPS ON GROUPS.FACULTY=FACULTY.IDFACULTY\n" +
                    "INNER JOIN STUDENT ON STUDENT.IDGROUP=GROUPS.IDGROUP\n" +
                    "INNER JOIN PROGRESS ON PROGRESS.IDSTUDENT=STUDENT.IDSTUDENT\n" +
                    "INNER JOIN SUBJECT ON PROGRESS.IDSUBJECT=SUBJECT.IDSUBJECT");
            sqLiteDatabase.execSQL("CREATE VIEW SUBJECTVIEW AS " +
                    "SELECT * FROM SUBJECT");
            sqLiteDatabase.execSQL("CREATE INDEX ProgressIndex on PROGRESS(IDSTUDENT)");
            sqLiteDatabase.execSQL("CREATE INDEX StudentIndex on STUDENT(NAME)");
            sqLiteDatabase.execSQL("CREATE INDEX FacultyIndex on FACULTY(FACULTY)");
//            sqLiteDatabase.execSQL("CREATE TRIGGER FirstTrigger BEFORE INSERT ON STUDENT\n" +
//                    "BEGIN\n" +
//                    "SELECT CASE\n" +
//                    "WHEN((SELECT COUNT(*) FROM STUDENT WHERE STUDENT.IDGROUP=NEW.IDGROUP)>6)\n" +
//                    "THEN RAISE(ABORT,\"В группе больше 6 студентов\")\n" +
//                    "END;\n" +
//                    "END;");
//            sqLiteDatabase.execSQL("CREATE TRIGGER SecondTrigger BEFORE DELETE ON STUDENT\n" +
//                    "BEGIN\n" +
//                    "SELECT CASE\n" +
//                    "WHEN((SELECT COUNT(*) FROM STUDENT WHERE STUDENT.IDGROUP=OLD.IDGROUP)<3)\n" +
//                    "THEN RAISE(ABORT,\"В группе менее 3 студентов\")\n" +
//                    "END;\n" +
//                    "END;");
            sqLiteDatabase.execSQL("CREATE TRIGGER ThirdTrigger INSTEAD OF UPDATE ON SUBJECTVIEW\n" +
                    "BEGIN\n" +
                    "\tUPDATE SUBJECT SET SUBJECT=NEW.SUBJECT WHERE SUBJECT.IDSUBJECT=NEW.IDSUBJECT;\n" +
                    "END;\n");
        }
        catch(Exception e){
            Log.d(TAG, "instance initializer: " + e.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
//    Список группы со средней оценкой для каждого студента по предмету,
//    для студента в целом, для группы в целом с выбором периода,
//    в который эта выборка производится.
    public Cursor firstQuery(SQLiteDatabase db,String subject,String begin,String end,String groupid){
        Cursor cursor=db.rawQuery("SELECT S.STUDENTNAME,AVG(S.MARK),(SELECT AVG(ST.MARK)\n" +
                "FROM LabView ST WHERE ST.STUDENTNAME = S.STUDENTNAME AND ST.SUBJECT=?\n" +
                "AND ST.EXAMDATE BETWEEN ? AND ?),(SELECT AVG(MARK) FROM LabView " +
                "GROUP BY LabView.IDGROUP HAVING LabView.IDGROUP=S.IDGROUP)\n" +
                "FROM LabView S WHERE S.IDGROUP=? AND S.EXAMDATE BETWEEN ? AND ?\n" +
                "GROUP BY S.STUDENTNAME", new String[]{subject,begin,end,groupid,begin,end});
        return cursor;
    }
    //Список наилучших студентов для всех групп факультета за период
    public Cursor secondQuery(SQLiteDatabase db,String fac,String begin,String end){
        Cursor cursor=db.rawQuery("SELECT S.STUDENTNAME,AVG(S.MARK) " +
                "FROM LabView S WHERE S.FACULTY=? " +
                "AND S.EXAMDATE BETWEEN ? AND ? " +
                "GROUP BY S.STUDENTNAME HAVING AVG(S.MARK)>=7", new String[]{fac,begin,end});
        return cursor;
    }
    //Список студентов, получивших оценки ниже 4, для всех групп факультета за период.
    public Cursor thirdQuery(SQLiteDatabase db,String fac,String begin,String end){
        Log.d(TAG, "thirdQuery: " + fac);
        Cursor cursor=db.rawQuery("SELECT S.STUDENTNAME FROM LabView S " +
                "WHERE S.FACULTY=? " +
                "AND S.MARK <4 AND S.EXAMDATE BETWEEN ? " +
                "AND ?",new String[]{fac,begin,end});
        return cursor;
    }
    //Сравнительный анализ по группам за период: по предмету и в целом.
    public Cursor fourthQuery(SQLiteDatabase db,String sub,String begin,String end){
        Cursor cursor=db.rawQuery("SELECT STF.GROUPNAME,AVG(MARK)," +
                "(SELECT AVG(MARK) FROM LabView S " +
                "WHERE S.SUBJECT=? AND S.GROUPNAME=STF.GROUPNAME) " +
                "FROM LabView STF WHERE STF.EXAMDATE BETWEEN ? AND ? " +
                "GROUP BY STF.GROUPNAME",new String[]{sub,begin,end});
        return cursor;
    }
    //Сравнение по факультетам: отсортированный список факультетов
    // за определенный период от минимальной средней оценки к максимальной.
    public Cursor fifthQuery(SQLiteDatabase db,String begin,String end){
        Cursor cursor=db.rawQuery("SELECT S.FACULTY,AVG(MARK) AS AMARK\n" +
                "FROM LabView S WHERE S.EXAMDATE BETWEEN ? AND ?\n" +
                "GROUP BY S.FACULTY\n" +
                "ORDER BY AMARK ASC", new String[]{begin,end});
        return cursor;
    }
}
