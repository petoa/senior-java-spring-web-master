package hu.ponte.hr.exception;

public class FileUploadFailedException extends RuntimeException {
    public FileUploadFailedException() {
        super();
    }

    public FileUploadFailedException(String message) {
        super(message);
    }
}
