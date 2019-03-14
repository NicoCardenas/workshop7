package edu.eci.arsw.cinema.filter.impl;

import edu.eci.arsw.cinema.filter.Filter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class FilteredByGender implements Filter{

    @Override
    public List<Movie> filterBy(Cinema cinema, String date, String filter) {
        List<Movie> res = new ArrayList<>();
        for (CinemaFunction cinemaFunction : cinema.getFunctions()) {
            if (cinemaFunction.getDate().equals(date))
                res.add(cinemaFunction.getMovie());
        }
        
        return res;
    }

}
