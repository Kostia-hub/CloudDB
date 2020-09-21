package common;

import java.util.ArrayList;
import java.util.List;

public class FileListRequest extends AbstractMessage {
    private List<String> remoteFiles;

    public List<String> getRemoteFiles() {
        return remoteFiles;
    }

    public void addFile(String fileName) {
        remoteFiles.add(fileName);
    }

    public FileListRequest() {
        this.remoteFiles = new ArrayList<String>();
    }
}
