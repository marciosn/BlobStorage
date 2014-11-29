package com.marciosn.cloud.storage.blobs.controll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.marciosn.cloud.storage.blobs.controll.rep.Blob;
import com.marciosn.cloud.storage.blobs.controll.rep.Repositorio;
import com.microsoft.windowsazure.services.blob.client.BlobContainerPermissions;
import com.microsoft.windowsazure.services.blob.client.BlobContainerPublicAccessType;
import com.microsoft.windowsazure.services.blob.client.CloudBlob;
import com.microsoft.windowsazure.services.blob.client.CloudBlobClient;
import com.microsoft.windowsazure.services.blob.client.CloudBlobContainer;
import com.microsoft.windowsazure.services.blob.client.CloudBlockBlob;
import com.microsoft.windowsazure.services.blob.client.ListBlobItem;
import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount;
import com.microsoft.windowsazure.services.core.storage.StorageException;

@ManagedBean
@RequestScoped
public class StorageControll_Pdfs {

	
	@ManagedProperty(value="#{repositorio}")
	private Repositorio repositorio;
	
	private UploadedFile file2;
	private File file3;
	private StreamedContent file;  
	private String nomeArquivo;
	//private String video;
    public static final String storageConnectionString = 
            "DefaultEndpointsProtocol=http;" + 
               "AccountName=portalvhdsjtq29274knmm2;" + 
               "AccountKey=ywi91c425cNVBnpFuQs0ieA1UzkUIF/nF5KZ0BpUc9fXh0xBs36IaO8w039MRvtRLineI1iMgcKGlXsOq51vKg==";
    
    public String Upload() throws FileNotFoundException {
        try
        {
        	File f = CriaFile(file2);
        	String path = f.getAbsolutePath();
        	nomeArquivo = file2.getFileName();

        	if(ValidaPDF(nomeArquivo) != true){
        		
        		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        		flash.setKeepMessages(true);
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage	(FacesMessage.SEVERITY_ERROR, "Invalid Format!!!", null));
        		return "/pages/upload";
        	}
        	else{
        	
        	System.out.println("nome do arquivo no metodo upload: " + nomeArquivo);
        	//System.out.println(nomeArquivo.get);
            CloudStorageAccount account;
            CloudBlobClient serviceClient;
            CloudBlobContainer container;
            CloudBlockBlob blob;

            account = CloudStorageAccount.parse(storageConnectionString);
            serviceClient = account.createCloudBlobClient();
            container = serviceClient.getContainerReference("mypdfs");
            container.createIfNotExist();
            BlobContainerPermissions containerPermissions;
            containerPermissions = new BlobContainerPermissions();
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
            container.uploadPermissions(containerPermissions);

            blob = container.getBlockBlobReference(nomeArquivo);
            //File f = new File(nomeArquivo);
            File fileReference = new File (path);
            //File fileReference = new File ("C:\\myimages\\a13.jpg");
            blob.upload(new FileInputStream(fileReference), fileReference.length());
            //
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.setKeepMessages(true);
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage	(FacesMessage.SEVERITY_INFO, "Operation Done", null));
    		
            System.out.println("Realizando Upload do arquivo --> " + nomeArquivo);
            System.out.println("Processamento completo.");
        	}

        }
        catch (FileNotFoundException fileNotFoundException)
        {
            System.out.print("FileNotFoundException encontrado no metodo Upload(): ");
            System.out.println(fileNotFoundException.getMessage());
            return "/pages/exception";
            //System.exit(-1);
        }
        catch (StorageException storageException)
        {
            System.out.print("StorageException encontrado no metodo Upload(): ");
            System.out.println(storageException.getMessage());
            return "/pages/exception";
            //System.exit(-1);
        }
        catch (URISyntaxException uriSyntaxException)
        {
            System.out.print("URISyntaxException encontrado no metodo Upload(): ");
            System.out.println(uriSyntaxException.getMessage());
            return "/pages/exception";
            //System.exit(-1);
        }
        catch (Exception e)
        {
            System.out.print("Exception encontrado no metodo Upload(): ");
            System.out.println(e.getMessage());
            return "/pages/exception";
            //System.exit(-1);
        }
        return ListarBlobs();
 }
    
    public String ListarBlobs() throws FileNotFoundException{
    	while(repositorio.getListapdfs().size() > 0){
    		LimpaLista();
    	}
    	CloudStorageAccount storageAccount;
		try {
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
	    	CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
	    	CloudBlobContainer container = blobClient.getContainerReference("mypdfs");
	    	for (ListBlobItem blobItem : container.listBlobs()) {
	    	    String uri = blobItem.getUri().toString();
	    	    System.out.println("Exibindo uri --------> "+ uri);
	        	Blob blob = new Blob(uri);
	            repositorio.getListapdfs().add(blob);

	    	}
		} catch (InvalidKeyException invalidKeyException) {
			System.out.print("InvalidKeyException encontrado no metodo ListarBlobs(): ");
			System.out.println(invalidKeyException.getMessage());
			return "/pages/exception";
            //System.exit(-1);

		} catch (URISyntaxException uriSyntaxException) {
			System.out.print("URISyntaxException encontrado no metodo ListarBlobs(): ");
	        System.out.println(uriSyntaxException.getMessage());
	        return "/pages/exception";
	        //System.exit(-1);
		} catch (StorageException storageException) {
			System.out.print("StorageException encontrado no metodo ListarBlobs(): ");
			System.out.println(storageException.getMessage());
			return "/pages/exception";
			//System.exit(-1);
		}
		/*Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage	(FacesMessage.SEVERITY_INFO, "Operation Done", null));*/
		return "/pages/list";

    }
    
    public String DownloadBlobls(){
    	try {
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference("mypdfs");
			
			for (ListBlobItem blobItem : container.listBlobs()) {
			    if (blobItem instanceof CloudBlob) {
			        CloudBlob blob = (CloudBlob) blobItem;
			        //blob.download(new FileOutputStream(blob.getName()));
			        blob.download(new FileOutputStream(nomeArquivo));
			        System.out.println("Download realizado --> "+ blobItem.getUri());
			    }
			}
			
		} catch (InvalidKeyException invalidKeyException) {
			System.out.print("InvalidKeyException encontrado no metodo DownloadBlobls(): ");
			System.out.println(invalidKeyException.getMessage());
			return "/pages/exception";
            //System.exit(-1);
			
		} catch (URISyntaxException uriSyntaxException) {
			System.out.print("URISyntaxException encontrado no metodo DownloadBlobls(): ");
	        System.out.println(uriSyntaxException.getMessage());
	        return "/pages/exception";
	        //System.exit(-1);
			
		} catch (StorageException storageException) {
			System.out.print("StorageException encontrado no metodo DownloadBlobls(): ");
			System.out.println(storageException.getMessage());
			return "/pages/exception";
			//System.exit(-1);
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.print("FileNotFoundException encontrado no metodo DownloadBlobls(): ");
			System.out.println(fileNotFoundException.getMessage());
			return "/pages/exception";
			//System.exit(-1);
			
		} catch (IOException ioException) {
			System.out.print("IOException encontrado no metodo DownloadBlobls(): ");
			System.out.println("IOException: " + ioException.getMessage() );
			return "/pages/exception";
		}
    	Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage	(FacesMessage.SEVERITY_INFO, "Operation Done", null));
    	return "/pages/download";
    }
    
    public String DeleteBlobs() throws FileNotFoundException{
    	try {

    		CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
    		CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
    		CloudBlobContainer container = blobClient.getContainerReference("mypdfs");
    		//CloudBlockBlob blob = container.getBlockBlobReference("a5.jpg");
    		CloudBlockBlob blob = container.getBlockBlobReference(nomeArquivo);
    		
    		System.out.println("Processo de exclusão concluído -->" + nomeArquivo);
    		
    		blob.delete();
    		
			
    	} catch (InvalidKeyException invalidKeyException) {
			System.out.print("InvalidKeyException encontrado no metodo DeleteBlobs(): ");
			System.out.println(invalidKeyException.getMessage());
			return "/pages/exception";
            //System.exit(-1);
			
		} catch (URISyntaxException uriSyntaxException) {
			System.out.print("URISyntaxException encontrado no metodo DeleteBlobs(): ");
	        System.out.println(uriSyntaxException.getMessage());
	        return "/pages/exception";
	        //System.exit(-1);
			
		} catch (StorageException storageException) {
			System.out.print("StorageException encontrado no metodo DeleteBlobs(): ");
			System.out.println(storageException.getMessage());
			return "/pages/exception";
			//System.exit(-1);
		}
    	Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage	(FacesMessage.SEVERITY_INFO, "Operation Done", null));
    	return ListarBlobs();
    }
    
    public String LimpaLista(){
    	System.out.println("Entrou no método de limpar lista");

    	for(int i=0 ; i < repositorio.getListapdfs().size();i++){
        	System.out.println("Pegando do arraylist: " + repositorio.getListapdfs().get(i).getUri() +" Indice --> "+i);
        	repositorio.getListapdfs().remove(i);
        }
    	return "/pages/list";
    }
    public boolean ValidaPDF(String string){
    	if(string.contains(".pdf")){
    		System.out.println("Passou na Validação de formato PDF.");
    		return true;
    	}else
    		System.out.println("Não passou na Validação de formato PDF.");
    	return false;
    }
    public File CriaFile(UploadedFile f) throws IOException{
		System.out.println("Entrou no metodo criaFile");
    	String prefix = FilenameUtils.getBaseName(file2.getFileName());
    	String suffix = FilenameUtils.getExtension(file2.getFileName());
    	//File file = File.createTempFile(prefix + ",", "." + suffix, "/path/to/downloads");
    	File file = File.createTempFile(prefix + ",", "." + suffix);
    	
    	InputStream input = file2.getInputstream();
    	OutputStream output = new FileOutputStream(file);
    	
    	try{
    		IOUtils.copy(input, output);
    	}finally{
    		IOUtils.closeQuietly(output);
    		IOUtils.closeQuietly(input);
    	}
    
    
    	System.out.println("Nome do novo file: --->" + file.getName());
    	System.out.println("Path do novo file: --->" + file.getAbsolutePath());
    	System.out.println("Path do novo file: --->" + file.getCanonicalPath());
    	return file;
    }

	public Repositorio getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
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

	public void setFile3(File file3) {
		this.file3 = file3;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
    
	
}
