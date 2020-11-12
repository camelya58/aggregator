package com.github.camelya58.aggregator.view;

import com.github.camelya58.aggregator.Controller;
import com.github.camelya58.aggregator.vo.Vacancy;

import java.util.List;

/**
 * Class View
 *
 * @author Kamila Meshcheryakova
 * created by 10.11.2020
 */
public interface View {

    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
