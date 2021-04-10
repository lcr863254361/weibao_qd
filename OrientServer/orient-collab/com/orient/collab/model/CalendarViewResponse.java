package com.orient.collab.model;

import java.util.List;

/**
 * CalendarViewResponse
 *
 * @author Seraph
 *         2016-08-29 下午3:10
 */
public class CalendarViewResponse {

    public List<CalendarEvent> getEvts() {
        return evts;
    }

    public void setEvts(List<CalendarEvent> evts) {
        this.evts = evts;
    }


    private List<CalendarEvent> evts;
}
