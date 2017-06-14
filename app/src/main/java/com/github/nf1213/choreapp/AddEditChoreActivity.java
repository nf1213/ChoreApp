package com.github.nf1213.choreapp;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddEditChoreActivity extends LifecycleActivity {

    public static final String KEY_CHORE_ID = "chore_id";

    private AddChoreViewModel viewModel;
    private Chore mChore;
    private TextInputLayout mEditText;
    private Button mSubmit;
    private View mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);
        mSubmit = (Button) findViewById(R.id.submit);
        mEditText = (TextInputLayout) findViewById(R.id.name);
        mProgress = findViewById(R.id.loading);

        ChoreApplication application = (ChoreApplication) getApplication();
        viewModel = ViewModelProviders.of(this, new ChoreFactory(application)).get(AddChoreViewModel.class);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(KEY_CHORE_ID)) {
            int id = getIntent().getExtras().getInt(KEY_CHORE_ID);
            enableUi(false);
            viewModel.getChore(id).observe(this, chore -> {
                enableUi(true);
                mChore = chore;
                mEditText.getEditText().setText(mChore.name);
            });
        } else {
            mChore = new Chore();
        }

        mSubmit.setOnClickListener(v -> {
            mChore.name = mEditText.getEditText().getText().toString();
            viewModel.putChore(mChore);
            finish();
        });
    }

    private void enableUi(boolean b) {
        mEditText.setVisibility(b ? View.VISIBLE : View.GONE);
        mSubmit.setVisibility(b ? View.VISIBLE : View.GONE);
        mProgress.setVisibility(!b ? View.VISIBLE : View.GONE);
    }
}
