package com.arkivit.model.db;

import java.io.File;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="webbleveranser")
public class Webbleveranser {


		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="ID")
		private int id;

		@Column(name="Company")
		private String company;
		
		@Lob
		@Column(name="Excel_File")
		private File excelFile;
		
		@Temporal(TemporalType.DATE)
		@Column(name="creation_date")
		private Date creationDate;

		public Webbleveranser() {
			
		}

		public Webbleveranser(String company, File excelFile, Date creationDate) {
			this.company = company;
			this.excelFile = excelFile;
			this.creationDate = creationDate;

		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public File getExcelFile() {
			return excelFile;
		}
		public void setExcelFile(File excelFile) {
			this.excelFile = excelFile;
		}
		
		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}


		@Override
		public String toString() {
			return "WebLev [id=" + id + ", company=" + company + ", excelFile=" + excelFile + "date= "+ creationDate +"]";
		}

	}
