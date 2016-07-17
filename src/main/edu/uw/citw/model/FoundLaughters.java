package edu.uw.citw.model;

import java.util.List;

/**
 * A model representing the found laughter instances in video/audio file.
 *
 * Created by milesdowe on 7/12/16.
 */
public class FoundLaughters {

    private String          filename;
    private List<StartStop> timestamps;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<StartStop> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<StartStop> timestamps) {
        this.timestamps = timestamps;
    }

    private class StartStop {
        private long start;
        private long stop;

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getStop() {
            return stop;
        }

        public void setStop(long stop) {
            this.stop = stop;
        }
    }
}
