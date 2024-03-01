package yee.pltision.blasttech;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.FileReader;
import java.util.List;

record DownloadFile(String path,String hash){}

class FilesWrapper {

    @SerializedName("files")
    private List<DownloadFile> files;

    public List<DownloadFile> getFiles() {
        return files;
    }

    public void setFiles(List<DownloadFile> files) {
        this.files = files;
    }
}

class JsonParsingExample {

    public static void main(String[] args) {
        try {
            // 创建Gson实例  
            Gson gson = new Gson();

            // 指定要读取的JSON文件路径  
            String jsonFilePath = "C:\\Users\\windows_user\\Documents\\Tencent Files\\3458283428\\FileRecv\\test.json";

            // 使用FileReader读取JSON文件  
            FileReader fileReader = new FileReader(jsonFilePath);

            // 使用Gson的fromJson方法解析JSON文件  
            FilesWrapper filesWrapper = gson.fromJson(fileReader, FilesWrapper.class);

            // 获取解析后的文件列表  
            List<DownloadFile> downloadFiles = filesWrapper.getFiles();

            // 输出解析结果（可选）  
            for (DownloadFile file : downloadFiles) {
                System.out.println("Path: " + file.path() + ", Hash: " + file.hash());
            }

            // 关闭文件读取器  
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}