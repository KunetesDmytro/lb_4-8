package service;

import model.MusicDisc;
import model.composition.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DiscProcessingSystem {
    private String fileName;
    private MusicDisc currentDisc;

    public DiscProcessingSystem(String fileName) {
        this.fileName = fileName;
        // За замовчуванням створюємо порожній диск
        this.currentDisc = new MusicDisc("New Collection");
    }

    public MusicDisc getCurrentDisc() { return currentDisc; }

    public void createNewDisc(String name) {
        this.currentDisc = new MusicDisc(name);
        System.out.println("New disc '" + name + "' created.");
    }

    public void addSong(String title, String artist, int duration, MusicStyle style, String language) {
        currentDisc.addTrack(new Song(title, artist, duration, style, language));
        System.out.println("Song added successfully.");
    }

    public void addInstrumental(String title, String artist, int duration, MusicStyle style, String instrument) {
        currentDisc.addTrack(new InstrumentalTrack(title, artist, duration, style, instrument));
        System.out.println("Instrumental track added successfully.");
    }

    public void viewCurrentDisc() {
        System.out.println("--- Current Disc: " + currentDisc.getDiscName() + " ---");
        List<MusicalComposition> tracks = currentDisc.getTrackList();
        if (tracks.isEmpty()) {
            System.out.println("(Disc is empty)");
        } else {
            for (int i = 0; i < tracks.size(); i++) {
                System.out.println((i + 1) + ". " + tracks.get(i));
            }
            System.out.println("Total duration: " + formatDuration(currentDisc.getTotalDuration()));
        }
    }

    public void sortTracksByStyle() {
        currentDisc.sortByStyle();
        System.out.println("Tracks sorted by style.");
    }

    public void findTracksByDuration(int min, int max) {
        System.out.println("--- Search Results (" + min + "-" + max + " sec) ---");
        List<MusicalComposition> found = currentDisc.findByDurationRange(min, max);
        if (found.isEmpty()) {
            System.out.println("No tracks found in this range.");
        } else {
            found.forEach(System.out::println);
        }
    }

    public void saveCurrentDisc() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {
            writer.println(currentDisc.getDiscName()); // Перший рядок - назва диска
            for (MusicalComposition track : currentDisc.getTrackList()) {
                StringBuilder sb = new StringBuilder();
                // TYPE|TITLE|ARTIST|DURATION|STYLE|EXTRA
                if (track instanceof Song) {
                    sb.append("SONG|");
                    sb.append(((Song) track).getLyricsLanguage());
                } else if (track instanceof InstrumentalTrack) {
                    sb.append("INST|");
                    sb.append(((InstrumentalTrack) track).getMainInstrument());
                }
                sb.append("|").append(track.getTitle())
                        .append("|").append(track.getArtist())
                        .append("|").append(track.getDurationSeconds())
                        .append("|").append(track.getStyle());
                writer.println(sb.toString());
            }
            System.out.println("Disc saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadDisc() {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not found. Creating new empty disc.");
            createNewDisc("New Disc");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String discName = reader.readLine();
            if (discName == null) discName = "Imported Disc";
            this.currentDisc = new MusicDisc(discName);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 6) continue;

                String type = parts[0];
                String extra = parts[1]; // Lang or Instrument
                String title = parts[2];
                String artist = parts[3];
                int duration = Integer.parseInt(parts[4]);
                MusicStyle style = MusicStyle.valueOf(parts[5]);

                if (type.equals("SONG")) {
                    currentDisc.addTrack(new Song(title, artist, duration, style, extra));
                } else if (type.equals("INST")) {
                    currentDisc.addTrack(new InstrumentalTrack(title, artist, duration, style, extra));
                }
            }
            System.out.println("Disc loaded successfully.");
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    public void deleteSavedData() {
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("Saved file deleted.");
            this.currentDisc = new MusicDisc("Empty Disc");
        } else {
            System.out.println("Failed to delete file (or it doesn't exist).");
        }
    }

    public void stopProgram() {
        System.out.println("Exiting...");
        System.exit(0);
    }

    private String formatDuration(int totalSeconds) {
        return (totalSeconds / 60) + " min " + (totalSeconds % 60) + " sec";
    }
}
