package com.github.camelya58.aggregator;


import com.github.camelya58.aggregator.model.HHStrategy;
import com.github.camelya58.aggregator.model.Model;
import com.github.camelya58.aggregator.model.MoikrugStrategy;
import com.github.camelya58.aggregator.model.Provider;
import com.github.camelya58.aggregator.view.HtmlView;

/**
 * Class Aggregator
 *
 * @author Kamila Meshcheryakova
 * created by 10.11.2020
 */
public class Aggregator {
    public static void main(String[] args) {
        Provider providerHH = new Provider(new HHStrategy());
        Provider providerMoikrug = new Provider(new MoikrugStrategy());
        HtmlView view = new HtmlView();
        Model model = new Model(view, providerHH, providerMoikrug);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
