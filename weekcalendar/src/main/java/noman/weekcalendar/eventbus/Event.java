package noman.weekcalendar.eventbus;

import android.view.View;
import android.widget.TextView;
import org.joda.time.DateTime;


public class Event {
    public static class OnDateClickEvent {
        public OnDateClickEvent(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        private DateTime dateTime;

        public DateTime getDateTime() {
            return dateTime;
        }
    }

    public static class InvalidateEvent {
    }

    public static class UpdateSelectedDateEvent {

        public UpdateSelectedDateEvent(int direction) {
            this.direction = direction;
        }

        public int getDirection() {
            return direction;
        }

        private int direction;
    }

    public static class SetCurrentPageEvent {
        public int getDirection() {
            return direction;
        }
        public SetCurrentPageEvent(int direction) {
            this.direction = direction;
        }

        private int direction;
    }

    public static class ResetEvent {
    }

    public static class SetSelectedDateEvent {
        public SetSelectedDateEvent(DateTime selectedDate) {
            this.selectedDate = selectedDate;
        }
        public DateTime getSelectedDate() {
            return selectedDate;
        }
        private DateTime selectedDate;
    }

    public static class SetStartDateEvent {
        public SetStartDateEvent(DateTime startDate) {
            this.startDate = startDate;
        }
        public DateTime getStartDate() {
            return startDate;
        }
        private DateTime startDate;
    }

    public static class OnDayDecorateEvent {
        private final View view;
        private final TextView dayTextView;
        private final DateTime dateTime;
        private DateTime firstDay,lastDay;
        private DateTime selectedDateTime;

        public OnDayDecorateEvent(View view, TextView dayTextView, DateTime dateTime,
                                  DateTime firstDayOfTheWeek,DateTime lastDayOfTheWeek, DateTime selectedDateTime) {
            this.view = view;
            this.dayTextView = dayTextView;
            this.dateTime = dateTime;
            this.firstDay = firstDayOfTheWeek;
            this.lastDay = lastDayOfTheWeek;
            this.selectedDateTime = selectedDateTime;
        }

        public View getView() {
            return view;
        }

        public TextView getDayTextView() {
            return dayTextView;
        }

        public DateTime getDateTime() {
            return dateTime;
        }

        public DateTime getFirstDay() {
            return firstDay;
        }

        public DateTime getSelectedDateTime() {
            return selectedDateTime;
        }
    }

    public static class OnWeekChange {

        private final DateTime firstDayOfTheWeek,lastDayOfTheWeek;
        private final boolean forward;

        public OnWeekChange(DateTime firstDayOfTheWeek,DateTime lastDayOfTheWeek, boolean isForward) {
            this.firstDayOfTheWeek = firstDayOfTheWeek;
            this.lastDayOfTheWeek = lastDayOfTheWeek;
            this.forward = isForward;
        }

        public DateTime getFirstDayOfTheWeek() {
            return firstDayOfTheWeek;
        } public DateTime getLastDayOfTheWeek() {
            return lastDayOfTheWeek;
        }

        public boolean isForward() {
            return forward;
        }
    }

    public static class OnUpdateUi {
    }
}
