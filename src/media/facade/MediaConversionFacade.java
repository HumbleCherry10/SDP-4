package media.facade;

import media.VideoFile;
import media.codecs.Codec;
import media.subsystem.AudioMixer;
import media.subsystem.BitrateReader;
import media.subsystem.CodecFactory;
import media.subsystem.MetadataReader;

import java.io.File;

/**
 * Facade class that provides a simple, unified interface to the complex
 * media conversion subsystem.
 *
 * <p>It orchestrates metadata extraction, codec resolution, bitrate transcoding,
 * and audio normalization in the proper sequence without exposing any subsystem
 * internals to the client.</p>
 */
public class MediaConversionFacade {

    /**
     * Converts a media file to the requested target format.
     *
     * @param fileName     the name or path of the input file (e.g. "summer_trip.ogg")
     * @param targetFormat the target container/codec format (e.g. "mp4", "ogg")
     * @return a File object representing the converted media file
     * @throws IllegalArgumentException if fileName or targetFormat is empty or invalid
     * @throws UnsupportedOperationException if source or target format is unsupported
     */
    public File convertMedia(String fileName, String targetFormat) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("Source file name must not be null or empty.");
        }
        if (targetFormat == null || targetFormat.trim().isEmpty()) {
            throw new IllegalArgumentException("Target format must not be null or empty.");
        }

        System.out.println("----------------------------------------------------------------------");
        System.out.println("[MediaConversionFacade] Initializing conversion: " + fileName + " -> " + targetFormat);
        System.out.println("----------------------------------------------------------------------");

        // Step 1: Wrap file into subsystem domain entity
        VideoFile file = new VideoFile(fileName);

        // Step 2: Extract container headers and metadata
        MetadataReader.MediaMetadata metadata = MetadataReader.readMetadata(file);
        System.out.println("[MediaConversionFacade] File metadata: " + metadata);

        // Step 3: Resolve source and destination codecs via factory
        Codec sourceCodec = CodecFactory.extract(file);
        Codec destinationCodec = CodecFactory.getCodec(targetFormat);

        // Step 4: Read raw bitstream and transcode buffer bitrate
        byte[] rawBuffer = BitrateReader.read(file, sourceCodec);
        byte[] convertedBuffer = BitrateReader.convert(rawBuffer, sourceCodec, destinationCodec);

        // Step 5: Post-process audio stream (mix channels & normalize gain)
        byte[] finalBuffer = AudioMixer.fixAudio(convertedBuffer);

        // Step 6: Package the processed buffer into the target file representation
        String baseName = fileName.contains(".")
                ? fileName.substring(0, fileName.lastIndexOf('.'))
                : fileName;
        String outputFileName = baseName + "_converted." + destinationCodec.getFormat();
        File resultFile = new File(outputFileName);

        System.out.println("----------------------------------------------------------------------");
        System.out.println("[MediaConversionFacade] Conversion completed successfully: " + resultFile.getName());
        System.out.println("----------------------------------------------------------------------");

        return resultFile;
    }
}
