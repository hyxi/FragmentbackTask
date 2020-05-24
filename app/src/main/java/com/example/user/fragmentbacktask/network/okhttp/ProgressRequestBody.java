package com.example.user.fragmentbacktask.network.okhttp;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 文件上传进度request
 */
public class ProgressRequestBody extends RequestBody {

    private MediaType contentType;
    private File file;
    private ProgressListener listener;

    public ProgressRequestBody(MediaType contentType, File file, ProgressListener listener) {
        this.file = file;
        this.listener = listener;
        this.contentType = contentType;
    }

    @Override
    public MediaType contentType() {
        return contentType;
    }

    @Override
    public long contentLength() {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source;
        try {
            source = Okio.source(file);
            //sink.writeAll(source);
            Buffer buf = new Buffer();
            Long remaining = contentLength();
            for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                sink.write(buf, readCount);
                listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    interface ProgressListener {
        void onProgress(long totalBytes, long remainingBytes, boolean done);
    }
}
