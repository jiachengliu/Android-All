package com.mylike.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * contenType类型
 */
public abstract class RequestBody {

    abstract String contentType();

    abstract void wirteTo(OutputStream ops) throws IOException;

}
