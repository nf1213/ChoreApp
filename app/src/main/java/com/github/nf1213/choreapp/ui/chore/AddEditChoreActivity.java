package com.github.nf1213.choreapp.ui.chore;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.nf1213.choreapp.ChoreApplication;
import com.github.nf1213.choreapp.ChoreFactory;
import com.github.nf1213.choreapp.R;
import com.github.nf1213.choreapp.ReminderTask;
import com.github.nf1213.choreapp.datastorage.Chore;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
            viewModel.addChore(mChore).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            ReminderTask.schedule(AddEditChoreActivity.this, mChore);
                        }

                        @Override
                        public void onComplete() {
                            Log.v("TAG", "onComplete - successfully added event");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.v("TAG", "onError - add:", e);
                        }
                    });
            finish();
        });
    }

    private void enableUi(boolean b) {
        mEditText.setVisibility(b ? View.VISIBLE : View.GONE);
        mSubmit.setVisibility(b ? View.VISIBLE : View.GONE);
        mProgress.setVisibility(!b ? View.VISIBLE : View.GONE);
    }
}
