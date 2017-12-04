package com.android.zore3x.acetonnailapplication.procedure;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.android.zore3x.acetonnailapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class ProcedureDialog extends DialogFragment {

    // тэг для передечи результата обратно
    public static final String TAG_PROCEDURE_SELECTED = "procedure";

    private Button mButtonAddProcedure;
    private EditText mEditTextNewProcedureTitle;
    private RecyclerView mRecyclerViewProcedureList;

    private String mSelectedProcedure;
    private List<Procedure> mSelectedProcedureList = new ArrayList<>();

    private ProcedureAdapter mAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_procedure, null);

        mEditTextNewProcedureTitle = (EditText) view.findViewById(R.id.editText_doalog_procedure_add_new_procedure);
        mRecyclerViewProcedureList = (RecyclerView) view.findViewById(R.id.recyclerView_dialog_fragment_procedure);
        mRecyclerViewProcedureList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mButtonAddProcedure = (Button) view.findViewById(R.id.button_dialog_procedure_add);
        mButtonAddProcedure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextNewProcedureTitle.getText().length() != 0) {
                    String procedureTitle = mEditTextNewProcedureTitle.getText().toString();
                    Procedure procedure = new Procedure();
                    procedure.setTitle(procedureTitle);

                    ProcedureLab.get(getActivity()).add(procedure);
                    mEditTextNewProcedureTitle.getText().clear();
                    updateUI();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(TAG_PROCEDURE_SELECTED, (Serializable) mSelectedProcedureList);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        updateUI();

        return builder.create();
    }

    private class ProcedureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CheckedTextView mTextViewProcedureTitle;

        private Procedure mProcedure;

        public ProcedureHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextViewProcedureTitle = (CheckedTextView) itemView;
        }

        private void bindProcedure(Procedure procedure) {
            mProcedure = procedure;
            mTextViewProcedureTitle.setText(mProcedure.getTitle());
        }

        @Override
        public void onClick(View v) {
            //mSelectedProcedure = mTextViewProcedureTitle.getText().toString();
            if(mTextViewProcedureTitle.isChecked()) {
                // если процерура выбрана ее необходима удалить из списка
                mSelectedProcedureList.remove(mProcedure);
                mTextViewProcedureTitle.setChecked(false);
            } else {
                // если процедура не выбрана добавить в список
                mSelectedProcedureList.add(mProcedure);
                mTextViewProcedureTitle.setChecked(true);
            }
        }
    }

    private class ProcedureAdapter extends RecyclerView.Adapter<ProcedureHolder> {

        private List<Procedure> mProcedures;

        public ProcedureAdapter(List<Procedure> procedures) {
            mProcedures = procedures;
        }

        @Override
        public ProcedureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);

            return new ProcedureHolder(view);
        }

        @Override
        public void onBindViewHolder(ProcedureHolder holder, int position) {
            Procedure procedure = mProcedures.get(position);
            holder.bindProcedure(procedure);
        }

        @Override
        public int getItemCount() {
            return mProcedures.size();
        }

        public void setProcedures(List<Procedure> procedures) {
            mProcedures = procedures;
        }
    }

    private void updateUI() {

        ProcedureLab procedureLab = ProcedureLab.get(getActivity());
        List<Procedure> procedures = procedureLab.getAll();
        if (mAdapter == null) {
            mAdapter = new ProcedureAdapter(procedures);
            mRecyclerViewProcedureList.setAdapter(mAdapter);
        } else {
            mAdapter.setProcedures(procedures);
            mAdapter.notifyDataSetChanged();
        }
    }


}
