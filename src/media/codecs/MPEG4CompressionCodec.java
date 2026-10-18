package media.codecs;

/**
 * Concrete codec implementation for MPEG-4 / H.264 formats.
 */
public class MPEG4CompressionCodec implements Codec {
    public static final String FORMAT = "mp4";

    @Override
    public String getFormat() {
        return FORMAT;
    }

    @Override
    public String getDescription() {
        return "MPEG-4 Part 14 (H.264 / AAC) compression codec";
    }
}
