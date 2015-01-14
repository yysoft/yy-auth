package net.caiban.auth.dashboard.domain.hr;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadBean {
    private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
