package model.composition;

public abstract class MusicalComposition {
    private String title;
    private String artist;
    private int durationSeconds; // Тривалість у секундах
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

    // Абстрактний метод для отримання детального опису
    public abstract String toString();

    // Форматування часу (допоміжний метод)
    protected String getFormattedDuration() {
        int min = durationSeconds / 60;
        int sec = durationSeconds % 60;
        return String.format("%d:%02d", min, sec);
    }
}