package ru.nordpage.onetraksteps;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.nordpage.onetraksteps.adapters.StepAdapter;
import ru.nordpage.onetraksteps.models.StepData;
import ru.nordpage.onetraksteps.tools.App;
import ru.nordpage.onetraksteps.tools.StepsApi;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list) RecyclerView mStepList;

    StepAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        StepsApi api = App.getApi();
        api.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::OnSuccess,this::OnError,this::OnComplete);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mStepList.setLayoutManager(llm);



    }

    private void OnComplete() {

    }

    private void OnError(Throwable throwable) {
        Snackbar.make(mStepList, "Load Data Error "+throwable.getMessage(), Snackbar.LENGTH_LONG)
                .show();
    }

    private void OnSuccess(List<StepData> stepData) {
        adapter = new StepAdapter(this,stepData);
        mStepList.setAdapter(adapter);
    }
}
