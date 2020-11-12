package com.github.camelya58.aggregator.model;

import com.github.camelya58.aggregator.vo.Vacancy;

import java.util.List;

/**
 * Class Strategy
 *
 * @author Kamila Meshcheryakova
 * created by 10.11.2020
 */
public interface Strategy {

  List<Vacancy> getVacancies(String searchString);
}
