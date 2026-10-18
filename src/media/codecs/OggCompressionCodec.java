package media.codecs;

/**
 * Concrete codec implementation for Ogg Vorbis / Theora formats.
 */
public class OggCompressionCodec implements Codec {
    public static final String FORMAT = "ogg";

    @Override
    public String getFormat() {
        return FORMAT;
    }

    @Override
    public String getDescription() {
        return "Ogg Vorbis / Theora open container compression codec";
    }
}
