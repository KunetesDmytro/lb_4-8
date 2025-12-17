package service;

import model.MusicDisc;
import model.composition.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DiscProcessingSystem {
    private static final Logger LOGGER = LogManager.getLogger(DiscProcessingSystem.class);

    private String fileName;
    private MusicDisc currentDisc;

    public DiscProcessingSystem(String fileName) {
        this.fileName = fileName;
        this.currentDisc = new MusicDisc("Default Disc");
        LOGGER.info("System initialized. Target file: {}", fileName); // Лог INFO
        loadDisc();
    }

    public MusicDisc getCurrentDisc() { return currentDisc; }

    public void createNewDisc(String name) {
        this.currentDisc = new MusicDisc(name);
        System.out.println("New disc '" + name + "' created.");
        LOGGER.info("Created new disc with name: {}", name); // Лог дії
    }

    public void addSong(String title, String artist, int duration, MusicStyle style, String language) {
        currentDisc.addTrack(new Song(title, artist, duration, style, language));
        System.out.println("Song added successfully.");
        LOGGER.info("Added Song: {} by {}", title, artist); // Лог додавання
    }

    public void addInstrumental(String title, String artist, int duration, MusicStyle style, String instrument) {
        currentDisc.addTrack(new InstrumentalTrack(title, artist, duration, style, instrument));
        System.out.println("Instrumental track added successfully.");
        LOGGER.info("Added Instrumental: {} by {}", title, artist);
    }

    public void viewCurrentDisc() {
        LOGGER.debug("User requested to view current disc content."); // Лог DEBUG
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
        LOGGER.info("Tracks sorted by style.");
    }

    public void findTracksByDuration(int min, int max) {
        System.out.println("--- Search Results (" + min + "-" + max + " sec) ---");
        List<MusicalComposition> found = currentDisc.findByDurationRange(min, max);
        LOGGER.info("Search performed. Range: {}-{}, Found: {} tracks", min, max, found.size());

        if (found.isEmpty()) {
            System.out.println("No tracks found in this range.");
        } else {
            found.forEach(System.out::println);
        }
    }

    public void saveCurrentDisc() {
        LOGGER.info("Attempting to save disc to file: {}", fileName);
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {
            writer.println(currentDisc.getDiscName());

            for (MusicalComposition track : currentDisc.getTrackList()) {
                StringBuilder sb = new StringBuilder();
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
            LOGGER.info("Successfully saved data to {}", fileName);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
            // КРИТИЧНА ПОМИЛКА - відправиться на пошту (через конфіг log4j2.xml)
            LOGGER.error("CRITICAL: Failed to save file {}", fileName, e);
        }
    }

    public void deleteSavedData() {
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("Saved file deleted.");
            this.currentDisc = new MusicDisc("Empty Disc");
            LOGGER.warn("File {} was deleted by user.", fileName); // Лог WARN
        } else {
            System.out.println("Failed to delete file (or it doesn't exist).");
            LOGGER.error("Failed to delete file {}", fileName);
        }
    }

    public void stopProgram() {
        System.out.println("Exiting...");
        LOGGER.info("Application stopped by user.");
        System.exit(0);
    }

    private String formatDuration(int totalSeconds) {
        return (totalSeconds / 60) + " min " + (totalSeconds % 60) + " sec";
    }

    public void loadDisc() {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not found. Creating new empty disc.");
            this.currentDisc = new MusicDisc("Default New Disc");
            LOGGER.info("File {} not found. Created new empty disc.", fileName);
            return;
        }
        System.out.println("EXECUTING: (loading disc from file " + fileName + "...)");

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String discName = reader.readLine();
            if (discName == null || discName.trim().isEmpty()) {
                discName = "Imported Disc";
            }
            this.currentDisc = new MusicDisc(discName.trim());
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 6) continue;

                String type = parts[0].trim();
                String extra = parts[1].trim();
                String title = parts[2].trim();
                String artist = parts[3].trim();
                int duration = Integer.parseInt(parts[4].trim());
                MusicStyle style = MusicStyle.valueOf(parts[5].trim());

                if (type.equals("SONG")) {
                    currentDisc.addTrack(new Song(title, artist, duration, style, extra));
                } else if (type.equals("INST")) {
                    currentDisc.addTrack(new InstrumentalTrack(title, artist, duration, style, extra));
                }
            }
            System.out.println("Disc '" + discName + "' loaded successfully.");
            LOGGER.info("Successfully loaded disc from file.");
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error loading file: " + e.getMessage());
            // КРИТИЧНА ПОМИЛКА для пошти
            LOGGER.fatal("FATAL ERROR: Corrupted data or IO error while loading {}", fileName, e);
            if (this.currentDisc == null) {
                this.currentDisc = new MusicDisc("Error Recovered Disc");
            }
        }
    }

    public void forceCriticalError() {
        try {
            throw new IOException("This is a TEST critical error for email alert.");
        } catch (IOException e) {
            LOGGER.error("Testing email alert system.", e);
        }
    }
}