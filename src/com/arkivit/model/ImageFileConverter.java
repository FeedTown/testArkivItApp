package com.arkivit.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import net.sf.image4j.codec.ico.ICODecoder;


public class ImageFileConverter {


	private MetadataToExcelGUI model;
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

			else {
				System.out.println("no files converted");
			}

		}

	}

	public void storeOriginalImages() throws IOException {
		for(File s: model.getFileList()) {
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

	}

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