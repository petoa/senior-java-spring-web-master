package hu.ponte.hr.exception;/*
 * Copyright © Progmasters (QTC Kft.), 2018.
 * All rights reserved. No part or the whole of this Teaching Material (TM) may be reproduced, copied, distributed,
 * publicly performed, disseminated to the public, adapted or transmitted in any form or by any means, including
 * photocopying, recording, or other electronic or mechanical methods, without the prior written permission of QTC Kft.
 * This TM may only be used for the purposes of teaching exclusively by QTC Kft. and studying exclusively by QTC Kft.’s
 * students and for no other purposes by any parties other than QTC Kft.
 * This TM shall be kept confidential and shall not be made public or made available or disclosed to any unauthorized person.
 * Any dispute or claim arising out of the breach of these provisions shall be governed by and construed in accordance with the laws of Hungary.
 */

import com.fasterxml.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ApiError> handleJsonParseException(JsonParseException ex) {
        logger.error("Request JSON could no be parsed: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("JSON_PARSE_ERROR", "The request could not be parsed as a valid JSON.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body, status);

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiError> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        logger.error("Maximum image size exceeded error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("MAX_UPLOAD_SIZE_EXCEEDED_ERROR", "Maximum image size (2MB) exceeded.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity<ApiError> handleFileUploadFailedException(FileUploadFailedException ex) {
        logger.error("File upload error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("FILE_UPLOAD_ERROR", "File upload failed.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ApiError> handleImageNotFoundException(ImageNotFoundException ex) {
        logger.error("Image not found error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("IMAGE_NOT_FOUND_ERROR", "Image not found.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> defaultErrorHandler(Throwable t) {
        logger.error("An unexpected error occurred: ", t);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError body = new ApiError("UNCLASSIFIED_ERROR", "An unexpected error occurred.", t.getLocalizedMessage());

        return new ResponseEntity<>(body, status);
    }

}

