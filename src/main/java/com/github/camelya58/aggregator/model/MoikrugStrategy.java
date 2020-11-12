package com.github.camelya58.aggregator.model;

import com.github.camelya58.aggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class MoikrugStrategy
 *
 * @author Kamila Meshcheryakova
 * created by 12.11.2020
 */
public class MoikrugStrategy implements Strategy {

    private static final String CACHE = "http://javarush.ru/testdata/big28data2.html";
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";
    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        int page = 0;
        while (true) {
            try {
                Document document = getDocument(searchString, page);
                Elements elements = document.select("div[class=vacancy-card__info]");
                if (elements.size() == 0) {
                    break;
                }
                elements.forEach(element -> {
                    Vacancy vacancy = new Vacancy();
                    Elements title = element.getElementsByAttributeValue("class", "vacancy-card__title");
                    vacancy.setTitle(title.text().trim());
                    Elements info = element.getElementsByAttributeValue("class", "vacancy-card__meta");
                    vacancy.setCompanyName(info.first().getElementsByTag("a").first().text().trim());
                    String city = info.first().getElementsByTag("a").last().text();
                    vacancy.setCity(city == null ? "" : city);

                    Elements salary = element.getElementsByAttributeValue("class", "vacancy-card__salary");
                    vacancy.setSalary(salary == null ? "" : salary.text().trim());
                    String siteName = URL_FORMAT.substring(0, URL_FORMAT.lastIndexOf("/"));
                    vacancy.setSiteName(siteName);
                    vacancy.setUrl(siteName + title.select("a").first().attr("href").trim());
                    vacancies.add(vacancy);
                });
                page++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        Document document = Jsoup.connect(CACHE)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/84.0.4147.89 Safari/537.36")
                .referrer("no-referrer-when-downgrade").get();
        document.html();
        return document;
    }
}
