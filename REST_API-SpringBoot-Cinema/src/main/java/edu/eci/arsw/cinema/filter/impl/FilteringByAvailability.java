package edu.eci.arsw.cinema.filter.impl;

import edu.eci.arsw.cinema.filter.Filter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FilteringByAvailability implements Filter{

    @Override
    public List<Movie> filterBy(Cinema cinema, String date, String filter) {
        List<Movie> res = new ArrayList<>();        
        for (CinemaFunction cinemaFunction : cinema.getFunctions()) {
            int count = 0;
            for (List<Boolean> seat : cinemaFunction.getSeats()) {
                for (Boolean boolean1 : seat) {
                    if (boolean1)
                        count++;
                }
            }
            if (count <= Integer.parseInt(filter))
                res.add(cinemaFunction.getMovie());
        }
        
        return res;
    }

}
