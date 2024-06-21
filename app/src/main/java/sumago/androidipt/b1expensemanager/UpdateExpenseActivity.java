package sumago.androidipt.b1expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import sumago.androidipt.b1expensemanager.database.DbHelper;
import sumago.androidipt.b1expensemanager.models.Expense;

public class UpdateExpenseActivity extends AppCompatActivity {
    TextInputLayout txLayoutExpenseName;
    TextInputLayout txLayoutAmount;
    TextInputEditText etName;
    TextInputEditText etAmount;
    TextInputEditText etDate;
    TextInputEditText etNotes;
    Button btnUpdate;
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
        setContentView(R.layout.activity_update_expense);
        etName=findViewById(R.id.etName);
        etDate=findViewById(R.id.etDate);
        etAmount=findViewById(R.id.etAmount);
        etNotes=findViewById(R.id.etNote);
        btnUpdate=findViewById(R.id.btnUpdate);
        txLayoutExpenseName=findViewById(R.id.txExpenseNameLayout);
        txLayoutAmount=findViewById(R.id.txAmountLayout);
        dbHelper=new DbHelper(this);
        expenseId=getIntent().getIntExtra("id",0);
        Log.d("mytag","main===>"+expenseId);
        categoryId=getIntent().getIntExtra("categoryId",0);
        name=getIntent().getStringExtra("name");
        note=getIntent().getStringExtra("note");
        category=getIntent().getStringExtra("category");
        date=getIntent().getStringExtra("date");
        amount=getIntent().getDoubleExtra("amount",0);

        etName.setText(name);
        etAmount.setText(""+amount);
        etNotes.setText(note);
        etDate.setText(date);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields())
                {
                    String name=etName.getText().toString();
                    double amount=Double.parseDouble(etAmount.getText().toString());
                    String note=etNotes.getText().toString();
                    String date=etDate.getText().toString();
                    String catName="Default";
                    int count=dbHelper.updateExpense(new Expense(expenseId,name,catName,date,amount,categoryId,note));
                    Log.d("mytag",""+count);
                    Log.d("mytag",""+expenseId);
                    if(count>0)
                    {
                        Intent intent=new Intent(UpdateExpenseActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }else{
                    Log.d("mytag","Validation Failed");
                }
            }
        });
    }



    private boolean validateFields() {
        ArrayList<Boolean> errors=new ArrayList<>();

        if(!etName.getText().toString().isEmpty() && etName.getText().toString().length()>1)
        {
            errors.add(true);
            etName.setError(null);
        }else
        {
            etName.setError("Please enter a valid name");
            errors.add(false);
        }
        if(!etAmount.getText().toString().isEmpty() && etAmount.getText().toString().length()>0)
        {
            errors.add(true);
            etAmount.setError(null);
        }else{
            etAmount.setError("Please enter a valid amount");
            errors.add(false);
        }
        return !errors.contains(false);
    }
}