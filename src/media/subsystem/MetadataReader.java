package media.subsystem;

import media.VideoFile;

/**
 * Subsystem component responsible for inspecting media file headers
 * and extracting container metadata.
 */
public class MetadataReader {

    public static class MediaMetadata {
        private final String title;
        private final String format;
        private final int estimatedDurationSeconds;
        private final int sampleRateHz;

        public MediaMetadata(String title, String format, int estimatedDurationSeconds, int sampleRateHz) {
            this.title = title;
            this.format = format;
            this.estimatedDurationSeconds = estimatedDurationSeconds;
            this.sampleRateHz = sampleRateHz;
        }

        public String getTitle() {
            return title;
        }

        public String getFormat() {
            return format;
        }

        public int getEstimatedDurationSeconds() {
            return estimatedDurationSeconds;
        }

        public int getSampleRateHz() {
            return sampleRateHz;
        }

        @Override
        public String toString() {
            return "MediaMetadata[title='" + title + "', format='." + format + "', duration="
                    + estimatedDurationSeconds + "s, sampleRate=" + sampleRateHz + "Hz]";
        }
    }

    public static MediaMetadata readMetadata(VideoFile file) {
        if (file == null) {
            throw new IllegalArgumentException("VideoFile must not be null.");
        }
        System.out.println("[MetadataReader] Parsing container headers and metadata for: " + file.getName());
        return new MediaMetadata(file.getName(), file.getCodecType(), 120, 44100);
    }
}
