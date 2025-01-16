package soundEffects;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlaySound {
    private String filePath;

    public PlaySound(String filePath) {
        this.filePath = filePath;
    }

    public void play() {
        try {
            // Loads sound file
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                throw new IllegalArgumentException("Sound file not found: " + filePath);
            }

            // Creates AudioInputStream to read loaded file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Reads the audio format and checks if it's supported
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16, 
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, 
                    baseFormat.getSampleRate(),
                    false
            );

            // Converts the stream if needed
            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);

            // Creates clip to play the sound
            Clip clip = AudioSystem.getClip();
            clip.open(convertedStream);
            // Plays the clip
            clip.start();
            
            // Wait for the clip to finish playing to avoid premature termination
            
            while (!clip.isRunning()) Thread.sleep(10);
            while (clip.isRunning()) Thread.sleep(10);

            // Closes everything used
            clip.close();
            audioStream.close();
            
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
