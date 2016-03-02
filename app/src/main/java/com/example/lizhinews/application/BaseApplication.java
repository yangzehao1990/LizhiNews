package com.example.lizhinews.application;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;


public class BaseApplication extends Application
{
    //private static File root;
    @Override
    public void onCreate()
    {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    /**
     * 使用DisplayImageOptions.Builder()创建DisplayImageOptions
     *
     * @param context
     */
    @SuppressWarnings("deprecation")
    public static void initImageLoader(Context context)
    {

        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "/mnt/sdcard/android/data/LizhiNewscatch");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)

                // 线程优先级
                .threadPriority(Thread.MAX_PRIORITY)

                        // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .memoryCacheExtraOptions(480, 800)

                        // 线程池内加载的数量
                .threadPoolSize(3)

                        // 设置缓存大
                        /**
                         * 	UsingFreqLimitedMemoryCache (缓存大小超过指定值时,删除最少使的bitmap)
                         *	LRULimitedMemoryCache (缓存大小超过指定值时,删除最近最少使用的<span helvetica="" segoe="" style="font-family:">bitmap) --默认值</span>
                         *	FIFOLimitedMemoryCache (缓存大小超过指定值时,按先进先出规则删除的<span helvetica="" segoe="" style="font-family:">bitmap)</span>
                         *	LargestLimitedMemoryCache (缓存大小超过指定值时,删除最大的bitmap)
                         *	LimitedAgeMemoryCache (缓存对象超过定义的时间后删除)
                         */
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))

                        //设置缓存路径
                        // 由原先的discCache -> diskCache
                        //UnlimitedDiskCache   不限制缓存大小（默认）
                        //TotalSizeLimitedDiskCache (设置总缓存大小，超过时删除最久之前的缓存)
                        //FileCountLimitedDiskCache (设置总缓存文件数量，当到达警戒值时，删除最久之前的缓存。如果文件的大小都一样的时候，可以使用该模式)
                        //LimitedAgeDiskCache (不限制缓存大小，但是设置缓存时间，到期后删除)

                        //.diskCache(new UnlimitedDiskCache(cacheDir))
                        //.diskCache(new LimitedAgeDiskCache(cacheDir, TRIM_MEMORY_BACKGROUND))


                        // connectTimeout (5 s), readTimeout (30 s)超时时间
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                .denyCacheImageMultipleSizesInMemory()
                        //将保存的时候的URI名称用MD5 加密
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }
}
