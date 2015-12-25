package dev.laz.qkeycounter.entities;

import dev.laz.qkeycounter.R;

/**
 * Image type.
 */
public enum ImageType {

    COOKIE(1, R.drawable.giant_cookie),
    MOON_LIGHT(2, R.drawable.moon_light),
    MOON_DARK(3, R.drawable.moon_dark),
    CHEESE(4, R.drawable.cheese);

    private int mCode;
    private int mImageResource;

    /**
     * Constructor.
     *
     * @param code Code.
     */
    ImageType(int code, int resource) {

        mCode = code;
        mImageResource = resource;
    }

    /**
     * Returns type code.
     *
     * @return Code.
     */
    public int getCode() {

        return mCode;
    }

    /**
     * Returns image resource.
     *
     * @return Image resource id.
     */
    public int getImageResource() {

        return mImageResource;
    }

    /**
     * Returns ImageType from code. Cookie if code is not related to any type.
     *
     * @param code Code.
     * @return ImageType.
     */
    public static ImageType from(int code) {

        ImageType[] imageTypes = values();
        for (int i = 0; i < imageTypes.length; i++) {

            if (imageTypes[i].getCode() == code) {

                return imageTypes[i];
            }
        }
        return ImageType.COOKIE;
    }

}
