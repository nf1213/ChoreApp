package com.github.nf1213.choreapp.ui.chore;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.github.nf1213.choreapp.ui.chore.AddEditChoreActivity.KEY_CHORE_ID;

/**
 * Created by Nicole Felch on 7/20/17.
 */

public class AddEditChoreFragment extends LifecycleFragment {

    private AddChoreViewModel viewModel;
    private Chore chore;
    private TextInputLayout choreNameView;
    private Button submitButton;
    private View progressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_chore, container, false);
        submitButton = (Button) rootView.findViewById(R.id.add_chore_submit);
        choreNameView = (TextInputLayout) rootView.findViewById(R.id.add_chore_name);
        progressView = rootView.findViewById(R.id.add_chore_loading);

        ChoreApplication application = (ChoreApplication) getActivity().getApplication();
        viewModel = ViewModelProviders.of(this, new ChoreFactory(application)).get(AddChoreViewModel.class);

        if (getActivity().getIntent().getExtras() != null && getActivity().getIntent().getExtras().containsKey(KEY_CHORE_ID)) {
            getActivity().setTitle(R.string.edit_chore);
            int id = getActivity().getIntent().getExtras().getInt(KEY_CHORE_ID);
            enableUi(false);
            viewModel.getChore(id).observe(this, chore -> {
                enableUi(true);
                this.chore = chore;
                choreNameView.getEditText().setText(chore.name);
            });
        } else {
            getActivity().setTitle(R.string.add_chore);
            chore = new Chore();
        }

         submitButton.setOnClickListener(v -> {
            chore.name = choreNameView.getEditText().getText().toString();
            viewModel.addChore(chore).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            ReminderTask.schedule(getActivity(), chore);
                        }

                        @Override
                        public void onComplete() { /* empty */ }

                        @Override
                        public void onError(Throwable e) { /* empty */ }
                    });
            getActivity().finish();
        });
        return rootView;
    }

    private void enableUi(boolean b) {
        choreNameView.setVisibility(b ? View.VISIBLE : View.GONE);
        submitButton.setVisibility(b ? View.VISIBLE : View.GONE);
        progressView.setVisibility(!b ? View.VISIBLE : View.GONE);
    }
}
