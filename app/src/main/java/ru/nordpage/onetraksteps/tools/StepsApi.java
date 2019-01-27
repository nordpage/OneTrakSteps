package ru.nordpage.onetraksteps.tools;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.nordpage.onetraksteps.models.StepData;

public interface StepsApi {

    @GET("metric.json")
    Observable<List<StepData>> getData();

}
