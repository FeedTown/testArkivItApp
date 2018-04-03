package com.arkivit.model;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;*/
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import net.sf.image4j.codec.ico.ICODecoder;


public class ImageFileConverter {


	static MetadataToExcelGUI model;
	private String fileNameWithOutExt = "";


	public ImageFileConverter(MetadataToExcelGUI model) {
		this.model = model;
	}

	public void convertImage() throws IOException{
		for(File s : model.getFileList())
		{
			fileNameWithOutExt = FilenameUtils.removeExtension(s.getName());
			if(s.getName().endsWith(".GIF") || s.getName().endsWith(".gif") || s.getName().endsWith(".JPG") || s.getName().endsWith(".jpg")
					|| s.getName().endsWith(".BMP") || s.getName().endsWith(".bmp")) {
				BufferedImage bi = ImageIO.read(new File(s.getAbsolutePath()));
				ImageIO.write(bi, "jpeg", new File(s.getParentFile().getAbsoluteFile(), fileNameWithOutExt + ".jpeg"));



				System.out.println("Image " + s.getName() + " was converted succesfully.");
			}

			else if(s.getName().endsWith(".ico") || s.getName().endsWith(".ICO")) {
				/*List<BufferedImage> bi = ICODecoder.read(new File(s.getAbsolutePath()));
				System.out.println("reading");
				ImageIO.write(bi.get(0), "png", new File(s.getParentFile().getAbsoluteFile(), fileNameWithOutExt + ".png"));
				System.out.println("Ico was converted.");*/
				
				File oldFile = new File(s.getAbsolutePath());
				try (InputStream inputStream = new FileInputStream(oldFile)) {
				     List<BufferedImage> bi = ICODecoder.read(inputStream);
				     ImageIO.write(bi.get(0), "png", new File(s.getParentFile().getAbsoluteFile(), fileNameWithOutExt + ".png"));

				} catch (IOException e) {
				   e.printStackTrace();
				}
				
			}

			else {
				System.out.println("no files converted");
			}


		}

		storeOriginalImages();

	}

	public void storeOriginalImages() {
		for(File file: model.getFileList()) {
			if(file.getName().endsWith(".GIF") || file.getName().endsWith(".gif") || file.getName().endsWith(".JPG") 
					|| file.getName().endsWith(".jpg")  || file.getName().endsWith(".ico") || file.getName().endsWith(".ICO")
					|| file.getName().endsWith(".BMP") || file.getName().endsWith(".bmp")) {
				System.out.println("in copy else");
				File illegalExtension = new File(file.getAbsolutePath());
				File illegalExtensionDest = new File(model.getTargetexcelFilepath() + "/" + model.getFolderName() + "_img_backup");
				System.out.println(illegalExtension + "/" + illegalExtensionDest);
				
				try {
					FileUtils.moveFileToDirectory(illegalExtension, illegalExtensionDest, true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}