package media.subsystem;

import media.VideoFile;
import media.codecs.Codec;
import media.codecs.MPEG4CompressionCodec;
import media.codecs.OggCompressionCodec;

/**
 * Subsystem factory responsible for resolving and instantiating appropriate
 * Codec objects based on file extensions or format strings.
 */
public class CodecFactory {

    public static Codec extract(VideoFile file) {
        if (file == null) {
            throw new IllegalArgumentException("VideoFile must not be null.");
        }
        String type = file.getCodecType();
        System.out.println("[CodecFactory] Detecting source codec from container: ." + type);
        return getCodec(type);
    }

    public static Codec getCodec(String format) {
        if (format == null || format.trim().isEmpty()) {
            throw new IllegalArgumentException("Format specification must not be null or empty.");
        }
        String normalized = format.trim().toLowerCase();
        if (normalized.startsWith(".")) {
            normalized = normalized.substring(1);
        }

        switch (normalized) {
            case "mp4":
                return new MPEG4CompressionCodec();
            case "ogg":
                return new OggCompressionCodec();
            default:
                throw new UnsupportedOperationException("Codec not supported for format: ." + format);
        }
    }
}
