package sumago.androidipt.b1expensemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import sumago.androidipt.b1expensemanager.models.Category;
import sumago.androidipt.b1expensemanager.models.Expense;
import sumago.androidipt.b1expensemanager.models.ExpenseReport;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "expenseManager";
    // Table Names
    private static final String TABLE_EXPENSE = "expense";
    private static final String TABLE_CATEGORY = "category";
    // Common Column
    private static final String COLUMN_IS_DELETED = "is_deleted";
    // Category Table Columns
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "categoryName";
    // Expense Table Columns
    private static final String COLUMN_EXPENSE_ID = "id";
    private static final String COLUMN_EXPENSE_NAME = "name";
    private static final String COLUMN_EXPENSE_NOTE = "note";
    private static final String COLUMN_EXPENSE_DATE = "date";
    private static final String COLUMN_EXPENSE_AMOUNT = "amount";
    private static final String COLUMN_EXPENSE_CATEGORY_ID = "categoryId";
    private static final String COLUMN_EXPENSE_CATEGORY_NAME = "categoryName";

    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " +
            TABLE_CATEGORY + "("
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORY_NAME + " TEXT NOT NULL, "
            + COLUMN_IS_DELETED + " INTEGER DEFAULT 0);";
    // Create Expense Table
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " +
            TABLE_EXPENSE + "("
            + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EXPENSE_NAME + " TEXT NOT NULL, "
            + COLUMN_EXPENSE_DATE + " TEXT NOT NULL, "
            + COLUMN_EXPENSE_NOTE + " TEXT NOT NULL, "
            + COLUMN_EXPENSE_AMOUNT + " REAL NOT NULL, "
            + COLUMN_EXPENSE_CATEGORY_ID + " INTEGER, "
            + COLUMN_EXPENSE_CATEGORY_NAME + " TEXT NOT NULL, "
            + COLUMN_IS_DELETED + " INTEGER DEFAULT 0"
            + ");";

    String grandQuery="SELECT" +
            " categoryName," +
            " TOTAL(amount) as totalAmount," +
            " MAX(amount) as maxAmount," +
            " MIN(amount) as minAmount," +
            " AVG(amount) as averageAmount" +
            " FROM" +
            " expense" +
            " GROUP BY" +
            " categoryId";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE_CATEGORY);
            db.execSQL(CREATE_TABLE_EXPENSE);
            db.execSQL("INSERT INTO category (categoryName) VALUES ('General')");
        } catch (SQLException e) {
            Log.d("mytag",e.getMessage(),e);
            e.printStackTrace();
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public long insertExpense(Expense expense)
    {
        SQLiteDatabase database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_EXPENSE_NAME,expense.getName());
        values.put(COLUMN_EXPENSE_AMOUNT,expense.getAmount());
        values.put(COLUMN_EXPENSE_DATE,expense.getDate());
        values.put(COLUMN_EXPENSE_NOTE,expense.getNote());
        values.put(COLUMN_EXPENSE_CATEGORY_ID,expense.getCategoryId());
        values.put(COLUMN_EXPENSE_CATEGORY_NAME,expense.getCategoryName());
        long id=database.insert(TABLE_EXPENSE,null,values);
        return id;
    }

    public ArrayList<Expense> getAllExpenses(){

        ArrayList<Expense> list=new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT * FROM "+TABLE_EXPENSE+" ORDER BY "+COLUMN_EXPENSE_ID+" DESC",null);
        if(cursor.moveToFirst())
        {
            do{
                Expense expense=new Expense();
                expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_NAME)));
                expense.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_AMOUNT)));
                expense.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_DATE)));
                expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_NOTE)));
                expense.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_CATEGORY_ID)));
                expense.setCategoryName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_CATEGORY_NAME)));
                expense.setId(cursor.getInt(0));
                list.add(expense);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public double getSum()
    {
        double sum=0d;
        SQLiteDatabase database=getReadableDatabase();

        Cursor cursor=database.rawQuery("SELECT SUM(amount) FROM expense",null);
        if(cursor.moveToFirst())
        {
            sum=cursor.getDouble(0);
        }

        return sum;
    }

    public int delete(int id)
    {
        SQLiteDatabase database=getWritableDatabase();
        int count=database.delete(TABLE_EXPENSE,"id=?",new String[]{String.valueOf(id)});
        return count;
    }

    public int updateExpense(Expense expense){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_EXPENSE_NAME,expense.getName());
        values.put(COLUMN_EXPENSE_AMOUNT,expense.getAmount());
        values.put(COLUMN_EXPENSE_DATE,expense.getDate());
        values.put(COLUMN_EXPENSE_NOTE,expense.getNote());
        values.put(COLUMN_EXPENSE_CATEGORY_ID,expense.getCategoryId());
        values.put(COLUMN_EXPENSE_CATEGORY_NAME,expense.getCategoryName());
        int id= 0;
        try {
            id = database.update(TABLE_EXPENSE,values,"id=?",new String[]{String.valueOf(expense.getId())});
        } catch (Exception e) {
        }
        return id;
    }
    public void clearAll()
    {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL("DELETE FROM "+TABLE_EXPENSE);
        database.execSQL("DELETE FROM " + TABLE_CATEGORY + " WHERE id > 1");
    }


    public long insertCategory(String category){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_CATEGORY_NAME,category);
        long id=database.insert(TABLE_CATEGORY,null,values);
        return id;
    }

    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> list=new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT * FROM "+TABLE_CATEGORY,null);
        if(cursor.moveToFirst())
        {
            do {

                Category  category=new Category();
                category.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME)));
                category.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
                list.add(category);

            }while (cursor.moveToNext());
        }


        return list;
    }

    public int deleteCategoryById(int id)
    {


        return 0;
    }

    public ArrayList<ExpenseReport> getExpenseReport(){
        ArrayList<ExpenseReport> list=new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.rawQuery(grandQuery,null);
        if(cursor.moveToFirst())
        {
            do{
                ExpenseReport expense=new ExpenseReport();
                expense.setAverageAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("averageAmount")));
                expense.setMinAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("minAmount")));
                expense.setMaxAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("maxAmount")));
                expense.setTotalAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("totalAmount")));
                expense.setCategoryName(cursor.getString(cursor.getColumnIndexOrThrow("categoryName")));
                list.add(expense);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
