package br.net.pin.srv_wav;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.SequenceInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.springframework.util.Base64Utils;

public class WAVWorker {

  public static String merge(String[] wavPaths) throws Exception {
    var audioInputStreams = new AudioInputStream[wavPaths.length];
    for (int i = 0; i < wavPaths.length; i++) {
      audioInputStreams[i] = AudioSystem.getAudioInputStream(new File(wavPaths[i]));
    }
    AudioInputStream appendedFiles = null;
    for (int i = 0; i < audioInputStreams.length - 1; i++) {
      if (i == 0) {
        appendedFiles = new AudioInputStream(
            new SequenceInputStream(audioInputStreams[i], audioInputStreams[i + 1]),
            audioInputStreams[i].getFormat(),
            audioInputStreams[i].getFrameLength() + audioInputStreams[i + 1].getFrameLength());
      } else {
        appendedFiles = new AudioInputStream(
            new SequenceInputStream(appendedFiles, audioInputStreams[i + 1]),
            appendedFiles.getFormat(),
            appendedFiles.getFrameLength() + audioInputStreams[i + 1].getFrameLength());
      }
    }
    var buffer = new ByteArrayOutputStream();
    AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, buffer);
    return Base64Utils.encodeToString(buffer.toByteArray());
  }

}
