package com.github.camelya58.aggregator.model;


import com.github.camelya58.aggregator.view.View;
import com.github.camelya58.aggregator.vo.Vacancy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Model {

    View view;
    Provider[] providers;

    public Model(View view, Provider... providers) {
        if (view == null || providers == null || providers.length == 0) {
            throw new IllegalArgumentException();
        }
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city) {
        List<Vacancy> vacancies = Arrays.stream(providers)
                .flatMap(provider -> provider.getJavaVacancies(city).stream())
                .collect(Collectors.toList());
        view.update(vacancies);
    }
}
