package com.baogetv.app.downloader.core;

import com.baogetv.app.downloader.domain.DownloadInfo;
import com.baogetv.app.downloader.exception.DownloadException;

/**
 * Created by renpingqing on 17/1/22.
 */

public interface DownloadResponse {

  void onStatusChanged(DownloadInfo downloadInfo);

  void handleException(DownloadException exception);
}
