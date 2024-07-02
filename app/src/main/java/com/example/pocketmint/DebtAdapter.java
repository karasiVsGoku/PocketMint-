package com.example.pocketmint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.ViewHolder> {

    Context context;
    List<Debt> debts;

    public DebtAdapter(Context context, List<Debt> debts) {
        this.context = context;
        this.debts = debts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.debt, parent, false);
        return new DebtAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Debt debt = debts.get(position);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(debt.getGiver()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User giver = snapshot.getValue(User.class);
                holder.giver.setText("Giver : " + giver.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(debt.getReceiver()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User receiver = snapshot.getValue(User.class);
                holder.receiver.setText("Receiver : " + receiver.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.details.setText("Due to : " + debt.getDate());
        holder.amount.setText("Amount : " + String.valueOf(debt.getAmount()));
        holder.reason.setText("Reason : " + debt.getReason());
        holder.information_layout.setVisibility(View.GONE);
        holder.itemView.setTag("unselected");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.itemView.getTag().equals("unselected")) {
                    holder.itemView.setTag("selected");
                    holder.information_layout.setVisibility(View.VISIBLE);
                }
                else {
                    holder.itemView.setTag("unselected");
                    holder.information_layout.setVisibility(View.GONE);
                }
            }
        });



        
    }

    @Override
    public int getItemCount() {
        return debts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView receiver;
        public TextView giver;
        public TextView details;
        public TextView amount;
        public TextView reason;
        LinearLayout information_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            receiver = itemView.findViewById(R.id.receiver);
            giver = itemView.findViewById(R.id.giver);
            details = itemView.findViewById(R.id.details);
            amount = itemView.findViewById(R.id.amount_of_money);
            information_layout = itemView.findViewById(R.id.information_layout);
            reason = itemView.findViewById(R.id.reason);
        }
    }
}
