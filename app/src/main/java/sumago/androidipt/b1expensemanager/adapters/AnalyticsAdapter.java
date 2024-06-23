package sumago.androidipt.b1expensemanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sumago.androidipt.b1expensemanager.R;
import sumago.androidipt.b1expensemanager.models.ExpenseReport;

public class AnalyticsAdapter extends RecyclerView.Adapter<AnalyticsAdapter.ViewHoler> {
    ArrayList<ExpenseReport> list;
    public AnalyticsAdapter(ArrayList<ExpenseReport> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AnalyticsAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_analytics,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnalyticsAdapter.ViewHoler holder, int position) {

        holder.tvTotal.setText("₹ "+list.get(position).getTotalAmount());
        holder.tvMax.setText("₹ "+list.get(position).getMaxAmount());
        holder.tvMin.setText("₹ "+list.get(position).getMinAmount());
        holder.tvAvg.setText("₹ "+list.get(position).getAverageAmount());
        holder.tvCategoryName.setText(list.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        TextView tvMax;
        TextView tvMin;
        TextView tvAvg;
        TextView tvTotal;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            tvCategoryName=itemView.findViewById(R.id.tvCategoryName);
            tvAvg=itemView.findViewById(R.id.tvAvg);
            tvTotal=itemView.findViewById(R.id.tvTotal);
            tvMax=itemView.findViewById(R.id.tvMax);
            tvMin=itemView.findViewById(R.id.tvMin);
        }
    }
}
