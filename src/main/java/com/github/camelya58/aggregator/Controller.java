package com.github.camelya58.aggregator;

import com.github.camelya58.aggregator.model.Model;

/**
 * Class Controller
 *
 * @author Kamila Meshcheryakova
 * created by 10.11.2020
 */
public class Controller {

    Model model;

    public Controller(Model model) {
        if (model == null) {
            throw new  IllegalArgumentException();
        }
        this.model = model;
    }

    public void onCitySelect(String cityName) {
        model.selectCity(cityName);
    }
}
