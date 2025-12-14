package model.composition;

public abstract class MusicalComposition {
    private String title;
    private String artist;
    private int durationSeconds;
    private MusicStyle style;

    public MusicalComposition(String title, String artist, int durationSeconds, MusicStyle style) {
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
        this.style = style;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getDurationSeconds() { return durationSeconds; }
    public MusicStyle getStyle() { return style; }

    public abstract String toString();

    protected String getFormattedDuration() {
        int min = durationSeconds / 60;
        int sec = durationSeconds % 60;
        return String.format("%d:%02d", min, sec);
    }
}