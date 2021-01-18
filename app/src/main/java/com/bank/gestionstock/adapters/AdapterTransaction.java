package com.bank.gestionstock.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bank.gestionstock.R;
import com.bank.gestionstock.models.Operation;
import java.util.ArrayList;

public class AdapterTransaction extends BaseAdapter {
    ArrayList<Operation> transactions;

    public AdapterTransaction(ArrayList<Operation> transactions ){
        this.transactions = transactions;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Operation getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_list_operation,parent,false);
        }
        TextView articleName,num,date,quantity;
        num = convertView.findViewById(R.id.numOfTrans);
        articleName = convertView.findViewById(R.id.article);
        date = convertView.findViewById(R.id.dateOfTrans);
        quantity = convertView.findViewById(R.id.qte);

        num.setText(String.valueOf(getItem(position).getNum()));
        articleName.setText(getItem(position).getArticleName());
        quantity.setText(String.valueOf(getItem(position).getQuantite()));
        date.setText(getItem(position).getDate());
        return convertView;
    }
}
