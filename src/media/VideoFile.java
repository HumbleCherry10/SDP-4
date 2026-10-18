package media;

import java.util.Objects;

/**
 * Represents a simulated video/media file containing container metadata
 * and binary payload.
 */
public class VideoFile {
    private final String name;
    private final String codecType;
    private final byte[] data;

    public VideoFile(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
        this.name = name.trim();
        int dotIndex = this.name.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < this.name.length() - 1) {
            this.codecType = this.name.substring(dotIndex + 1).toLowerCase();
        } else {
            this.codecType = "unknown";
        }
        this.data = ("Simulated binary media payload for [" + this.name + "]").getBytes();
    }

    public VideoFile(String name, byte[] data) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
        this.name = name.trim();
        int dotIndex = this.name.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < this.name.length() - 1) {
            this.codecType = this.name.substring(dotIndex + 1).toLowerCase();
        } else {
            this.codecType = "unknown";
        }
        this.data = Objects.requireNonNullElseGet(data, () -> new byte[0]);
    }

    public String getName() {
        return name;
    }

    public String getCodecType() {
        return codecType;
    }

    public byte[] getData() {
        return data;
    }
}
