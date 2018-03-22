package com.arkivit.model;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import net.sf.image4j.codec.ico.ICODecoder;


public class ImageFileConverter {


	static MetadataToExcelGUI model;
	//static File imageFile = new File("C:/Users/Kevin/Desktop/test2/img/penguin.ico");
	private File imageFile;
	private String fileNameWithOutExt = "";


	public ImageFileConverter(MetadataToExcelGUI model) {
		this.model = model;
	}

	public void convertImage() throws IOException {
		for(File s : model.getFileList())
		{
			fileNameWithOutExt = FilenameUtils.removeExtension(s.getName());
			if(s.getName().endsWith(".GIF") || s.getName().endsWith(".gif") || s.getName().endsWith(".JPG") || s.getName().endsWith(".jpg")
					|| s.getName().endsWith(".BMP") || s.getName().endsWith(".bmp")) {
				BufferedImage bi = ImageIO.read(new File(s.getAbsolutePath()));
				ImageIO.write(bi, "jpeg", new File(s.getParentFile().getAbsoluteFile(), fileNameWithOutExt + ".jpeg"));
				


				System.out.println("Image " + s.getName() + " was converted succesfully.");
			}

			else if(s.getName().endsWith(".ico")) {
				List<BufferedImage> bi = ICODecoder.read(new File(s.getAbsolutePath()));
				System.out.println("reading2");
				ImageIO.write(bi.get(0), "png", new File(s.getParentFile().getAbsoluteFile(), fileNameWithOutExt + ".png"));
				System.out.println("Ico was converted.");
			}
			else {
				System.out.println("no files converted");
			}
		}

	}
}


