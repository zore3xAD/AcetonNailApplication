package com.android.zore3x.acetonnailapplication.masters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zore3x.acetonnailapplication.R;
import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.procedure.Procedure;
import com.android.zore3x.acetonnailapplication.procedure.ProcedureDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class EditMasterInformationFragment extends Fragment {

    public static final String TAG = "EditMasterFragment";
    private static final String ARG_MASTER_ID = "master_id";
    private static final int REQUEST_PROCEDURE = 1;

    private EditText mEditTextMasterName;
    private EditText mEditTextMasterSurname;
    private EditText mEditTextMasterPhone;

    private TextView mTextViewMasterType;

    private Button mButtonConfirm;
    private Button mButtonMasterProcedure;

    private Master mMaster;
    private List<Procedure> mMasterTypeList;
    private boolean isEditable = false;

    public static EditMasterInformationFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_MASTER_ID, id);

        EditMasterInformationFragment fragment = new EditMasterInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // достааем из аргументов фрагмента ИД мастера
        UUID masterId = (UUID)getArguments().getSerializable(ARG_MASTER_ID);
        // если данные присутствуют то выбераем необходимую запись из базы данных и устанавливаем флаг редактирование
        // иначе создаем нового мастера
        if(masterId != null) {
            mMaster = MasterLab.get(getActivity()).getItem(masterId);
            isEditable = true;
        } else {
            mMaster = new Master();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_master_information, container, false);

        mEditTextMasterName = (EditText)v.findViewById(R.id.editText_editable_master_name);
        mEditTextMasterSurname = (EditText)v.findViewById(R.id.editText_editable_master_surname);
        mEditTextMasterPhone = (EditText)v.findViewById(R.id.editText_editable_master_phone);

        mTextViewMasterType = (TextView)v.findViewById(R.id.textView_editable_master_type);

        mButtonConfirm = (Button)v.findViewById(R.id.button_editable_master_confirm);
        // обработка нажатия на кнопку подтверждения
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextMasterName.getText().toString();
                String surname = mEditTextMasterSurname.getText().toString();
                String phone = mEditTextMasterPhone.getText().toString();

                // если Редактирование то обновляем запись в базе данных
                // иначе добавляем новую запись
                if(isEditable) {
                    mMaster.setName(name);
                    mMaster.setSurname(surname);
                    mMaster.setPhone(phone);
                    mMaster.setMasterType(mMasterTypeList);

                    MasterLab.get(getActivity()).update(mMaster);
                    Toast.makeText(getActivity(), "Master edited", Toast.LENGTH_SHORT).show();

                    getActivity().finish();
                } else {
                    mMaster.setName(name);
                    mMaster.setSurname(surname);
                    mMaster.setPhone(phone);
                    mMaster.setMasterType(mMasterTypeList);

                    MasterLab.get(getActivity()).add(mMaster);
                    Toast.makeText(getActivity(), "Master " + mMaster.getPersonal() + " was added", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }
        });
        // обработка нажатия на кнопку выбора типа мастера
        mButtonMasterProcedure = (Button)v.findViewById(R.id.button_editable_master_procedure);
        mButtonMasterProcedure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // открытие диалогового окна с выбором типа мастера
                openProcedureFragment();
            }
        });

        // заполнени информацией о выбранном мастере полей ввода
        if (isEditable) {
            mEditTextMasterName.setText(mMaster.getName());
            mEditTextMasterSurname.setText(mMaster.getSurname());
            mEditTextMasterPhone.setText(mMaster.getPhone());
            mTextViewMasterType.setText(mMaster.getStringMasterType());
        }

        return v;
    }

    // функция открытия диалогового окна
    private void openProcedureFragment() {
        DialogFragment procedureFragment = new ProcedureDialog();
        procedureFragment.setTargetFragment(this, REQUEST_PROCEDURE);
        procedureFragment.show(getFragmentManager(), procedureFragment.getClass().getName());
    }

    // возвращение результата выбора типа мастера
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PROCEDURE:
                    mMasterTypeList = (ArrayList<Procedure>)data.getSerializableExtra(ProcedureDialog.TAG_PROCEDURE_SELECTED);
                    mMaster.setMasterType(mMasterTypeList);
                    mTextViewMasterType.setText(mMaster.getStringMasterType());
                    break;
            }
        }
    }
}
