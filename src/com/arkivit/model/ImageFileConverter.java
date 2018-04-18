package com.arkivit.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import net.sf.image4j.codec.ico.ICODecoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;


public class ImageFileConverter {


	private MetadataToExcelGUI model;
	private String fileNameWithOutExt = "";


	public ImageFileConverter(MetadataToExcelGUI model) {
		this.model = model;
	}

	@SuppressWarnings("deprecation")
	public void convertImage(ArrayList<File> file, String sourceFolderPath) throws IOException{
		for(File s : file)
		{
			fileNameWithOutExt = FilenameUtils.removeExtension(s.getName());
			if(s.getName().endsWith(".GIF") || s.getName().endsWith(".gif") || s.getName().endsWith(".JPG") || s.getName().endsWith(".jpg")
					|| s.getName().endsWith(".BMP") || s.getName().endsWith(".bmp") || s.getName().endsWith(".WBMP") || s.getName().endsWith(".wbmp")) {
				BufferedImage bi = ImageIO.read(new File(s.getAbsolutePath()));
				ImageIO.write(bi, "jpeg", new File(s.getParentFile().getAbsoluteFile(), fileNameWithOutExt + ".jpeg"));

				System.out.println("Image " + s.getName() + " was converted succesfully.");
				storeOriginalImages1(s);
			}
			else if(s.getName().endsWith(".ico") || s.getName().endsWith(".ICO")) {

				InputStream inputStream = new FileInputStream(s);
				System.out.println("reading2");
				ImageIO.write(ICODecoder.read(inputStream).get(0), "png", new File(s.getParentFile().getAbsoluteFile(), fileNameWithOutExt + ".png"));
				System.out.println("Ico was converted.");
				inputStream.close();

				storeOriginalImages1(s);

			}
			else if(s.getName().endsWith(".svg") || s.getName().endsWith(".SVG")) {
				
				String svgURI = Paths.get(s.getAbsolutePath()).toUri().toString();
				TranscoderInput input = new TranscoderInput(svgURI);
				
				OutputStream ostream = new FileOutputStream(new File(s.getParentFile().getAbsoluteFile(), fileNameWithOutExt + ".png"));
				TranscoderOutput output = new TranscoderOutput(ostream);
				
				PNGTranscoder t = new PNGTranscoder();
				
				t.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, new Float(600));
				t.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, new Float(600));
				
				try {
					t.transcode(input, output);
				} catch (TranscoderException e) {
					e.printStackTrace();
				}
				// flush and close the stream
				ostream.flush();
				ostream.close();
				storeOriginalImages1(s);
			}

			else {
				System.out.println("no files converted");
			}

		}
		
	}

	/*public void storeOriginalImages(ArrayList<File> file) throws IOException {
		for(File s: file) {
			if(s.getName().endsWith(".GIF") || s.getName().endsWith(".gif") || s.getName().endsWith(".JPG") 
					|| s.getName().endsWith(".jpg") || s.getName().endsWith(".BMP") 
					|| s.getName().endsWith(".bmp") || s.getName().endsWith(".ico") || s.getName().endsWith(".ICO")) {
				System.out.println("in copy else");
				//File illegalExtension = new File(s.getAbsolutePath());

				File illegalExtension = s;
				File illegalExtensionDest = new File(model.getTargetexcelFilepath() + "/" + model.getFolderName() + "_img_backup");
				System.out.println(illegalExtension + "/" + illegalExtensionDest);
				try {

					FileUtils.moveFileToDirectory(illegalExtension, illegalExtensionDest, true);
				}catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}

	} */

	public void storeOriginalImages1(File illegalExtension) throws IOException 
	{
		File illegalExtensionDest = new File(model.getTargetexcelFilepath() + "/" + model.getFolderName() + "_img_backup");
		System.out.println(illegalExtension + "/" + illegalExtensionDest);
		try {
			FileUtils.moveFileToDirectory(illegalExtension, illegalExtensionDest, true);
		}catch(IOException e)
		{
			e.printStackTrace();
		}

	}

}