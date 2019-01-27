package ru.nordpage.onetraksteps.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nordpage.onetraksteps.R;
import ru.nordpage.onetraksteps.models.StepData;
import ru.nordpage.onetraksteps.tools.SegmentedBar;
import ru.nordpage.onetraksteps.tools.Utils;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    Context ctx;
    List<StepData> stepDataList;

    ObjectAnimator colorAnimator;
    ArgbEvaluator colorEvaluator;

    public StepAdapter(Context ctx, List<StepData> stepDataList) {
        this.ctx = ctx;
        this.stepDataList = stepDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(mView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        StepData item = stepDataList.get(i);
        viewHolder.init(item);
    }

    @Override
    public int getItemCount() {
        return stepDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.segmentedBar) SegmentedBar mBar;
        @BindView(R.id.goal) ConstraintLayout mGoalLay;
        @BindView(R.id.star) ImageView mStar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void init(StepData item){

            mBar.setDate(item.getDate());
            mBar.setMaxValue(4000);
            mBar.setWalk(item.getWalk());
            mBar.setAerobic(item.getAerobic());
            mBar.setRun(item.getRun());

            if (mBar.getSumm() > mBar.getMaxValue()){
                mGoalLay.setVisibility(View.VISIBLE);

                Utils.animateImageView(ctx,mStar);
            }



        }
    }

}
