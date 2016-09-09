package com.xiyuan.image;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.QualityInfo;

import java.io.File;

public class FrescoConfig {
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;

    public static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 5 * ByteConstants.MB;
    public static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 10 * ByteConstants.MB;
    public static final int MAX_SMALL_DISK_CACHE_SIZE = 20 * ByteConstants.MB;

    public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 10 * ByteConstants.MB;
    public static final int MAX_DISK_CACHE_LOW_SIZE = 30 * ByteConstants.MB;
    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;


    private static String IMAGE_CACHE_PATH = "";

    public static final String IMAGE_PIPELINE_CACHE_DIR = "fresco_cache";

    private static ImagePipelineConfig sImagePipelineConfig;

    private FrescoConfig(){

    }

    public static void initCacheDir(Context context, String path)
    {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + path;
        IMAGE_CACHE_PATH = path;

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File dir = new File(path);
            if(!dir.exists())
            {
                dir.mkdirs();
            }
        }
    }


    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            sImagePipelineConfig = configureCaches(context);
        }
        return sImagePipelineConfig;
    }


    private static ImagePipelineConfig configureCaches(Context context) {

        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE,
                Integer.MAX_VALUE,
                MAX_MEMORY_CACHE_SIZE,
                Integer.MAX_VALUE,
                Integer.MAX_VALUE);

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams= new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

        //
        ProgressiveJpegConfig progressiveJpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int i) {
                return 0;
            }

            @Override
            public QualityInfo getQualityInfo(int i) {
                return null;
            }
        };

        DiskCacheConfig diskSmallCacheConfig =  DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(new File(IMAGE_CACHE_PATH))
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)
                .build();

        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(new File(IMAGE_CACHE_PATH))
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)
                .build();

        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .setMainDiskCacheConfig(diskCacheConfig)
            .setProgressiveJpegConfig(progressiveJpegConfig)
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)
                ;
        return configBuilder.build();
    }

    public static RoundingParams getRoundingParams(){
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
        return roundingParams;
    }

    public static GenericDraweeHierarchy getGenericDraweeHierarchy(Context context){
        GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(context.getResources())
                .setFailureImage(sErrorDrawable)
                .setPlaceholderImage(sPlaceholderDrawable)
                .setProgressBarImage(new ProgressBarDrawable())
                .setRoundingParams(RoundingParams.asCircle())
                .build();
        return gdh;
    }

    public static SimpleDraweeControllerBuilder getSimpleDraweeControllerBuilder(SimpleDraweeControllerBuilder sdcb, Uri uri, Object callerContext, DraweeController draweeController){
        SimpleDraweeControllerBuilder controllerBuilder = sdcb
                .setUri(uri)
                .setCallerContext(callerContext)
                .setOldController(draweeController);
        return controllerBuilder;
    }

    public static ImageDecodeOptions getImageDecodeOptions(){
        ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
                .setUseLastFrameForPreview(true)
                .build();
        return decodeOptions;
    }

    public static Drawable sPlaceholderDrawable;
    public static Drawable sErrorDrawable;

}
