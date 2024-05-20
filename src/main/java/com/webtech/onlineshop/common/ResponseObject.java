/*
 *  Copyright (c) 2018 - 2019. Irembo
 *
 * All rights reserved.
 *
 */

package com.webtech.onlineshop.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {

    private boolean status;

    private Object data;

    private String message;
    private String detailedMessage;

    public ResponseObject(final Exception exception) {
        this.status = false;
        this.message = exception.getMessage();
    }

    public ResponseObject(final Exception exception, String failureMessage) {
        this.status = false;
        this.message = failureMessage;
    }

    public ResponseObject(Object results) {
        this.status = true;
        this.data = results;
        this.message = "Result found";
    }

    public ResponseObject(Object response, String message) {
        this.status = true;
        this.data = response;
        this.message = message;
    }
    public ResponseObject(final byte[] response, String message) {
        this.status = true;
        this.data = response;
        this.message = message;
    }
}
