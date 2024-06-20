package sumago.androidipt.b1expensemanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sumago.androidipt.b1expensemanager.adapters.ExpenseListAdapter;
import sumago.androidipt.b1expensemanager.database.DbHelper;
import sumago.androidipt.b1expensemanager.interfaces.OnDeleteListener;
import sumago.androidipt.b1expensemanager.models.Expense;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnDeleteListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button button;
    RecyclerView recyclerViewExpenseList;
    ExpenseListAdapter expenseListAdapter;
    DbHelper dbHelper;
    TextView tvTotal;

    public HomeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewExpenseList=view.findViewById(R.id.recyclerViewExpenseList);
        tvTotal=view.findViewById(R.id.tvTotal);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerViewExpenseList.setLayoutManager(layoutManager);
        dbHelper=new DbHelper(getActivity());
        ArrayList<Expense> list=dbHelper.getAllExpenses();
        double sum= dbHelper.getSum();
        tvTotal.setText("₹ "+sum);
        expenseListAdapter=new ExpenseListAdapter(list,HomeFragment.this);
        recyclerViewExpenseList.setAdapter(expenseListAdapter);
        recyclerViewExpenseList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onDelete(Expense expense) {
        dbHelper.delete(expense.getId());
        ArrayList<Expense> list=dbHelper.getAllExpenses();
        double sum= dbHelper.getSum();
        tvTotal.setText("₹ "+sum);
        expenseListAdapter=new ExpenseListAdapter(list,HomeFragment.this);
        recyclerViewExpenseList.setAdapter(expenseListAdapter);
        recyclerViewExpenseList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
    }
}