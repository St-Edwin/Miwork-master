package com.example.codetribe.miwork;


/**
 * Created by Codetribe on 11/4/2016.
 */

public class Word
{
    /** Default translation for the word*/
    private  String mDefaultTranslation;

    /** Miwok translation for the word*/
    private  String mMiwokTranslation;

    /**Image resource ID for the word*/
    private  int mImageResourceId = NO_IMAGE_PROVIDED;

    private  static final int NO_IMAGE_PROVIDED = -1;

    //Created a Word object constructor
    public  Word(String defaultTranslation, String miwokTranslation)
    {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    //Created a Word object constructor to add image
    public  Word(String defaultTranslation, String miwokTranslation, int imageResourceId)
    {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
    }

    //get methods
    public String getDefaultTranslation()
    {
        return  mDefaultTranslation;
    }

    public String getMiwokTranslation()
    {
        return  mMiwokTranslation;
    }

    public int getImageResourceId()
    {
        return mImageResourceId;
    }

    /**
     * Return whether or not there is an image for this word
     * @return
     */
    public boolean hasImage()
    {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
