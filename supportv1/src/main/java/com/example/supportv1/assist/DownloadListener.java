package com.example.supportv1.assist;

public interface DownloadListener {
	public void onDownload(String fileUrl, int downloaded_size, int total_size);
    public void onError(String fileUrl, String filePath, DownLoadManager.ERRORCODE error_code, String errorMsg);
    public void onFinish(String filePath, String fileUrl, boolean is_pause);
}
