import media.VideoFile;
import media.codecs.Codec;
import media.facade.MediaConversionFacade;
import media.subsystem.AudioMixer;
import media.subsystem.BitrateReader;
import media.subsystem.CodecFactory;
import media.subsystem.MetadataReader;

import java.io.File;

/**
 * Client application and test harness demonstrating the Facade Design Pattern.
 *
 * <p>Demonstrates:
 * 1. Standard Client converting media via a simple, single-line Facade method.
 * 2. Standard Client converting in reverse format direction.
 * 3. Robust error handling for unsupported formats and invalid inputs.
 * 4. Advanced Client bypassing the Facade to interact directly with subsystem classes,
 *    verifying that subsystem classes remain fully decoupled and unaware of the Facade.</p>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("ShP-2216 Software Design Patterns | Assignment #4: Facade Pattern");
        System.out.println("Student: Dias Tursynbay (SE-2537) | Astana IT University");
        System.out.println("======================================================================\n");

        MediaConversionFacade facade = new MediaConversionFacade();
        int passedChecks = 0;
        int totalChecks = 4;

        // --------------------------------------------------------------------
        // DEMO 1: Standard Client converting OGG to MP4 via Facade
        // --------------------------------------------------------------------
        System.out.println(">>> DEMO 1: Standard Client converts 'summer_trip.ogg' to 'mp4' via Facade");
        File mp4Result = facade.convertMedia("summer_trip.ogg", "mp4");
        if (mp4Result != null && mp4Result.getName().equals("summer_trip_converted.mp4")) {
            System.out.println("[PASS] Demo 1 verified: Client received " + mp4Result.getName() + "\n");
            passedChecks++;
        } else {
            System.err.println("[FAIL] Demo 1 failed unexpected file result.\n");
        }

        // --------------------------------------------------------------------
        // DEMO 2: Standard Client converting MP4 to OGG via Facade
        // --------------------------------------------------------------------
        System.out.println(">>> DEMO 2: Standard Client converts 'presentation.mp4' to 'ogg' via Facade");
        File oggResult = facade.convertMedia("presentation.mp4", "ogg");
        if (oggResult != null && oggResult.getName().equals("presentation_converted.ogg")) {
            System.out.println("[PASS] Demo 2 verified: Client received " + oggResult.getName() + "\n");
            passedChecks++;
        } else {
            System.err.println("[FAIL] Demo 2 failed unexpected file result.\n");
        }

        // --------------------------------------------------------------------
        // DEMO 3: Error Handling via Facade for Unsupported Format
        // --------------------------------------------------------------------
        System.out.println(">>> DEMO 3: Error handling test for unsupported codec format ('avi')");
        try {
            facade.convertMedia("archive_video.mp4", "avi");
            System.err.println("[FAIL] Demo 3 failed: exception was not thrown.\n");
        } catch (UnsupportedOperationException ex) {
            System.out.println("[PASS] Demo 3 verified: Expected exception caught -> \"" + ex.getMessage() + "\"\n");
            passedChecks++;
        }

        // --------------------------------------------------------------------
        // DEMO 4: Advanced Client Bypassing Facade (Direct Subsystem Access)
        // --------------------------------------------------------------------
        System.out.println(">>> DEMO 4: Advanced Client directly orchestrating subsystem classes");
        System.out.println("           (Proves subsystem classes have no back-reference to Facade)");

        // 1. Direct model instantiation
        VideoFile advancedFile = new VideoFile("raw_footage.ogg");

        // 2. Direct metadata extraction
        MetadataReader.MediaMetadata customMeta = MetadataReader.readMetadata(advancedFile);

        // 3. Direct codec lookup
        Codec srcCodec = CodecFactory.extract(advancedFile);
        Codec dstCodec = CodecFactory.getCodec("mp4");

        // 4. Direct stream reading and transcoding
        byte[] rawBytes = BitrateReader.read(advancedFile, srcCodec);
        byte[] transcodedBytes = BitrateReader.convert(rawBytes, srcCodec, dstCodec);

        // 5. Direct audio mixing
        byte[] mixedBytes = AudioMixer.fixAudio(transcodedBytes);

        // 6. Direct output file construction with custom naming
        File customResult = new File("raw_footage_custom." + dstCodec.getFormat());

        if (customResult.getName().equals("raw_footage_custom.mp4") && mixedBytes.length > 0) {
            System.out.println("[PASS] Demo 4 verified: Advanced client executed custom pipeline directly -> "
                    + customResult.getName() + "\n");
            passedChecks++;
        } else {
            System.err.println("[FAIL] Demo 4 failed.\n");
        }

        // --------------------------------------------------------------------
        // Summary
        // --------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("DEMONSTRATION EXECUTION SUMMARY: " + passedChecks + "/" + totalChecks + " CHECKS PASSED");
        System.out.println("======================================================================");
    }
}
