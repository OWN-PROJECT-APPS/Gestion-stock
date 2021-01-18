package com.bank.gestionstock.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bank.gestionstock.R;
import com.bank.gestionstock.adapters.AdapterTransaction;
import com.bank.gestionstock.models.Operation;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class OperationList extends Fragment {

    ArrayList<Operation> transactions;
    ListView transactionsList;
    AdapterTransaction adapterTransaction;
    public OperationList(ArrayList<Operation> transactions) {

        this.transactions = transactions;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.operation_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transactionsList = view.findViewById(R.id.transactionsList);
        adapterTransaction = new AdapterTransaction(transactions);
        transactionsList.setAdapter(adapterTransaction);

    }
    public void addNewTransaction(Operation transaction){
        transactions.add(transaction);
        adapterTransaction.notifyDataSetChanged();
        Snackbar.make(transactionsList, "Operation Successfully", Snackbar.LENGTH_SHORT).show();
    }


}