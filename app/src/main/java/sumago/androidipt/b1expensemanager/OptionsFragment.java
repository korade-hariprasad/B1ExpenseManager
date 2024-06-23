package sumago.androidipt.b1expensemanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import sumago.androidipt.b1expensemanager.database.DbHelper;
import sumago.androidipt.b1expensemanager.models.Category;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OptionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputLayout texLayoutExpenseName;
    TextInputEditText etName;
    MaterialButton btnAdd;
    ChipGroup chipGroup;
    DbHelper dbHelper;
    public OptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OptionsFragment newInstance(String param1, String param2) {
        OptionsFragment fragment = new OptionsFragment();
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
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd = view.findViewById(R.id.btnAdd);
        chipGroup=view.findViewById(R.id.chipGroup);
        dbHelper=new DbHelper(getActivity());
        texLayoutExpenseName = view.findViewById(R.id.texLayoutExpenseName);
        etName=view.findViewById(R.id.etCategoryName);
        ArrayList<Category> categoryArrayList = dbHelper.getAllCategories();
        for (int i = 0; i < categoryArrayList.size(); i++) {
            Chip chip = new Chip(chipGroup.getContext());
            chip.setText(categoryArrayList.get(i).getName());
            chip.setTag(categoryArrayList.get(i));
            chip.setCheckable(false);
            chip.setId(ViewCompat.generateViewId());
            if(i==0){

            }else{
                chip.setCloseIconVisible(true); // Enable close icon
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Category category= (Category) chip.getTag();
                        int count=dbHelper.deleteCategory(category.getId());
                        if(count>0)
                        {
                            chipGroup.removeView(chip);
                        }

                    }
                });
            }

            chipGroup.addView(chip);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields())
                {
                    long count=dbHelper.insertCategory(etName.getText().toString().trim());
                    if(count>0)
                    {
                        Chip chip = new Chip(chipGroup.getContext());
                        chip.setText(etName.getText().toString().trim());
                        chip.setTag(new Category((int) count,etName.getText().toString().trim()));
                        chip.setCheckable(true);
                        chip.setCloseIconVisible(true);
                        chip.setId(ViewCompat.generateViewId());
                        chip.setCloseIconVisible(true); // Enable close icon
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Category category= (Category) chip.getTag();
                                int count=dbHelper.deleteCategory(category.getId());
                                if(count>0)
                                {
                                    chipGroup.removeView(chip);
                                    chipGroup.invalidate();
                                }

                            }
                        });
                        chipGroup.addView(chip);
                    }
                }
                }

        });
    }

    private boolean validateFields() {
        ArrayList<Boolean> errors = new ArrayList<>();

        if (!etName.getText().toString().isEmpty() && etName.getText().toString().length() > 1) {
            errors.add(true);
            texLayoutExpenseName.setError(null);
        } else {
            texLayoutExpenseName.setError("Please enter a valid category name");
            errors.add(false);
        }
        return !errors.contains(false);
    }
}