package noman.weekcalendar.decorator;

import android.view.View;
import android.widget.TextView;
import org.joda.time.DateTime;


public interface DayDecorator {
    void decorate(View view, TextView dayTextView, DateTime dateTime, DateTime firstDayOfTheWeek, DateTime selectedDateTime);
}
