package com.example.demo.mybatis_for_chat.filetransfer;

import jakarta.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

@ServerEndpoint("/websocket/chat")
public class FileTransferServer {

    private static final int BUFFER_SIZE = 8192;

    // 处理与单个客户端的文件传输及断点下载，并上传到指定URL
    @OnMessage
    public void handleFileTransfer(Session session, ByteBuffer buffer) throws IOException {
        // 从WebSocket消息中获取已下载的字节数相关信息
        long alreadyDownloadedStart = bytesToLong(buffer.array(), 0);
        long alreadyDownloadedEnd = bytesToLong(buffer.array(), 4);

        // 获取文件名信息
        int fileNameLength = bytesToInt(Arrays.copyOfRange(buffer.array(), 8, 12));
        byte[] fileNameBytes = Arrays.copyOfRange(buffer.array(), 12, 12 + fileNameLength);
        String fileName = new String(fileNameBytes);

        // 指定要接收并保存的文件路径
        File file = new File(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);

        // 根据是否是从0开始下载进行不同处理
        if (alreadyDownloadedStart == 0) {
            // 从0开始下载，直接初始化文件输入流等操作
            FileInputStream fileInputStream = new FileInputStream(file);
            long fileLength = fileInputStream.available();
            long bytesSent = 0;

            // 发送剩余的文件内容并计算进度
            byte[] bufferArray = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(bufferArray))!= -1) {
                ByteBuffer sendBuffer = ByteBuffer.wrap(bufferArray, 0, bytesRead);
                if (session!= null) {
                    session.getBasicRemote().sendBinary(sendBuffer);
                }
                bytesSent += bytesRead;
                int progress = (int) ((bytesSent * 100) / fileLength);
                sendProgress(session, progress);
            }

            fileInputStream.close();
        } else {
            // 断点重传情况，定位到已下载的末尾位置并继续传输
            fileOutputStream.write(new byte[(int) (alreadyDownloadedEnd - alreadyDownloadedEnd + 1)]);

            FileInputStream fileInputStream = new FileInputStream(file);
            long fileLength = fileInputStream.available();
            long bytesSent = 0;

            // 跳过已经下载过的字节
            fileInputStream.skip(alreadyDownloadedEnd + 1);

            // 发送剩余的文件内容并计算进度
            byte[] bufferArray = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(bufferArray))!= -1) {
                ByteBuffer sendBuffer = ByteBuffer.wrap(bufferArray, 0, bytesRead);
                if (session!= null) {
                    session.getBasicRemote().sendBinary(sendBuffer);
                }
                bytesSent += bytesRead;
                int progress = (int) ((bytesSent * 100) / fileLength);
                sendProgress(session, progress);
            }

            fileInputStream.close();
        }

        fileOutputStream.close();

        // 上传文件到指定URL并检查上传是否成功
        boolean uploadSuccess = uploadFileToUrl(file, "your_upload_url_here");
        if (uploadSuccess) {
            System.out.println("文件 " + fileName + " 上传到指定URL成功！");
        } else {
            System.out.println("文件 " + fileName + " 上传到指定URL失败！");
        }
    }

    private static long bytesToLong(byte[] bytes, int offset) {
        return ((long) (bytes[offset] & 0xff) << 56) |
                ((long) (bytes[offset + 1] & 0xff) << 48) |
                ((long) (bytes[offset + 2] & 0xff) << 40) |
                ((long) (bytes[offset + 3] & 0xff) << 32) |
                ((long) (bytes[offset + 4] & 0xff) << 24) |
                ((long) (bytes[offset + 5] & 0xff) << 16) |
                ((long) (bytes[offset + 6] & 0xff) << 8) |
                ((long) (bytes[offset + 7] & 0xff));
    }

    private static int bytesToInt(byte[] bytes) {
        return ((bytes[0] & 0xff) << 24) | ((bytes[1] & 0xff) << 16) | ((bytes[2] & 0xff) << 8) | (bytes[3] & 0xff);
    }

    private void sendProgress(Session session, int progress) throws IOException {
        ByteBuffer progressBuffer = ByteBuffer.allocate(4);
        progressBuffer.put((byte) ((progress >> 24) & 0xff));
        progressBuffer.put((byte) ((progress >> 16) & 0xff));
        progressBuffer.put((byte) ((progress >> 8) & 0xff));
        progressBuffer.put((byte) (progress & 0xff));
        progressBuffer.flip();
        if (session!= null) {
            session.getBasicRemote().sendBinary(progressBuffer);
        }
    }

    private boolean uploadFileToUrl(File file, String uploadUrl) throws IOException {
        HttpClientBuilder built = HttpClientBuilder.create();
        HttpClient httpClient = built.build();
        HttpPost httpPost = new HttpPost(uploadUrl);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, file.getName());

        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);

        // 根据响应状态码判断上传是否成功
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            return true;
        } else {
            return false;
        }
    }
}