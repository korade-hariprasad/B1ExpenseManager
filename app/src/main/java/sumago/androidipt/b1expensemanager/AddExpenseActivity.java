package sumago.androidipt.b1expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import sumago.androidipt.b1expensemanager.database.DbHelper;
import sumago.androidipt.b1expensemanager.models.Category;
import sumago.androidipt.b1expensemanager.models.Expense;

public class AddExpenseActivity extends AppCompatActivity {

    ChipGroup chipGroup;

    TextInputLayout txLayoutExpenseName;
    TextInputLayout txLayoutAmount;
    TextInputEditText etName;
    TextInputEditText etAmount;
    TextInputEditText etDate;
    TextInputEditText etNotes;
    Button btnAdd;

    DbHelper dbHelper;
    ArrayList<Category> categoryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etAmount = findViewById(R.id.etAmount);
        etNotes = findViewById(R.id.etNote);
        btnAdd = findViewById(R.id.btnAdd);
        txLayoutExpenseName = findViewById(R.id.txExpenseNameLayout);
        txLayoutAmount = findViewById(R.id.txAmountLayout);
        chipGroup = findViewById(R.id.chipGroup);
        dbHelper = new DbHelper(this);
        categoryArrayList = new ArrayList<>();
        categoryArrayList = dbHelper.getAllCategories();
        chipGroup.setSingleSelection(true);
        Chip firstChip = null;
        for (int i = 0; i < categoryArrayList.size(); i++) {
            Chip chip = new Chip(chipGroup.getContext());
            chip.setText(categoryArrayList.get(i).getName());
            chip.setId(ViewCompat.generateViewId());
            chip.setTag(categoryArrayList.get(i));
            chip.setCheckable(true);
            chipGroup.addView(chip);
            if (i == 0) {
                firstChip = chip;
            }
        }
        firstChip.setChecked(true);
        chipGroup.setSingleSelection(true);
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {


            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateFields()) {
                    Chip selectedChip=findViewById(chipGroup.getCheckedChipId());
                    Category category= (Category) selectedChip.getTag();
                    String name = etName.getText().toString();
                    double amount = Double.parseDouble(etAmount.getText().toString());
                    String note = etNotes.getText().toString();
                    String date = etDate.getText().toString();
                    String catName = category.getName();
                    int catId = category.getId();
                    long id = dbHelper.insertExpense(new Expense(name, catName, date, amount, catId, note));
                    if (id > 0) {
                        Intent intent=new Intent(AddExpenseActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    Log.d("mytag", "" + id);
                }
            }
        });

    }

    private boolean validateFields() {
        ArrayList<Boolean> errors = new ArrayList<>();

        if (!etName.getText().toString().isEmpty() && etName.getText().toString().length() > 1) {
            errors.add(true);
            txLayoutExpenseName.setError(null);
        } else {
            txLayoutExpenseName.setError("Please enter a valid name");
            errors.add(false);
        }
        if (!etAmount.getText().toString().isEmpty() && etAmount.getText().toString().length() > 0) {
            errors.add(true);
            txLayoutAmount.setError(null);
        } else {
            txLayoutAmount.setError("Please enter a valid amount");
            errors.add(false);
        }
        return !errors.contains(false);
    }
}