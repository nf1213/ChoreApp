package com.github.nf1213.choreapp.ui.chore;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
    private Chore chore;
    private TextInputLayout choreNameView;
    private Button submitButton;
    private View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);
        submitButton = (Button) findViewById(R.id.add_chore_submit);
        choreNameView = (TextInputLayout) findViewById(R.id.add_chore_name);
        progressView = findViewById(R.id.add_chore_loading);

        ChoreApplication application = (ChoreApplication) getApplication();
        viewModel = ViewModelProviders.of(this, new ChoreFactory(application)).get(AddChoreViewModel.class);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(KEY_CHORE_ID)) {
            int id = getIntent().getExtras().getInt(KEY_CHORE_ID);
            enableUi(false);
            viewModel.getChore(id).observe(this, chore -> {
                enableUi(true);
                this.chore = chore;
                choreNameView.getEditText().setText(chore.name);
            });
        } else {
            chore = new Chore();
        }

        submitButton.setOnClickListener(v -> {
            chore.name = choreNameView.getEditText().getText().toString();
            viewModel.addChore(chore).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            ReminderTask.schedule(AddEditChoreActivity.this, chore);
                        }

                        @Override
                        public void onComplete() { /* empty */ }

                        @Override
                        public void onError(Throwable e) { /* empty */ }
                    });
            finish();
        });
    }

    private void enableUi(boolean b) {
        choreNameView.setVisibility(b ? View.VISIBLE : View.GONE);
        submitButton.setVisibility(b ? View.VISIBLE : View.GONE);
        progressView.setVisibility(!b ? View.VISIBLE : View.GONE);
    }
}
