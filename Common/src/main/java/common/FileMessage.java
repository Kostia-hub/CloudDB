package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileMessage extends AbstractMessage {
    private static final long serialVersionUID = Long.MAX_VALUE;

    private String fileName;
    private byte[] data;
    private double size;
    private String sha1;

    public double getSize() {
        return size;
    }

    public String getSha1() {
        return sha1;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }

    public FileMessage(Path path) throws IOException, NoSuchAlgorithmException {
        fileName = path.getFileName().toString();
        data = Files.readAllBytes(path);
        sha1 = getCheckSum("SHA1");
        size = Files.size(path);
    }

    private String getCheckSum(String hash) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(hash);
        sha.update(data);

        byte[] hashByte = sha.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : hashByte) sb.append(String.format("%08X", b));
        return sb.toString();
    }
}
