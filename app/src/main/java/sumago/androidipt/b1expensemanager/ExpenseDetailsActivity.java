package sumago.androidipt.b1expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sumago.androidipt.b1expensemanager.database.DbHelper;
import sumago.androidipt.b1expensemanager.models.Expense;

public class ExpenseDetailsActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvNote;
    TextView tvDate;
    TextView tvCategory;
    TextView tvAmount;
    CardView cardDelete;
    CardView cardEdit;
    DbHelper dbHelper;
    int expenseId;
    int categoryId;
    String name;
    String note;
    String category;
    String date;
    double amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);
        tvAmount=findViewById(R.id.tvAmount);
        tvName=findViewById(R.id.tvName);
        tvNote=findViewById(R.id.tvNote);
        tvCategory=findViewById(R.id.tvCategory);
        tvDate=findViewById(R.id.tvDate);
        cardDelete=findViewById(R.id.cardDelete);
        cardEdit=findViewById(R.id.cardEdit);
        dbHelper=new DbHelper(this);
        expenseId=getIntent().getIntExtra("id",0);
        Log.d("mytag","aaaaaa =>"+expenseId);
        categoryId=getIntent().getIntExtra("categoryId",0);
         name=getIntent().getStringExtra("name");
         note=getIntent().getStringExtra("note");
         category=getIntent().getStringExtra("category");
         date=getIntent().getStringExtra("date");
         amount=getIntent().getDoubleExtra("amount",0);

        tvName.setText(name);
        tvCategory.setText(category);
        tvAmount.setText(""+amount);
        tvNote.setText(note);
        tvDate.setText(date);

        cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count=dbHelper.delete(expenseId);
                if(count>0)
                {
                    finish();
                }
            }
        });
        cardEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ExpenseDetailsActivity.this, UpdateExpenseActivity.class);
                Log.d("mytag","================>"+expenseId);
                intent.putExtra("id",expenseId);
                intent.putExtra("name",name);
                intent.putExtra("amount",amount);
                intent.putExtra("category",category);
                intent.putExtra("categoryId",categoryId);
                intent.putExtra("note",note);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
    }
}