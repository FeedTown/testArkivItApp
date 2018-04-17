package Test.code;

import java.io.*;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import java.nio.file.Paths;
import java.nio.file.Path;
public class SaveAsJPEG {
    public static void main(String[] args) throws Exception {
        //Step -1: We read the input SVG document into Transcoder Input
        //We use Java NIO for this purpose
        String svg_URI_input = Paths.get("C:\\Users\\Kevin\\Desktop\\test2\\img\\kiwi.svg").toUri().toURL().toString();
        TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);        
        //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
        OutputStream png_ostream = new FileOutputStream("C:\\Users\\Kevin\\Desktop\\test2\\img\\kiwi.png");
        TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);              
        // Step-3: Create PNGTranscoder and define hints if required
        PNGTranscoder my_converter = new PNGTranscoder();        
        // Step-4: Convert and Write output
        my_converter.transcode(input_svg_image, output_png_image);
        // Step 5- close / flush Output Stream
        png_ostream.flush();
        png_ostream.close();        
    }
}