package soundEffects;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlaySound {
    private String soundFilePath;

    public PlaySound(String soundFilePath) { // play sound
        this.soundFilePath = soundFilePath;
    }

    public void play() {
        try {
            // Load the sound file
            File soundFile = new File(soundFilePath);
            if (!soundFile.exists()) {
                throw new IllegalArgumentException("Sound file not found: " + soundFilePath);
            }

            // Create an AudioInputStream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Get the audio format and check if it's supported
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16, // Bits per sample
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, // Frame size
                    baseFormat.getSampleRate(),
                    false // Big-endian
            );

            // Convert the stream if needed
            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);

            // Get a clip to play the sound
            Clip clip = AudioSystem.getClip();
            clip.open(convertedStream);

            // Play the clip
            clip.start();

            // Optional: Wait for the clip to finish playing
            while (!clip.isRunning()) Thread.sleep(10);
            while (clip.isRunning()) Thread.sleep(10);

            // Close resources
            clip.close();
            audioStream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
