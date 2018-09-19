package com.example.xiaopeng.myapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.BitmapImageViewTarget

/**
 * Glide特点
 * 使用简单
 * 可配置度高，自适应程度高
 * 支持常见图片格式 Jpg png gif webp
 * 支持多种数据源  网络、本地、资源、Assets 等
 * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
 * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
 * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
 * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
 *
 *
 * Created by xiaopeng on 2018/4/26
 */
object GlideUtil {

    /**
     * 一下方法默认都是居中剪裁，以及缓存原图片跟转换后图片，有其他需求自己另外添加
     */

    //默认加载
    fun loadImageView(mContext: Context, path: String?, mImageView: ImageView?) {
        Glide.with(mContext)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .centerCrop()
                .into(mImageView)
    }

    /**
     * 无占位图，加载失败展示错误图
     */
    fun loadImageView(mContext: Context, path: String?, mImageView: ImageView?, resId: Int) {
        Glide.with(mContext)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
//                .placeholder(resId)
                .error(resId)
                .centerCrop()
                .into(mImageView)
    }



    fun loadImageView(mContext: Context, uri: Uri?, mImageView: ImageView?) {
        Glide.with(mContext)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .centerCrop()
                .into(mImageView)
    }

    fun loadImageView(mContext: Context, uri: Uri?, mImageView: ImageView?, resId: Int) {
        Glide.with(mContext)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .placeholder(resId)
                .centerCrop()
                .into(mImageView)
    }


    //加载指定大小
    fun loadImageViewSize(mContext: Context, path: String, width: Int, height: Int, mImageView: ImageView) {
        Glide.with(mContext)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .centerCrop()
                .override(width, height)
                .into(mImageView)
    }

    //设置加载中以及加载失败图片
    fun loadImageViewLoding(mContext: Context, path: String, mImageView: ImageView, placeholderResId: Int, errorResId: Int) {
        Glide.with(mContext)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .centerCrop()
                .placeholder(placeholderResId)
                .error(errorResId)
                .into(mImageView)
    }

    //设置加载中以及加载失败图片并且指定大小
    fun loadImageViewLodingSize(mContext: Context, path: String, width: Int, height: Int, mImageView: ImageView) {
        Glide.with(mContext)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .centerCrop()
                .override(width, height)
                //                .placeholder()
                //                .error()
                .into(mImageView)
    }

    /**
     * 圆形的图片，此方法不支持gif
     *
     * @param context
     * @param imageView
     * @param imageUrl
     */
    fun setImageViewCircleUrl(context: Context, imageView: ImageView, imageUrl: String) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .centerCrop()
                //                .placeholder()
                //                .error()
                .into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap) {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.isCircular = true
                        imageView.setImageDrawable(circularBitmapDrawable)
                    }

                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                        super.onLoadFailed(e, errorDrawable)
                        //                        imageView.setImageResource();
                    }
                })
    }

    fun setImageViewCircleUrl(context: Context, imageView: ImageView, imageUrl: String, resId: Int) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .centerCrop()
                .placeholder(resId)
                //                .placeholder()
                //                .error()
                .into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap) {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.isCircular = true
                        imageView.setImageDrawable(circularBitmapDrawable)
                    }

                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                        super.onLoadFailed(e, errorDrawable)
                        //                        imageView.setImageResource();
                    }
                })
    }

    /**
     * 带缩略图的
     *
     * @param mContext
     * @param path
     * @param mImageView
     */
    fun loadImageViewThumbnail(mContext: Context, path: String, mImageView: ImageView) {
        Glide.with(mContext)
                .load(path)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .centerCrop()
                .into(mImageView)
    }

    /**
     * 设置接口监听
     *
     * @param mContext
     * @param path
     * @param mImageView
     * @param requstlistener
     */
    fun loadImageViewListener(mContext: Context, path: String, mImageView: ImageView, requstlistener: RequestListener<String, GlideDrawable>) {
        Glide.with(mContext)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存源资源和转换后的资源
                .centerCrop()
                .listener(requstlistener)
                .into(mImageView)
    }

    /**
     * 跟随控件自身设置的scaleType
     */
    fun loadImageScaleTypeByView(context: Context?, path: String?, imageView: ImageView) {
        Glide.with(context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

    /**
     * 清理磁盘缓存 需要在子线程中执行
     *
     * @param context
     */
    fun ClearDiskCache(context: Context) {
        Glide.get(context).clearDiskCache()
    }

    fun trimMemory(level: Int, context: Context) {
        Glide.with(context).onTrimMemory(level)
    }

    /**
     * 清理内存缓存  可以在UI主线程中进行
     *
     * @param context
     */
    fun clearAllMemoryCaches(context: Context) {
        Glide.with(context).onLowMemory()
    }

}
