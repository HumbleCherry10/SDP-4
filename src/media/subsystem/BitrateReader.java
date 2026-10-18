package media.subsystem;

import media.VideoFile;
import media.codecs.Codec;

import java.nio.charset.StandardCharsets;

/**
 * Subsystem component responsible for reading media bitstreams and
 * performing bitrate resampling and transcoding between codecs.
 */
public class BitrateReader {

    public static byte[] read(VideoFile file, Codec sourceCodec) {
        if (file == null || sourceCodec == null) {
            throw new IllegalArgumentException("VideoFile and Codec must not be null.");
        }
        System.out.println("[BitrateReader] Reading raw bitstream from: " + file.getName()
                + " using [" + sourceCodec.getDescription() + "]");
        return file.getData();
    }

    public static byte[] convert(byte[] buffer, Codec sourceCodec, Codec destinationCodec) {
        if (buffer == null || sourceCodec == null || destinationCodec == null) {
            throw new IllegalArgumentException("Buffer and codecs must not be null.");
        }
        System.out.println("[BitrateReader] Resampling bitrate and transcoding buffer: ["
                + sourceCodec.getFormat() + "] -> [" + destinationCodec.getFormat() + "]");

        String originalContent = new String(buffer, StandardCharsets.UTF_8);
        String transcodedContent = originalContent + " -> TranscodedTo(" + destinationCodec.getFormat() + ")";
        return transcodedContent.getBytes(StandardCharsets.UTF_8);
    }
}
