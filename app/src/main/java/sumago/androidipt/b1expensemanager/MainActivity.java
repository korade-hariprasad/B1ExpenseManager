package sumago.androidipt.b1expensemanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import sumago.androidipt.b1expensemanager.database.DbHelper;
import sumago.androidipt.b1expensemanager.models.Expense;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    DbHelper dbHelper;

    FloatingActionButton fabAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        loadFragment(new HomeFragment());
        dbHelper=new DbHelper(this);
        fabAdd=findViewById(R.id.fabAdd);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.action_home)
                {
                    loadFragment(new HomeFragment());
                }
                if(item.getItemId()==R.id.action_analytics)
                {
                    loadFragment(new AnalyticsFragment());

                }
                if(item.getItemId()==R.id.action_more)
                {
                    loadFragment(new MoreFragment());
                }
                if(item.getItemId()==R.id.action_options)
                {
                    loadFragment(new OptionsFragment());
                }
                return true;
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });
    }
    public void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }
}