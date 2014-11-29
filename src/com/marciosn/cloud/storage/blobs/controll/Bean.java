package com.marciosn.cloud.storage.blobs.controll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
public class Bean {
	private UploadedFile file2;
	private File file3;
	String myFileName;
	private List<String> selectedTexts;  
      private List<String> imgs = new ArrayList<String>();

      public void myFileUpload(FileUploadEvent event) {
    	    String myFileName = FilenameUtils.getName(event.getFile().getFileName());
    	}
      
    public List<String> complete(String query) {  
        List<String> results = new ArrayList<String>();  
          
        for(int i = 0; i < 10; i++) {  
            results.add(query + i);  
        }  
          
        return results;  
    }   
        
  
    public List<String> getSelectedTexts() {  
        return selectedTexts;  
    }  
    public void setSelectedTexts(List<String> selectedTexts) {  
        this.selectedTexts = selectedTexts;  
    }  
  
    public void handleUnselect(UnselectEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected:" + event.getObject().toString(), null);  
          
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }


	public List<String> getImgs() {
		return imgs;
	}


	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}
    public UploadedFile getFile2() {
		return file2;
	}


	public void setFile2(UploadedFile file2) {
		this.file2 = file2;
	}


	public File getFile3() {
		return file3;
	}


	public String getMyFileName() {
		return myFileName;
	}

	public void setMyFileName(String myFileName) {
		this.myFileName = myFileName;
	}

	public void setFile3(File file3) {
		this.file3 = file3;
	}


}
