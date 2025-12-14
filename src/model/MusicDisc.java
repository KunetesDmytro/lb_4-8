package model;

import model.composition.MusicalComposition;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MusicDisc {
    private String discName;
    private List<MusicalComposition> trackList;

    public MusicDisc(String discName) {
        this.discName = discName;
        this.trackList = new ArrayList<>();
    }

    public String getDiscName() { return discName; }

    public void addTrack(MusicalComposition track) {
        trackList.add(track);
    }

    public List<MusicalComposition> getTrackList() {
        return trackList;
    }

    public int getTotalDuration() {
        return trackList.stream().mapToInt(MusicalComposition::getDurationSeconds).sum();
    }

    public void sortByStyle() {
        trackList.sort(Comparator.comparing(MusicalComposition::getStyle));
    }

    public List<MusicalComposition> findByDurationRange(int minSec, int maxSec) {
        return trackList.stream()
                .filter(t -> t.getDurationSeconds() >= minSec && t.getDurationSeconds() <= maxSec)
                .collect(Collectors.toList());
    }

    public void clear() {
        trackList.clear();
    }

    @Override
    public String toString() {
        return "Disc: '" + discName + "' (Tracks: " + trackList.size() + ")";
    }
}