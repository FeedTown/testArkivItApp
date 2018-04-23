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
	private ArrayList<File> imgFileList = new ArrayList<>();


	public ImageFileConverter() {
	}


	public File convertImage(File file, String sourceFolderPath) throws IOException{
		int i = 0;
		//File tempFile = new File(file.getAbsolutePath());
		imgFileList.add(file);
		/*for(int i=0; i<imgFileList.size(); i++)
		{
			//file = imgFileList.get(i);
			System.out.println("In for loop");
			if(imgFileList.get(i).getName().endsWith(".GIF") || imgFileList.get(i).getName().endsWith(".gif") || imgFileList.get(i).getName().endsWith(".JPG") || 
					imgFileList.get(i).getName().endsWith(".jpg")|| imgFileList.get(i).getName().endsWith(".BMP") || imgFileList.get(i).getName().endsWith(".bmp") ||
					imgFileList.get(i).getName().endsWith(".WBMP") || imgFileList.get(i).getName().endsWith(".wbmp")) {

				fileNameWithOutExt = FilenameUtils.removeExtension(imgFileList.get(i).getName());
				BufferedImage bi = ImageIO.read(new File(imgFileList.get(i).getAbsolutePath()));
				ImageIO.write(bi, "jpeg", new File(imgFileList.get(i).getParentFile().getAbsoluteFile(),
						fileNameWithOutExt + ".jpeg"));

				System.out.println("Image " + imgFileList.get(i).getName() + " was converted succesfully.");
				imgFileList.add(file);

			}
			if(imgFileList.get(i).getName().endsWith(".ico") || imgFileList.get(i).getName().endsWith(".ICO")) {

				InputStream inputStream = new FileInputStream(imgFileList.get(i));
				System.out.println("reading ICO");
				fileNameWithOutExt = FilenameUtils.removeExtension(imgFileList.get(i).getName());
				ImageIO.write(ICODecoder.read(inputStream).get(0), "png", new File(imgFileList.get(i).getParentFile().getAbsoluteFile(), 
						fileNameWithOutExt + ".png"));
				System.out.println("Ico was converted.");
				inputStream.close();
				System.out.println("End of ico con");
				imgFileList.add(file);

			}
			if(imgFileList.get(i).getName().endsWith(".svg") || imgFileList.get(i).getName().endsWith(".SVG")) {
				System.out.println("SVG BLOCK");
				String svgURI = Paths.get(imgFileList.get(i).getAbsolutePath()).toUri().toString();
				TranscoderInput input = new TranscoderInput(svgURI);
				fileNameWithOutExt = FilenameUtils.removeExtension(imgFileList.get(i).getName());
				OutputStream ostream = new FileOutputStream(new File(imgFileList.get(i).getParentFile().getAbsoluteFile(), 
						fileNameWithOutExt + ".png"));
				TranscoderOutput output = new TranscoderOutput(ostream);

				PNGTranscoder t = new PNGTranscoder();

				t.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, new Float(600));
				t.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, new Float(600));
				System.out.println("In SVG block");

				try {
					t.transcode(input, output);
					imgFileList.add(file);
				} catch (TranscoderException e) {
					e.printStackTrace();
				}
				// flush and close the stream
				ostream.flush();
				ostream.close();

			}


			//file = imgFileList.get(i);
			//imgFileList.add(file);
			//storeOriginalImages1(file);
			return file;
		} */ 

		for(File f : imgFileList)
		{
			//f = imgFileList.get(i);

			if(f.getName().endsWith(".GIF") || f.getName().endsWith(".gif") || f.getName().endsWith(".JPG") || 
					f.getName().endsWith(".jpg")|| f.getName().endsWith(".BMP") || f.getName().endsWith(".bmp") ||
					f.getName().endsWith(".WBMP") || f.getName().endsWith(".wbmp")) {

				fileNameWithOutExt = FilenameUtils.removeExtension(f.getName());
				BufferedImage bi = ImageIO.read(new File(f.getAbsolutePath()));
				ImageIO.write(bi, "jpeg", new File(f.getParentFile().getAbsoluteFile(),
						fileNameWithOutExt + ".jpeg"));

				System.out.println("Image " + f.getName() + " was converted succesfully.");
				imgFileList.add(f);

			}
			if(f.getName().endsWith(".ico") || f.getName().endsWith(".ICO")) {

				InputStream inputStream = new FileInputStream(f);
				System.out.println("reading ICO");
				fileNameWithOutExt = FilenameUtils.removeExtension(f.getName());
				ImageIO.write(ICODecoder.read(inputStream).get(0), "png", new File(f.getParentFile().getAbsoluteFile(), 
						fileNameWithOutExt + ".png"));
				System.out.println("Ico was converted.");
				inputStream.close();
				imgFileList.add(f);

			}
			if(f.getName().endsWith(".svg") || f.getName().endsWith(".SVG")) {
				System.out.println("SVG BLOCK");
				String svgURI = Paths.get(f.getAbsolutePath()).toUri().toString();
				TranscoderInput input = new TranscoderInput(svgURI);
				fileNameWithOutExt = FilenameUtils.removeExtension(f.getName());
				OutputStream ostream = new FileOutputStream(new File(f.getParentFile().getAbsoluteFile(), 
						fileNameWithOutExt + ".png"));
				TranscoderOutput output = new TranscoderOutput(ostream);

				PNGTranscoder t = new PNGTranscoder();

				t.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, new Float(600));
				t.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, new Float(600));
				System.out.println("In SVG block");

				try 
				{
					t.transcode(input, output);
					imgFileList.add(f);
				} 
				catch (TranscoderException e) 
				{
					e.printStackTrace();
				}
				// flush and close the stream
				ostream.flush();
				ostream.close();

			}

			/*else {
				System.out.println("no files converted");
			}*/
			//file = imgFileList.get(i);
			//imgFileList.add(f);
			storeOriginalImages1(file);
			i++;
			return f;
		}
		System.out.println("VERY End of method");

		return file;
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

	public File storeOriginalImages1(File illegalExtension) throws IOException 
	{
		File illegalExtensionDest = new File(model.getTargetexcelFilepath() + "/" + model.getFolderName() + "_img_backup");
		System.out.println(illegalExtension + "/" + illegalExtensionDest);
		try {
			FileUtils.moveFileToDirectory(illegalExtension, illegalExtensionDest, true);
		}catch(IOException e)
		{
			e.printStackTrace();
		}

		return illegalExtensionDest;
	} 

}