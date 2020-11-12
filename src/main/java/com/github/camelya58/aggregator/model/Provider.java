package com.github.camelya58.aggregator.model;


import com.github.camelya58.aggregator.vo.Vacancy;

import java.util.List;

/**
 * Class Provider
 *
 * @author Kamila Meshcheryakova
 * created by 10.11.2020
 */
public class Provider {

  private Strategy strategy;

  public Provider(Strategy strategy) {
    this.strategy = strategy;
  }

  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }

  public List<Vacancy> getJavaVacancies(String searchString) {
    return this.strategy.getVacancies(searchString);
  }
}
