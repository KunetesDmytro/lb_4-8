package model.composition;

public class Song extends MusicalComposition {
    private String lyricsLanguage;

    public Song(String title, String artist, int durationSeconds, MusicStyle style, String lyricsLanguage) {
        super(title, artist, durationSeconds, style);
        this.lyricsLanguage = lyricsLanguage;
    }

    public String getLyricsLanguage() { return lyricsLanguage; }

    @Override
    public String toString() {
        return String.format("[Song] %s - %s (%s) | Style: %s | Lang: %s",
                getArtist(), getTitle(), getFormattedDuration(), getStyle(), lyricsLanguage);
    }
}