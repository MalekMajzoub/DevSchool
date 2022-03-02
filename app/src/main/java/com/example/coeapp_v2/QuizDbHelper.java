package com.example.coeapp_v2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coeapp_v2.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyQuizzes.db";
    private static final int DATABASE_VERSION = 2;
    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_Create_Question_Table = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_Create_Question_Table);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    private void fillQuestionsTable() {
        Question q1 = new Question("What is a correct syntax to output \"Hello World\" in Java?", "Console.WriteLine(\"Hello World\")", "System.out.println(\"Hello World\")", "print(\"Hello World\")", 2);
        addQuestion(q1);
        Question q2 = new Question("Comments in Java are preceded by?", "// ...", "#", "/*", 1);
        addQuestion(q2);
        Question q3 = new Question("Method for getting length?", "getSize()", "len()", "length()", 3);
        addQuestion(q3);
        Question q4 = new Question("Variable that should store text?", "myString", "String", "Txt", 2);
        addQuestion(q4);
        Question q5 = new Question("Variable with the numeric value 5?", "float x = 5;", "num x = 5;", "int x = 5;", 3);
        addQuestion(q5);
        Question q6 = new Question("Variable with the floating number 2.8?", "float x = 2.8;", "float x =2.8f", "x = 2.8f", 2);
        addQuestion(q6);
        Question q7 = new Question("If A = true and B = false, A && B =?", "false", "true", "neither", 1);
        addQuestion(q7);
        Question q8 = new Question("If A = true and B = false, A || B =?", "false", "true", "both", 2);
        addQuestion(q8);
        Question q9 = new Question("If A = true and B = false, A && !B =?", "false", "true", "neither", 2);
        addQuestion(q9);
        Question q10 = new Question("int x = 2; if(x > 3){A} else {B}; code will execute", "A", "B", "none", 2);
        addQuestion(q10);
        Question q11 = new Question("boolean x = true; if(x){A: x=false} else {B}; code will execute", "A", "B", "both", 1);
        addQuestion(q11);
        Question q12 = new Question("int x = 0; while(x <= 3); code will execute:", "2 times", "3 times", "4 times", 3);
        addQuestion(q12);
        Question q13 = new Question("Which for loop statement is correct", "for(i > 4; int i = 0; i++)", "for(int i = 0; i > 4 ; i++)", "for(i++ ;int i = 0; i > 4)", 2);
        addQuestion(q13);
        Question q14 = new Question("for(int i = 3; i <= 3; i++); code will execute: ", "1 time", "2 times", "never", 1);
        addQuestion(q14);
        Question q15 = new Question("Array is defined as the following:", "arr = {a1, a2,...}", "arr = (ar1, ar2,...)", "arr = [a1, a2,...]", 3);
        addQuestion(q15);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);

    }

    @SuppressLint("Range")
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);


            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
