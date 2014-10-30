package com.juliomarcos;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class ImageViewPopUpHelper {

	private Activity context;
	
	private Drawable imageViewDrawable;
	private int finalImageWidth;
	private int finalImageHeight;
	private boolean requireResizingOfBitmap;

	private ImageView poppedImageView;
	private Dialog dialog;

	@SuppressWarnings("deprecation")
	private void cacheResizedDrawable(Drawable drawable, boolean shouldScaleDown, boolean shouldScaleUp) {
		imageViewDrawable = drawable;
		int imageRealWidth = imageViewDrawable.getIntrinsicWidth();
		int imageRealHeight = imageViewDrawable.getIntrinsicHeight();
		
		Point screenDimensions = getScreenDimensions(context);		
		final int screenWidth = screenDimensions.x;
		final int screenHeight = screenDimensions.y;
				
		// Algoritmo iterativo para achar um tamanho final para a imagem
		while(shouldScaleDown && (imageRealWidth >= screenWidth || imageRealHeight >= screenHeight)) {
			imageRealWidth  *= 0.9;
			imageRealHeight *= 0.9;
			requireResizingOfBitmap = true;
		}

		while(shouldScaleUp && ((imageRealWidth * 1.1) <= screenWidth && (imageRealHeight * 1.1) <= screenHeight)) {
      imageRealWidth  *= 1.1;
      imageRealHeight *= 1.1;
      requireResizingOfBitmap = true;
    }

		finalImageWidth = imageRealWidth;
		finalImageHeight = imageRealHeight;		
		
		if (requireResizingOfBitmap) {
			Bitmap bitmap = drawableToBitmap(imageViewDrawable);
			BitmapDrawable resizedBitmapDrawable = new BitmapDrawable(
					context.getResources(), 
					Bitmap.createScaledBitmap(bitmap, finalImageWidth, finalImageHeight, false));
			poppedImageView.setBackgroundDrawable(resizedBitmapDrawable);
		}
		else {
			poppedImageView.setBackgroundDrawable(imageViewDrawable);
		}
	}

	/**
   * Enable tap to show popup image dialog with alternative drawable than imageView's drawable
   * @param context Context
   * @param imageView Target Image View
   * @param drawable Alternative Drawable
   */
  public static void enablePopUpOnClick(final Activity context, final ImageView imageView, final Drawable drawable) {
      new ImageViewPopUpHelper().internalEnablePopUpOnClick(context, imageView, drawable);
  }

	public static void enablePopUpOnClick(final Activity context, final ImageView imageView) {
		new ImageViewPopUpHelper().internalEnablePopUpOnClick(context, imageView);
	}

	private void internalEnablePopUpOnClick(final Activity context, final ImageView imageView, final Drawable drawable) {

		this.context = context;
		poppedImageView = new ImageView(context);
		
		dialog = new Dialog(context);
		dialog.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
		dialog.setContentView(poppedImageView);
		dialog.getWindow().setBackgroundDrawable(null); // Without this line there is a very small border around the image (1px)				
		dialog.setCanceledOnTouchOutside(true); // Gingerbread support
		
		imageView.setOnClickListener(new View.OnClickListener() {						
			@Override
			public void onClick(View v) {

				if (drawable != null) {
          if (imageViewDrawable != drawable) {
            cacheResizedDrawable(drawable, true, true);
          }
        }
        else {
          ImageView imageView = (ImageView) v;

          if (imageViewDrawable != imageView.getDrawable()) {
            cacheResizedDrawable(imageView.getDrawable(), true, true);
          }
        }

				dialog.show();
			}
		});
	}

	private void internalEnablePopUpOnClick(final Activity context, final ImageView imageView) {
		internalEnablePopUpOnClick(context, imageView, null);
	}

	@SuppressWarnings("deprecation")
	private Point getScreenDimensions(Activity context) {
		// Get screen size
		Display display = context.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1){
		    display.getRealSize(size);
		}
		else if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2){
			display.getSize(size);
		} else{
		    size.x = display.getWidth(); 
		    size.y = display.getHeight();
		}
		return size;
	}
	
	private Bitmap drawableToBitmap (Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);

	    return bitmap;
	}
	
}
