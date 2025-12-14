package model.composition;

public class InstrumentalTrack extends MusicalComposition {
    private String mainInstrument;

    public InstrumentalTrack(String title, String artist, int durationSeconds, MusicStyle style, String mainInstrument) {
        super(title, artist, durationSeconds, style);
        this.mainInstrument = mainInstrument;
    }

    public String getMainInstrument() { return mainInstrument; }

    @Override
    public String toString() {
        return String.format("[Inst] %s - %s (%s) | Style: %s | Instr: %s",
                getArtist(), getTitle(), getFormattedDuration(), getStyle(), mainInstrument);
    }
}