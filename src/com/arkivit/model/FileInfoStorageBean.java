package com.arkivit.model;

public class FileInfoStorageBean {
	
	
	private String fileNameColl ,fileTypeNameColl,
	fileTypeVersionNameColl,fileSizeNameColl,charsetNameColl,
	durationColl, filePathNameColl, confidentialityColl,
	personalInformationHandelingNameColl,commentColl;
	
	
	
	public FileInfoStorageBean(String fileNameColl, String fileTypeNameColl, String fileTypeVersionNameColl,
			String fileSizeNameColl, String charsetNameColl, String durationColl, String filePathNameColl,
			String confidentialityColl, String personalInformationHandelingNameColl, String commentColl) 
	{
		
		this.fileNameColl = fileNameColl;
		this.fileTypeNameColl = fileTypeNameColl;
		this.fileTypeVersionNameColl = fileTypeVersionNameColl;
		this.fileSizeNameColl = fileSizeNameColl;
		this.charsetNameColl = charsetNameColl;
		this.durationColl = durationColl;
		this.filePathNameColl = filePathNameColl;
		this.confidentialityColl = confidentialityColl;
		this.personalInformationHandelingNameColl = personalInformationHandelingNameColl;
		this.commentColl = commentColl;
	}



	public String getFileNameColl() {
		return fileNameColl;
	}

	public void setFileNameColl(String fileNameColl) {
		this.fileNameColl = fileNameColl;
	}

	public String getFileTypeNameColl() {
		return fileTypeNameColl;
	}

	public void setFileTypeNameColl(String fileTypeNameColl) {
		this.fileTypeNameColl = fileTypeNameColl;
	}

	public String getFileTypeVersionNameColl() {
		return fileTypeVersionNameColl;
	}

	public void setFileTypeVersionNameColl(String fileTypeVersionNameColl) {
		this.fileTypeVersionNameColl = fileTypeVersionNameColl;
	}

	public String getFileSizeNameColl() {
		return fileSizeNameColl;
	}

	public void setFileSizeNameColl(String fileSizeNameColl) {
		this.fileSizeNameColl = fileSizeNameColl;
	}

	public String getCharsetNameColl() {
		return charsetNameColl;
	}

	public void setCharsetNameColl(String charsetNameColl) {
		this.charsetNameColl = charsetNameColl;
	}

	public String getDurationColl() {
		return durationColl;
	}

	public void setDurationColl(String durationColl) {
		this.durationColl = durationColl;
	}

	public String getFilePathNameColl() {
		return filePathNameColl;
	}

	public void setFilePathNameColl(String filePathNameColl) {
		this.filePathNameColl = filePathNameColl;
	}

	public String getConfidentialityColl() {
		return confidentialityColl;
	}

	public void setConfidentialityColl(String confidentialityColl) {
		this.confidentialityColl = confidentialityColl;
	}

	public String getPersonalInformationHandelingNameColl() {
		return personalInformationHandelingNameColl;
	}

	public void setPersonalInformationHandelingNameColl(String personalInformationHandelingNameColl) {
		this.personalInformationHandelingNameColl = personalInformationHandelingNameColl;
	}

	public String getCommentColl() {
		return commentColl;
	}

	public void setCommentColl(String commentColl) {
		this.commentColl = commentColl;
	}

}
