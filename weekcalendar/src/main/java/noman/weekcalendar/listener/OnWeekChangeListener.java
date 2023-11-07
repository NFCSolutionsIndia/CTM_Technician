package noman.weekcalendar.listener;

import org.joda.time.DateTime;


public interface OnWeekChangeListener {

    void onWeekChange(DateTime firstDayOfTheWeek,DateTime lastDayOfTheWeek, boolean forward);
}
